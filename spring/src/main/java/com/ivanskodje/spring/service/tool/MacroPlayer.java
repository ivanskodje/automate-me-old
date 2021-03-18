package com.ivanskodje.spring.service.tool;


import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.maintainer.NativeKeyPressMaintainer;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.runner.MacroActionRunner;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MacroPlayer {

  private final GlobalMacroState globalMacroState;
  private final NativeKeyPressMaintainer nativeKeyPressMaintainer;
  private Thread thread;

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(keyExtractor.apply(t));
  }

  @SneakyThrows
  public void play(List<MacroAction> macroActionList) {
//    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    if (globalMacroState.isStopped()) {
      log.debug("Playing last recording");
      globalMacroState.changeToPlaying();

      // TODO: Dont do this, fix
      Runnable runnable = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
          for (MacroAction macroAction : macroActionList) {
            Thread.sleep(macroAction.getDelayInMs()); // DONT DO THIS (probably)
            if (globalMacroState.isStopped()) {
              releaseAllPressedKeys(); // TODO: Fix, we probably have to store the nativekeyevent directly (or on the side?)
              return;
            }
            GlobalScreen.postNativeEvent(macroAction.getNativeKeyEvent());
          }

          // Get unique list of only keys that have been pressed
//          List<MacroAction> macroActionListKeys = macroActionList.stream().filter(distinctByKey(
//              MacroAction::getRawCode)).collect(Collectors.toList());

          log.debug("Stopping playback (end)");
          globalMacroState.changeToStopped();
        }

        private void releaseAllPressedKeys() {
          for (int pressedRawCode : nativeKeyPressMaintainer.getPressedRawCodeList()) {

            // int id, int modifiers, int rawCode, int keyCode, char keyChar, int keyLocation
            NativeKeyEvent nativeKeyEvent = new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_RELEASED, 0,
                pressedRawCode, 0, ' ');
            GlobalScreen.postNativeEvent(nativeKeyEvent);
          }
          nativeKeyPressMaintainer.getPressedRawCodeList().clear();
        }
      };

      this.thread = new Thread(runnable);
      thread.start();


    } else if (globalMacroState.isPlaying()) {
      log.debug("Stop playing");
      globalMacroState.changeToStopped();
//      executorService.shutdownNow();
    } else {
      log.debug("We cannot play/stop because we are in state {}", globalMacroState.getMacroState());
    }
  }

//  private void scheduleMacroStateStopped(ScheduledExecutorService executorService,
//      MacroAction lastMacroAction) {
//    executorService
//        .schedule(globalMacroState::changeToStopped,
//            lastMacroAction.getDelayInMs() + 1,
//            TimeUnit.MILLISECONDS);
//  }

  void scheduleMacroAction(ScheduledExecutorService executorService, MacroAction macroAction) {
    executorService
        .schedule(new MacroActionRunner(macroAction), macroAction.getDelayInMs(), TimeUnit.MILLISECONDS);
  }
}

package com.ivanskodje.spring.service.tool;


import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.runner.MacroActionRunner;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MacroPlayer {

  private final GlobalMacroState globalMacroState;

  public void play(List<MacroAction> macroActionList) {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    if (globalMacroState.isStopped()) {
      log.debug("Playing last recording");
      globalMacroState.changeToPlaying();

      MacroAction lastMacroAction = null;
      for (MacroAction macroAction : macroActionList) {
        scheduleMacroAction(executorService, macroAction);
        lastMacroAction = macroAction;
      }
      scheduleMacroStateStopped(executorService, lastMacroAction);
    } else if (globalMacroState.isPlaying()) {
      log.debug("Stop playing");
      globalMacroState.changeToStopped();
      executorService.shutdownNow();
    } else {
      log.debug("We cannot play/stop because we are in state {}", globalMacroState.getMacroState());
    }
  }


  private void scheduleMacroStateStopped(ScheduledExecutorService executorService,
      MacroAction lastMacroAction) {
    executorService
        .schedule(globalMacroState::changeToStopped,
            lastMacroAction.getDelayInMs() + 1,
            TimeUnit.MILLISECONDS);
  }

  void scheduleMacroAction(ScheduledExecutorService executorService, MacroAction macroAction) {
    executorService
        .schedule(new MacroActionRunner(macroAction), macroAction.getDelayInMs(), TimeUnit.MILLISECONDS);
  }
}

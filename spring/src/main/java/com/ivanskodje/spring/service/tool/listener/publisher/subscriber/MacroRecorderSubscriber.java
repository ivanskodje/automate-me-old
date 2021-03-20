package com.ivanskodje.spring.service.tool.listener.publisher.subscriber;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.GlobalMacroState;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MacroRecorderSubscriber implements KeySubscriber {

  private final GlobalMacroState globalMacroState;
  private boolean isRunning = false;
  private MacroAction previousMacroAction = null;
  @Getter
  @Setter
  private List<MacroAction> macroActionList = new ArrayList<>();
  @Setter
  private Long startTimeInMs;


  public MacroRecorderSubscriber(GlobalMacroState globalMacroState) {
    this.globalMacroState = globalMacroState;
  }

  @Override
  public void pressed(NativeKeyEvent nativeKeyEvent) {
    recordKeyEvent(nativeKeyEvent);
  }

  @Override
  public void released(NativeKeyEvent nativeKeyEvent) {
    recordKeyEvent(nativeKeyEvent);
  }

  private void recordKeyEvent(NativeKeyEvent nativeKeyEvent) {
    Long delayInMs;
    if (previousMacroAction == null) {
      delayInMs = System.currentTimeMillis() - startTimeInMs;
    } else {
      delayInMs = System.currentTimeMillis() - previousMacroAction.getTime();
    }
    MacroAction macroAction = new MacroAction(nativeKeyEvent, delayInMs, System.currentTimeMillis());
    macroActionList.add(macroAction);
    previousMacroAction = macroAction;
  }

  public void toggle() {
    switch (globalMacroState.getMacroState()) {
      case STOPPED:
        log.debug("Starting to record");
        start();
        break;
      case RECORDING:
        log.debug("Stopping recording");
        stop();
        break;
      case PLAYING:
      default:
        log.warn("Cannot Start or Stop while we are Recording: Manually stop the recording first");
    }
  }

  public void start() {
    macroActionList.clear();
    this.startTimeInMs = System.currentTimeMillis();
    globalMacroState.changeToRecording();
    startRecording();
  }

  void startRecording() {
    isRunning = true;
  }

  public void stop() {
    stopRecording();
    previousMacroAction = null;
    globalMacroState.changeToStopped();
    removeShortcutsFromRecording();
  }

  private void removeShortcutsFromRecording() {
    List<MacroAction> macroActionsToDelete = macroActionList.stream()
        .filter(doesContainsAnyShortcuts())
        .collect(Collectors.toList());
    macroActionList.removeAll(macroActionsToDelete);
  }

  private Predicate<MacroAction> doesContainsAnyShortcuts() {
    return macroAction -> (macroAction.getRawCode() == KeyEvent.VK_F9
        || macroAction.getRawCode() == KeyEvent.VK_F10);
  }

  void stopRecording() {
    isRunning = false;
  }

  public boolean isRunning() {
    return isRunning;
  }
}

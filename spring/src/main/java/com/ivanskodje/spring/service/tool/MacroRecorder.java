package com.ivanskodje.spring.service.tool;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
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
public class MacroRecorder {

  private final GlobalMacroState globalMacroState;
  private final MacroKeyListener macroKeyListener;
  private MacroAction previousMacroAction = null;
  @Getter
  @Setter
  private List<MacroAction> macroActionList = new ArrayList<>();
  @Setter
  private Long startTimeInMs;

  public MacroRecorder(GlobalMacroState globalMacroState) {
    this.globalMacroState = globalMacroState;
    this.macroKeyListener = new MacroKeyListener(this);
  }

  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeKeyEvent) {
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

  @OnlyPressOnce
  public void released(NativeKeyEvent nativeKeyEvent) {
    recordKeyEvent(nativeKeyEvent);
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
    enableMacroKeyListener();
  }

  void enableMacroKeyListener() {
    GlobalScreen.addNativeKeyListener(macroKeyListener);
  }

  public void stop() {
    disableMacroKeyListener();
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

  void disableMacroKeyListener() {
    GlobalScreen.removeNativeKeyListener(macroKeyListener);
  }
}

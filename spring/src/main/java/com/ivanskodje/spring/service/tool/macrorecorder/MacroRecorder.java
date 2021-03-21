package com.ivanskodje.spring.service.tool.macrorecorder;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.ivanskodje.spring.service.action.BetterNativeKeyEvent;
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
public class MacroRecorder {

  private final GlobalMacroState globalMacroState;
  private boolean isKeyboardEventsActive = false;
  private boolean isMouseEventsActive = false;
  private MacroAction previousMacroAction = null;
  @Getter
  @Setter
  private List<MacroAction> macroActionList = new ArrayList<>();
  @Setter
  private Long startTimeInMs;


  public MacroRecorder(GlobalMacroState globalMacroState) {
    this.globalMacroState = globalMacroState;
  }

  public void pressed(NativeKeyEvent nativeKeyEvent) {
    BetterNativeKeyEvent betterNativeKeyEvent = new BetterNativeKeyEvent(nativeKeyEvent);
    recordKeyEvent(betterNativeKeyEvent);
  }

  private void recordKeyEvent(BetterNativeKeyEvent nativeKeyEvent) {
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

  public void pressedMouse(NativeMouseEvent nativeMouseEvent) {
    BetterNativeKeyEvent betterNativeKeyEvent = new BetterNativeKeyEvent(nativeMouseEvent);
    recordKeyEvent(betterNativeKeyEvent);
  }

  public void released(NativeKeyEvent nativeKeyEvent) {
    BetterNativeKeyEvent betterNativeKeyEvent = new BetterNativeKeyEvent(nativeKeyEvent);
    recordKeyEvent(betterNativeKeyEvent);
  }

  public void releasedMouse(NativeMouseEvent nativeMouseEvent) {
    BetterNativeKeyEvent betterNativeKeyEvent = new BetterNativeKeyEvent(nativeMouseEvent);
    recordKeyEvent(betterNativeKeyEvent);
  }

  public void toggle() {
    switch (globalMacroState.getMacroState()) {
      case STOPPED:
        log.debug("Starting to record (keyboard and mouse)");
        startKeyboardAndMouse();
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

  public void startKeyboard() {
    prepareForRecording();
    startKeyboardRecording();
  }

  void startKeyboardRecording() {
    isKeyboardEventsActive = true;
  }

  private void prepareForRecording() {
    macroActionList.clear();
    this.startTimeInMs = System.currentTimeMillis();
    globalMacroState.changeToRecording();
  }

  public void stop() {
    stopRecording();
    previousMacroAction = null;
    globalMacroState.changeToStopped();
    removeShortcutsFromRecording();
  }

  private void removeShortcutsFromRecording() {
    // TODO: Instead of going through the ENTIRE list, cut the beginning and end (that matches)
    List<MacroAction> macroActionsToDelete = macroActionList.stream()
        .filter(doesContainsAnyShortcuts())
        .collect(Collectors.toList());
    macroActionList.removeAll(macroActionsToDelete);
  }

  private Predicate<MacroAction> doesContainsAnyShortcuts() {
    return macroAction -> ((macroAction.getRawCode() != null) && (macroAction.getRawCode() == KeyEvent.VK_F9
        || macroAction.getRawCode() == KeyEvent.VK_F10));
  }

  void stopRecording() {
    isKeyboardEventsActive = false;
    isMouseEventsActive = false;
  }

  public boolean isKeyboardEventsActive() {
    return isKeyboardEventsActive;
  }

  public boolean isMouseEventsActive() {
    return isMouseEventsActive;
  }

  public void toggleMouse() {
    switch (globalMacroState.getMacroState()) {
      case STOPPED:
        log.debug("Starting to record MOUSE");
        startMouse();
        break;
      case RECORDING:
        log.debug("Stopping recording MOUSE");
        stop();
        break;
      case PLAYING:
      default:
        log.warn("Cannot Start or Stop while we are Recording: Manually stop the recording first");
    }
  }

  public void startMouse() {
    prepareForRecording();
    startMouseRecording();
  }

  void startMouseRecording() {
    isMouseEventsActive = true;
  }

  public void startKeyboardAndMouse() {
    prepareForRecording();
    startMouseRecording();
    startKeyboardRecording();
  }
}

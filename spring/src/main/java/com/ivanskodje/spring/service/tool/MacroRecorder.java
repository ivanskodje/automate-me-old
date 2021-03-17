package com.ivanskodje.spring.service.tool;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MacroRecorder {


  @Getter
  private final List<MacroAction> macroActionList = new ArrayList<>();
  private final MacroKeyListener macroKeyListener;
  @Setter
  private Long startTimeInMs;
  private MacroState macroState = MacroState.STOPPED;

  public MacroRecorder() {
    this.macroKeyListener = new MacroKeyListener(this);
  }

  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeKeyEvent) {
    if (!(nativeKeyEvent.getRawCode() == KeyEvent.VK_F9)) {
      recordKeyEvent(nativeKeyEvent);
    }
  }

  private void recordKeyEvent(NativeKeyEvent nativeKeyEvent) {
    Long delayInMs = System.currentTimeMillis() - startTimeInMs;
    MacroAction macroAction = new MacroAction(nativeKeyEvent, delayInMs);
    macroActionList.add(macroAction);
  }

  @OnlyPressOnce
  public void released(NativeKeyEvent nativeKeyEvent) {
    recordKeyEvent(nativeKeyEvent);
  }

  public void toggle() {
    switch (macroState) {
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
    this.macroState = MacroState.RECORDING;
    enableMacroKeyListener();
  }

  void enableMacroKeyListener() {
    GlobalScreen.addNativeKeyListener(macroKeyListener);
  }

  public void stop() {
    disableMacroKeyListener();
    this.macroState = MacroState.STOPPED;
  }

  void disableMacroKeyListener() {
    GlobalScreen.removeNativeKeyListener(macroKeyListener);
  }

  public MacroState getMacroState() {
    return macroState;
  }
}

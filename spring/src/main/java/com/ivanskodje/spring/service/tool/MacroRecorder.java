package com.ivanskodje.spring.service.tool;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
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

  @Setter
  private Long startTimeInMs;
  private MacroKeyListener macroKeyListener;
  private MacroState macroState = MacroState.STOPPED;

  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeKeyEvent) {
    recordKeyEvent(nativeKeyEvent);
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
      case RECORDING:
        log.debug("Stopping recording");
        stop();
      case PLAYING:
      default:
        log.warn("Cannot start or stop while we already are recording");
    }
  }

  public void start() {
    this.startTimeInMs = System.currentTimeMillis();
    this.macroKeyListener = new MacroKeyListener(this);
    GlobalScreen.addNativeKeyListener(macroKeyListener);
    this.macroState = MacroState.RECORDING;
  }

  public void stop() {
    GlobalScreen.removeNativeKeyListener(macroKeyListener);
    this.macroState = MacroState.STOPPED;
  }

  public MacroState getMacroState() {
    return macroState;
  }
}

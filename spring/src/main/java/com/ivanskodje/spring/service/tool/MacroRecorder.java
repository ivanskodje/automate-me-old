package com.ivanskodje.spring.service.tool;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.OnlyPressOnce;
import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
import com.ivanskodje.spring.service.action.MacroAction;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class MacroRecorder {

  @Getter
  private final List<MacroAction> macroActionList = new ArrayList<>();

  @Setter
  private Long startTimeInMs;
  private MacroKeyListener macroKeyListener;

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

  public void start() {
    this.startTimeInMs = System.currentTimeMillis();
    this.macroKeyListener = new MacroKeyListener(this);
    GlobalScreen.addNativeKeyListener(macroKeyListener);
  }

  public void stop() {
    GlobalScreen.removeNativeKeyListener(macroKeyListener);
  }
}

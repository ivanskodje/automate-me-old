package com.ivanskodje.spring.service.tool.listener;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.ivanskodje.spring.service.tool.MacroRecorder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MacroKeyListener implements NativeKeyListener {

  @Getter
  private MacroRecorder macroRecorder;

  public MacroKeyListener(MacroRecorder macroRecorder) {
    this.macroRecorder = macroRecorder;
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    macroRecorder.pressed(nativeKeyEvent);
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    macroRecorder.released(nativeKeyEvent);
  }

}

package com.ivanskodje.spring.service.macro;

import com.github.kwhat.jnativehook.GlobalScreen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MacroActionRunner implements Runnable {

  private final MacroAction macroAction;

  @Override
  public void run() {
    GlobalScreen.postNativeEvent(macroAction.getNativeKeyEvent());
  }
}
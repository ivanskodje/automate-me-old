package com.ivanskodje.spring.service.macro;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.kwhat.jnativehook.GlobalScreen;

@Slf4j
@RequiredArgsConstructor
public class MacroActionRunner implements Runnable {

  private final MacroAction macroAction;

  @Override
  public void run() {
    log.info("{}: {}", macroAction.getKeyName(), macroAction.getActionEvent());
    GlobalScreen.postNativeEvent(macroAction.getNativeKeyEvent());
  }
}

// TODO: Disable the horrible jnativehook error logging
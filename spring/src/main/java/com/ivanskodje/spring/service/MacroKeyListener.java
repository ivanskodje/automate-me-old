package com.ivanskodje.spring.service;

import com.ivanskodje.spring.service.macro.MacroAction;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

@Slf4j
public class MacroKeyListener implements NativeKeyListener {

  @Getter
  private final List<MacroAction> macroActionList = new ArrayList<>();
  private final List<Integer> pressedRawCodeList = new ArrayList<>();

  @Override
  public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    log.error("(Something got typed?): {}", nativeKeyEvent.paramString());
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    if (keyEventIsNotAlreadyPressed(nativeKeyEvent)) {
      pressedRawCodeList.add(nativeKeyEvent.getRawCode());
      recordMacroAction(nativeKeyEvent);
    }
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    removePressedKey(nativeKeyEvent);
    recordMacroAction(nativeKeyEvent);
  }

  private void removePressedKey(NativeKeyEvent nativeKeyEvent) {
    if (pressedRawCodeList.contains(nativeKeyEvent.getRawCode())) {
      pressedRawCodeList.remove((Object) nativeKeyEvent.getRawCode());
    }
  }

  private void recordMacroAction(NativeKeyEvent nativeKeyEvent) {
    MacroAction macroAction = new MacroAction(nativeKeyEvent);
    macroActionList.add(macroAction);
  }

  private boolean keyEventIsNotAlreadyPressed(NativeKeyEvent nativeKeyEvent) {
    return !pressedRawCodeList.contains(nativeKeyEvent.getRawCode());
  }
}

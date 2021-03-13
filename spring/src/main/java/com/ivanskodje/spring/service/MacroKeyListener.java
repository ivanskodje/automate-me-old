package com.ivanskodje.spring.service;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.ivanskodje.spring.service.macro.MacroAction;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MacroKeyListener implements NativeKeyListener {

  @Getter
  private final List<MacroAction> macroActionList = new ArrayList<>();
  private final List<Integer> pressedRawCodeList = new ArrayList<>();
  private final Long startTimeInMs;

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
    Long delayInMs = System.currentTimeMillis() - startTimeInMs;
    MacroAction macroAction = new MacroAction(nativeKeyEvent, delayInMs);
    macroActionList.add(macroAction);
  }

  private boolean keyEventIsNotAlreadyPressed(NativeKeyEvent nativeKeyEvent) {
    return !pressedRawCodeList.contains(nativeKeyEvent.getRawCode());
  }
}

package com.ivanskodje.spring.service.testhelp;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.action.MacroAction;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Super class for tests
 */
public class TestKeyPressing {

  @Setter
  private Long delayInMs = 1L;

  public List<MacroAction> write(String text) {
    List<MacroAction> macroActionList = new ArrayList<>();
    for (Character charCode : text.toCharArray()) {
      int keyCode = KeyEvent.getExtendedKeyCodeForChar(charCode);
      MacroAction macroActionPressed = buildMacroActionKeyPress(keyCode);
      MacroAction macroActionReleased = buildMacroActionKeyRelease(keyCode);
      macroActionList.add(macroActionPressed);
      macroActionList.add(macroActionReleased);
    }
    return macroActionList;
  }

  public MacroAction buildMacroActionKeyPress(int keyEvent) {
    return new MacroAction(buildNativeKeyEvent(keyEvent, NativeKeyEvent.NATIVE_KEY_PRESSED),
        delayInMs);
  }

  public NativeKeyEvent buildNativeKeyEvent(int keyEvent, int nativeKeyEventId) {
    return new NativeKeyEvent(nativeKeyEventId, 0, keyEvent, 0, ' ');
  }

  public MacroAction buildMacroActionKeyRelease(int keyEvent) {

    return new MacroAction(buildNativeKeyEvent(keyEvent, NativeKeyEvent.NATIVE_KEY_RELEASED),
        delayInMs);
  }
}

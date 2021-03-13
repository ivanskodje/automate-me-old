package com.ivanskodje.spring.service.testhelp;

import com.ivanskodje.spring.service.macro.ActionEvent;
import com.ivanskodje.spring.service.macro.MacroAction;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

/**
 * Super class for tests
 */
public class TestKeyPressing {

  public MacroAction buildMacroActionKeyPress(int keyEvent) {
    return new MacroAction(buildNativeKeyEvent(keyEvent, ActionEvent.KEY_PRESSED));
  }

  public NativeKeyEvent buildNativeKeyEvent(int keyEvent, ActionEvent actionEvent) {
    return new NativeKeyEvent(actionEvent.getKeyCode(), 0, keyEvent, 0, ' ');
  }

  public MacroAction buildMacroActionKeyRelease(int keyEvent) {
    return new MacroAction(buildNativeKeyEvent(keyEvent, ActionEvent.KEY_RELEASE));
  }

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
}

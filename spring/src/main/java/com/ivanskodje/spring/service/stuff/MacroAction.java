package com.ivanskodje.spring.service.stuff;

import java.awt.event.KeyEvent;
import lombok.Getter;
import org.jnativehook.keyboard.NativeKeyEvent;

@Getter
public class MacroAction {

  private final Integer keyEventCode;
  private final Long timeOfEventInMs;
  private final ActionEvent actionEvent;

  public MacroAction(NativeKeyEvent nativeKeyEvent) {
    this.keyEventCode = nativeKeyEvent.getRawCode();
    this.timeOfEventInMs = System.currentTimeMillis();
    final int keyCode = nativeKeyEvent.getID();
    this.actionEvent = ActionEvent.getActionEvent(keyCode);
  }

  public String getKeyName() {
    return KeyEvent.getKeyText(keyEventCode);
  }
}

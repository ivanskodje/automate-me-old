package com.ivanskodje.spring.service.macro;

import java.awt.event.KeyEvent;
import lombok.Getter;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

@Getter
public class MacroAction {

  private final Integer keyCode;
  private final Long timeOfEventInMs; // store delay directly!
  private final ActionEvent actionEvent;
  private final NativeKeyEvent nativeKeyEvent;

  public MacroAction(NativeKeyEvent nativeKeyEvent) {
    this.keyCode = nativeKeyEvent.getRawCode();
    this.timeOfEventInMs = System.currentTimeMillis();

    final int keyCode = nativeKeyEvent.getID();
    this.actionEvent = ActionEvent.getActionEvent(keyCode);
    // TODO: Consider removing keycode and action event
    this.nativeKeyEvent = nativeKeyEvent;

  }

  public String getKeyName() {
    return KeyEvent.getKeyText(keyCode);
  }

  @Override
  public String toString() {
    return KeyEvent.getKeyText(keyCode);
  }
}

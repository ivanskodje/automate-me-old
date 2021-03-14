package com.ivanskodje.spring.service.action;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.awt.event.KeyEvent;
import lombok.Getter;

@Getter
public class MacroAction {

  private final Long delayInMs;
  private final NativeKeyEvent nativeKeyEvent;

  public MacroAction(NativeKeyEvent nativeKeyEvent, Long delay) {
    this.delayInMs = delay;
    this.nativeKeyEvent = nativeKeyEvent;
  }

  @Override
  public String toString() {
    return getKeyName();
  }

  public String getKeyName() {
    return KeyEvent.getKeyText(nativeKeyEvent.getRawCode());
  }
}

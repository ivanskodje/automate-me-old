package com.ivanskodje.spring.service.action;

import com.github.kwhat.jnativehook.NativeInputEvent;
import java.awt.event.KeyEvent;
import lombok.Getter;

@Getter
public class MacroAction {

  private final Long delayInMs;
  private final Long time;
  private final BetterNativeKeyEvent betterNativeKeyEvent;

  public MacroAction(BetterNativeKeyEvent betterNativeKeyEvent, Long delay, Long startTime) {
    this.delayInMs = delay;
    this.betterNativeKeyEvent = betterNativeKeyEvent;
    this.time = startTime;
  }

  @Override
  public String toString() {
    return getKeyName();
  }

  public String getKeyName() {
    return KeyEvent.getKeyText(betterNativeKeyEvent.getRawCode());
  }

  public Integer getRawCode() {
    return betterNativeKeyEvent.getRawCode();
  }

  public NativeInputEvent getNativeEvent() {
    return betterNativeKeyEvent.getNativeEvent();
  }
}

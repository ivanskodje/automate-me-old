package com.ivanskodje.spring.service.action;

import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BetterNativeKeyEvent {

  private NativeKeyEvent nativeKeyEvent;
  private NativeMouseEvent nativeMouseEvent;

  public BetterNativeKeyEvent(NativeKeyEvent nativeKeyEvent) {
    this.nativeKeyEvent = nativeKeyEvent;
  }

  public BetterNativeKeyEvent(NativeMouseEvent nativeMouseEvent) {
    this.nativeMouseEvent = nativeMouseEvent;
  }

  public Integer getRawCode() {
    if (isKeyEvent()) {
      return nativeKeyEvent.getRawCode();
    }
    return null;
  }

  public boolean isKeyEvent() {
    return nativeKeyEvent != null;
  }

  public int getID() {
    return getNativeEvent().getID();
  }

  public NativeInputEvent getNativeEvent() {
    if (isKeyEvent()) {
      return nativeKeyEvent;
    }
    if (isMouseEvent()) {
      return nativeMouseEvent;
    }
    return null;
  }

  public boolean isMouseEvent() {
    return nativeKeyEvent != null;
  }
}

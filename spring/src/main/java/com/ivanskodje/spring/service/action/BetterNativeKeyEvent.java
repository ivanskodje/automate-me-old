package com.ivanskodje.spring.service.action;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import java.awt.event.KeyEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    log.error("NativeInputEvent was null!");
    throw new RuntimeException("Something went very wrong. Find the cause!!");
  }

  public boolean isKeyEvent() {
    return nativeKeyEvent != null;
  }

  public boolean isMouseEvent() {
    return nativeMouseEvent != null;
  }

  @Override
  public String toString() {
    if (isKeyEvent()) {
      return KeyEvent.getKeyText(getRawCode());
    }
    if (isMouseEvent()) {

      if (NativeMouseEvent.NATIVE_MOUSE_PRESSED == nativeMouseEvent.getID()) {
        return "NATIVE_MOUSE_PRESSED";
      }
      if (NativeMouseEvent.NATIVE_MOUSE_RELEASED == nativeMouseEvent.getID()) {
        return "NATIVE_MOUSE_RELEASED";
      }
    }

    throw new RuntimeException("Error in toString(), ffix it!");
  }

  public Integer getRawCode() {
    if (isKeyEvent()) {
      return nativeKeyEvent.getRawCode();
    }
    return null;
  }
}

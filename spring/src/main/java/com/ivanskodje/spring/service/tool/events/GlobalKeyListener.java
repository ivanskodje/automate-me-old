package com.ivanskodje.spring.service.tool.events;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalKeyListener implements NativeKeyListener, NativeMouseInputListener {

  private final MouseEventPublisher mouseEventPublisher;
  private final KeyboardEventPublisher keyboardEventPublisher;

  public GlobalKeyListener(KeyboardEventPublisher keyboardEventPublisher,
      MouseEventPublisher mouseEventPublisher) {
    this.keyboardEventPublisher = keyboardEventPublisher;
    this.mouseEventPublisher = mouseEventPublisher;
    GlobalScreen.addNativeKeyListener(this);
    GlobalScreen.addNativeMouseListener(this);
    GlobalScreen.addNativeMouseMotionListener(this);
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    keyboardEventPublisher.pressed(nativeKeyEvent);
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    keyboardEventPublisher.released(nativeKeyEvent);
  }

  @Override
  public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
    mouseEventPublisher.pressed(nativeMouseEvent);
  }

  @Override
  public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
    mouseEventPublisher.released(nativeMouseEvent);
  }

  @Override
  public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
    mouseEventPublisher.pressed(nativeMouseEvent);
  }

  @Override
  public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
    mouseEventPublisher.pressed(nativeMouseEvent);
  }
}

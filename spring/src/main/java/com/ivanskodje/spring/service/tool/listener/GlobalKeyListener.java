package com.ivanskodje.spring.service.tool.listener;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.ivanskodje.spring.service.tool.KeyShortcutResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalKeyListener implements NativeKeyListener {

  private final KeyShortcutResponder keyShortcutResponder;

  public GlobalKeyListener(KeyShortcutResponder keyShortcutResponder) {
    this.keyShortcutResponder = keyShortcutResponder;
    GlobalScreen.addNativeKeyListener(this);
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
    keyShortcutResponder.pressed(nativeEvent);
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
    keyShortcutResponder.released(nativeEvent);
  }

}

package com.ivanskodje.spring.service.tool.listener;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.ivanskodje.spring.service.tool.listener.publisher.KeyPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalKeyListener implements NativeKeyListener {

  private final KeyPublisher keyPublisher;

  public GlobalKeyListener(KeyPublisher keyPublisher) {
    this.keyPublisher = keyPublisher;
    GlobalScreen.addNativeKeyListener(this);
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
    keyPublisher.pressed(nativeEvent);
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
    keyPublisher.released(nativeEvent);
  }

}

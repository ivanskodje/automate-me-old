package com.ivanskodje.spring.service.tool.listener.publisher.subscriber;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.KeyboardMacroService;
import java.awt.event.KeyEvent;
import org.springframework.stereotype.Component;


@Component
public class ShortcutSubscriber implements KeySubscriber {

  private final KeyboardMacroService keyboardMacroService;

  public ShortcutSubscriber(KeyboardMacroService keyboardMacroService) {
    this.keyboardMacroService = keyboardMacroService;
//    keyPublisher.subscribe();
  }

  @Override
  public void pressed(NativeKeyEvent nativeKeyEvent) {

  }

  @Override
  public void released(NativeKeyEvent nativeKeyEvent) {
    switch (nativeKeyEvent.getRawCode()) {
      case KeyEvent.VK_F9:
        keyboardMacroService.toggleRecording();
        break;
      case KeyEvent.VK_F10:
        keyboardMacroService.togglePlayback();
        break;
    }
  }
}

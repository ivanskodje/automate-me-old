package com.ivanskodje.spring.service.tool.events;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.KeyboardMacroService;
import java.awt.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ShortcutEvent {

  private final KeyboardMacroService keyboardMacroService;

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

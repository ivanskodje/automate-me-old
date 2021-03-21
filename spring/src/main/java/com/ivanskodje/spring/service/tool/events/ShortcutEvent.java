package com.ivanskodje.spring.service.tool.events;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.KeyboardMacroService;
import com.ivanskodje.spring.service.MouseMacroService;
import java.awt.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ShortcutEvent {

  private final MouseMacroService mouseMacroService;
  private final KeyboardMacroService keyboardMacroService;

  public void released(NativeKeyEvent nativeKeyEvent) {
    switch (nativeKeyEvent.getRawCode()) {
      case KeyEvent.VK_F9:
        mouseMacroService.toggleRecording();
        break;
      case KeyEvent.VK_F10:
        mouseMacroService.togglePlayback();
        break;
    }
  }
}

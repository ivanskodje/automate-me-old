package com.ivanskodje.spring.service.tool.listener.publisher.subscriber;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.MacroRunnerService;
import com.ivanskodje.spring.service.tool.listener.publisher.KeyPublisher;
import java.awt.event.KeyEvent;
import org.springframework.stereotype.Component;


@Component
public class ShortcutSubscriber implements KeySubscriber {

  private final MacroRunnerService macroRunnerService;

  public ShortcutSubscriber(MacroRunnerService macroRunnerService) {
    this.macroRunnerService = macroRunnerService;
//    keyPublisher.subscribe();
  }

  @Override
  public void pressed(NativeKeyEvent nativeKeyEvent) {

  }

  @Override
  public void released(NativeKeyEvent nativeKeyEvent) {
    switch (nativeKeyEvent.getRawCode()) {
      case KeyEvent.VK_F9:
        macroRunnerService.toggleRecording();
        break;
      case KeyEvent.VK_F10:
        macroRunnerService.togglePlayRecording();
        break;
    }
  }
}

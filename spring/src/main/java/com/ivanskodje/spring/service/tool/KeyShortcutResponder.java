package com.ivanskodje.spring.service.tool;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.MacroRunnerService;
import java.awt.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyShortcutResponder {

  private final MacroRunnerService macroRunnerService;

  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeEvent) {

  }

  @OnlyPressOnce
  public void released(NativeKeyEvent nativeEvent) {
    switch (nativeEvent.getRawCode()) {
      case KeyEvent.VK_F9:
        macroRunnerService.toggleRecording();
        break;
      case KeyEvent.VK_F10:
        macroRunnerService.togglePlayRecording();
        break;
    }
  }
}

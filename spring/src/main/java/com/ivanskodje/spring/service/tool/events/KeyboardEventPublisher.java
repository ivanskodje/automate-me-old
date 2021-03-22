package com.ivanskodje.spring.service.tool.events;

import org.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.tool.macrorecorder.MacroRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyboardEventPublisher {

  private final ShortcutEvent shortcutEvent;
  private final MacroRecorder macroRecorder;


  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeKeyEvent) {
    if (macroRecorder.isKeyboardEventsActive()) {
      macroRecorder.pressed(nativeKeyEvent);
    }
  }

  @OnlyPressOnce
  public void released(NativeKeyEvent nativeKeyEvent) {
    shortcutEvent.released(nativeKeyEvent);

    if (macroRecorder.isKeyboardEventsActive()) {
      macroRecorder.released(nativeKeyEvent);
    }
  }
}

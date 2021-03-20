package com.ivanskodje.spring.service.tool.events;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.ivanskodje.spring.service.tool.macrorecorder.MacroRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MouseEventPublisher {

  private final MacroRecorder macroRecorder;

  public void pressed(NativeMouseEvent nativeEvent) {
    if (macroRecorder.isKeyboardEventsActive()) {
      macroRecorder.pressedMouse(nativeEvent);
    }
  }

  public void released(NativeMouseEvent nativeEvent) {
    if (macroRecorder.isKeyboardEventsActive()) {
      macroRecorder.releasedMouse(nativeEvent);
    }
  }
}

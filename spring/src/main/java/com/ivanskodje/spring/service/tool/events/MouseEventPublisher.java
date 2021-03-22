package com.ivanskodje.spring.service.tool.events;

import org.jnativehook.mouse.NativeMouseEvent;
import com.ivanskodje.spring.service.tool.macrorecorder.MacroRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MouseEventPublisher {

  private final MacroRecorder macroRecorder;

  public void pressed(NativeMouseEvent nativeMouseEvent) {
//    log.info("Mouse Press X: {}, Y: {}, Point: {}", nativeMouseEvent.getX(), nativeMouseEvent.getY(),
//    nativeMouseEvent.getPoint());

    if (macroRecorder.isMouseEventsActive()) {
      macroRecorder.pressedMouse(nativeMouseEvent);
    }
  }

  public void released(NativeMouseEvent nativeMouseEvent) {
//    log.info("Mouse Release X: {}, Y: {}, Point: {}", nativeMouseEvent.getX(), nativeMouseEvent.getY(),
//    nativeMouseEvent.getPoint());
    if (macroRecorder.isMouseEventsActive()) {
      macroRecorder.releasedMouse(nativeMouseEvent);
    }
  }
}

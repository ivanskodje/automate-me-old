package com.ivanskodje.spring.service.tool.listener.publisher;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.tool.listener.publisher.subscriber.KeySubscriber;
import com.ivanskodje.spring.service.tool.listener.publisher.subscriber.MacroRecorderSubscriber;
import com.ivanskodje.spring.service.tool.listener.publisher.subscriber.ShortcutSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyPublisher {

  private final ShortcutSubscriber shortcutSubscriber;
  private final MacroRecorderSubscriber macroRecorderSubscriber;

  public void subscribe() {

  }

  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeEvent) {
    shortcutSubscriber.pressed(nativeEvent);

    if (macroRecorderSubscriber.isRunning()) {
      macroRecorderSubscriber.pressed(nativeEvent);
    }
  }

  @OnlyPressOnce
  public void released(NativeKeyEvent nativeEvent) {
    shortcutSubscriber.released(nativeEvent);

    if (macroRecorderSubscriber.isRunning()) {
      macroRecorderSubscriber.released(nativeEvent);
    }
  }
}

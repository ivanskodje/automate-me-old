package com.ivanskodje.spring.service.tool.listener.publisher.subscriber;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public interface KeySubscriber {

  void pressed(NativeKeyEvent nativeKeyEvent);

  void released(NativeKeyEvent nativeKeyEvent);
}

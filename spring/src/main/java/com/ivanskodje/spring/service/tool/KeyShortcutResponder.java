package com.ivanskodje.spring.service.tool;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.OnlyPressOnce;
import java.awt.event.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeyShortcutResponder {

  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeEvent) {
    // ignore?
  }

  @OnlyPressOnce
  public void released(NativeKeyEvent nativeEvent) {

//    switch (nativeEvent.getRawCode()) {
//      case KeyEvent.VK_F9:
//
//        break;
//      case KeyEvent.VK_F10:
//        break;
//    }
  }
}

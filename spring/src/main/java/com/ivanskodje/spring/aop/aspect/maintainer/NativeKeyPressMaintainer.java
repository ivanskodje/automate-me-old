package com.ivanskodje.spring.aop.aspect.maintainer;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NativeKeyPressMaintainer {

  @Getter
  private final Map<Integer, NativeKeyEvent> pressedNativeKeyEventMap = new ConcurrentHashMap<>();

  public Object maintain(ProceedingJoinPoint joinPoint, NativeKeyEvent nativeKeyEvent) throws Throwable {
    int rawCode = nativeKeyEvent.getRawCode();

    if (nativeKeyEvent.getID() == NativeKeyEvent.NATIVE_KEY_PRESSED) {
      if (!pressedNativeKeyEventMap.containsKey(rawCode)) {
        pressedNativeKeyEventMap.put(rawCode, nativeKeyEvent);
        return joinPoint.proceed();
      }
    }

    if (nativeKeyEvent.getID() == NativeKeyEvent.NATIVE_KEY_RELEASED) {
      pressedNativeKeyEventMap.remove(rawCode);
      return joinPoint.proceed();
    }

    return null;
  }

  public void releasePressedKeys() {
    for (NativeKeyEvent nativeKeyEvent : pressedNativeKeyEventMap.values()) {
      GlobalScreen.postNativeEvent(nativeKeyEvent);
    }
  }
}

package com.ivanskodje.spring.aop.aspect.maintainer;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NativeKeyPressMaintainer {

  @Getter
  private final List<Integer> pressedRawCodeList = new ArrayList<>(); // TODO: Consider if we can save NativeEvents directly

  public Object maintain(ProceedingJoinPoint joinPoint, NativeKeyEvent nativeKeyEvent) throws Throwable {
    int rawCode = nativeKeyEvent.getRawCode();

    if (nativeKeyEvent.getID() == NativeKeyEvent.NATIVE_KEY_PRESSED) {
      if (!pressedRawCodeList.contains(rawCode)) {
        pressedRawCodeList.add(nativeKeyEvent.getRawCode());
        return joinPoint.proceed();
      }
    }

    if (nativeKeyEvent.getID() == NativeKeyEvent.NATIVE_KEY_RELEASED) {
      if (pressedRawCodeList.contains(rawCode)) {
        pressedRawCodeList.remove((Object) nativeKeyEvent.getRawCode());
        return joinPoint.proceed();
      }
    }

    return null;
  }
}

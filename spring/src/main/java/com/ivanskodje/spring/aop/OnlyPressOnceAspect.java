package com.ivanskodje.spring.aop;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OnlyPressOnceAspect {

  @Getter
  private final List<Integer> pressedRawCodeList = new ArrayList<>();

  @Around(value = "@annotation(onlyPressOnceAnnotation)")
  public Object onlyPressOnce(ProceedingJoinPoint joinPoint, OnlyPressOnce onlyPressOnceAnnotation)
      throws Throwable {

    if (hasNotArgument(joinPoint)) {
      return joinPoint.proceed();
    }

    Object arg = joinPoint.getArgs()[0];

    if (arg instanceof NativeKeyEvent) {
      NativeKeyEvent nativeKeyEvent = (NativeKeyEvent) arg;
      int rawCode = nativeKeyEvent.getRawCode();

      if (nativeKeyEvent.getID() == NativeKeyEvent.NATIVE_KEY_PRESSED) {
        if (!pressedRawCodeList.contains(rawCode)) {
          log.info("Pressed {}", KeyEvent.getKeyText(rawCode));
          pressedRawCodeList.add(nativeKeyEvent.getRawCode());
          return joinPoint.proceed();
        }
      } else if (nativeKeyEvent.getID() == NativeKeyEvent.NATIVE_KEY_RELEASED) {
        if (pressedRawCodeList.contains(rawCode)) {
          log.info("Release {}", KeyEvent.getKeyText(rawCode));
          pressedRawCodeList.remove((Object) nativeKeyEvent.getRawCode());
          return joinPoint.proceed();
        }
      }
    }
    return null;
  }

  private boolean hasNotArgument(ProceedingJoinPoint joinPoint) {
    return joinPoint.getArgs() == null || joinPoint.getArgs().length == 0;
  }

}

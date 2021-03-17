package com.ivanskodje.spring.aop.aspect;

import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.maintainer.NativeKeyPressMaintainer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OnlyPressOnceAspect {

  private final NativeKeyPressMaintainer nativeKeyPressMaintainer;


  @Around(value = "@annotation(onlyPressOnceAnnotation)")
  public Object onlyPressOnce(ProceedingJoinPoint joinPoint, OnlyPressOnce onlyPressOnceAnnotation)
      throws Throwable {
    validateAnnotationPlacement(joinPoint);

    Object arg = joinPoint.getArgs()[0];
    if (arg instanceof NativeKeyEvent) {
      return nativeKeyPressMaintainer.maintain(joinPoint, (NativeKeyEvent) arg);
    } else {
      return null;
    }
  }


  private void validateAnnotationPlacement(ProceedingJoinPoint joinPoint) {
    if (!hasArgument(joinPoint)) {
      throw new UnsupportedOperationException(
          "@OnlyPressOnce annotation was misplaced on a method without an argument");
    }

    if (!hasNativeInputEventArgument(joinPoint)) {
      throw new UnsupportedOperationException(
          "@OnlyPressOnce annotation was not put on a method that has a class or subclass of NativeInputEvent.class");
    }
  }

  private boolean hasNativeInputEventArgument(ProceedingJoinPoint joinPoint) {
    return joinPoint.getArgs()[0].getClass().getSuperclass().equals(NativeInputEvent.class) || (joinPoint
        .getArgs()[0] instanceof NativeInputEvent);
  }

  private boolean hasArgument(ProceedingJoinPoint joinPoint) {
    return joinPoint.getArgs() != null && joinPoint.getArgs().length > 0;
  }

  public List<Integer> getNativeKeyPressedRawCodeList() {
    return nativeKeyPressMaintainer.getPressedRawCodeList();
  }

}

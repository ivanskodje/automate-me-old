package com.ivanskodje.spring.aop;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnceAspect;
import com.ivanskodje.spring.aop.aspect.maintainer.NativeKeyPressMaintainer;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import java.awt.event.KeyEvent;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OnlyPressOnceAspectTest extends TestKeyPressing {

  private OnlyPressOnceAspect onlyPressOnceAspect;

  @Before
  public void before() {
    onlyPressOnceAspect = new OnlyPressOnceAspect(new NativeKeyPressMaintainer());
  }


  @Test
  public void testKeyPressedOnce_expectOneResult() throws Throwable {
    pressKey(KeyEvent.VK_I);

    Map<Integer, NativeKeyEvent> nativeKeyEventMap = onlyPressOnceAspect.getPressedNativeKeyEventMap();

    Assertions.assertThat(nativeKeyEventMap.get(KeyEvent.VK_I).getRawCode()).isEqualTo(KeyEvent.VK_I);
    Assertions.assertThat(nativeKeyEventMap).hasSize(1);
  }

  private void pressKey(int keyEvent) throws Throwable {
    ProceedingJoinPoint joinPoint = getProceedingJoinPoint(keyEvent);
    onlyPressOnceAspect.onlyPressOnce(joinPoint, null);
  }

  private ProceedingJoinPoint getProceedingJoinPoint(int keyEvent) {
    ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
    NativeKeyEvent nativeKeyEvent = buildNativeKeyEvent(keyEvent, NativeKeyEvent.NATIVE_KEY_PRESSED);
    Object[] args = {nativeKeyEvent};
    when(joinPoint.getArgs()).thenReturn(args);
    return joinPoint;
  }

  @Test
  public void testKeyPressedMultipleTimes_expectOneResult() throws Throwable {
    pressKey(KeyEvent.VK_I);
    pressKey(KeyEvent.VK_I);
    pressKey(KeyEvent.VK_I);
    pressKey(KeyEvent.VK_A);

    Map<Integer, NativeKeyEvent> pressedNativeKeyEventMap = onlyPressOnceAspect.getPressedNativeKeyEventMap();

    Assertions.assertThat(pressedNativeKeyEventMap.get(KeyEvent.VK_I).getRawCode()).isEqualTo(KeyEvent.VK_I);
    Assertions.assertThat(pressedNativeKeyEventMap.get(KeyEvent.VK_A).getRawCode()).isEqualTo(KeyEvent.VK_A);
    Assertions.assertThat(pressedNativeKeyEventMap).hasSize(2);
  }
}
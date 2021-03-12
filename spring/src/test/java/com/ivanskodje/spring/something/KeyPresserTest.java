package com.ivanskodje.spring.something;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.event.KeyEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyPresserTest {

  @Test
  public void test() {
    KeyPresser keyPresser = spy(KeyPresser.class);

    keyPresser.clickButton(KeyEvent.VK_SPACE);

    verify(keyPresser, times(1)).keyPress(anyInt());
    verify(keyPresser, times(1)).keyRelease(anyInt());

  }

}
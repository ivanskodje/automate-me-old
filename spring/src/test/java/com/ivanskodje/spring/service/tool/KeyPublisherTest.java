package com.ivanskodje.spring.service.tool;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.KeyboardMacroService;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import com.ivanskodje.spring.service.tool.listener.publisher.subscriber.ShortcutSubscriber;
import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyPublisherTest extends TestKeyPressing {


  @Mock
  private KeyboardMacroService keyboardMacroService;
  private ShortcutSubscriber shortcutSubscriber;

  @Before
  public void before() {
    this.shortcutSubscriber = new ShortcutSubscriber(keyboardMacroService);
  }

  @Test
  public void testToggleReachedWhenPressingF9() {
    NativeKeyEvent nativeKeyEvent = buildNativeKeyEvent(KeyEvent.VK_F9, NativeKeyEvent.NATIVE_KEY_RELEASED);

    shortcutSubscriber.released(nativeKeyEvent);

    verify(keyboardMacroService, times(1)).toggleRecording();
  }

  @Test
  public void testPlayRecordingReachedWhenPressingF10() {
    NativeKeyEvent nativeKeyEvent = buildNativeKeyEvent(KeyEvent.VK_F10, NativeKeyEvent.NATIVE_KEY_RELEASED);

    shortcutSubscriber.released(nativeKeyEvent);

    verify(keyboardMacroService, times(1)).togglePlayback();
  }
}
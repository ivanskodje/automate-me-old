package com.ivanskodje.spring.service.tool;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.KeyboardMacroService;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import com.ivanskodje.spring.service.tool.events.ShortcutEvent;
import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class KeyboardEventPublisherTest extends TestKeyPressing {


  @Mock
  private KeyboardMacroService keyboardMacroService;
  private ShortcutEvent shortcutEvent;

  @Before
  public void before() {
    this.shortcutEvent = new ShortcutEvent(null, keyboardMacroService);
  }

  @Test
  public void testToggleReachedWhenPressingF9() {
    NativeKeyEvent nativeKeyEvent = buildNativeKeyEvent(KeyEvent.VK_F9, NativeKeyEvent.NATIVE_KEY_RELEASED);

    shortcutEvent.released(nativeKeyEvent);

    verify(keyboardMacroService, times(1)).toggleRecording();
  }

  @Test
  public void testPlayRecordingReachedWhenPressingF10() {
    NativeKeyEvent nativeKeyEvent = buildNativeKeyEvent(KeyEvent.VK_F10, NativeKeyEvent.NATIVE_KEY_RELEASED);

    shortcutEvent.released(nativeKeyEvent);

    verify(keyboardMacroService, times(1)).togglePlayback();
  }
}
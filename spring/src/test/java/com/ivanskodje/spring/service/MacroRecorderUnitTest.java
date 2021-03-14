package com.ivanskodje.spring.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
import com.ivanskodje.spring.service.tool.MacroRecorder;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import java.awt.event.KeyEvent;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MacroRecorderUnitTest extends TestKeyPressing {

  private MacroKeyListener macroKeyListener;

  @Before
  public void before() {

  }

  @Test
  public void testKeyPressing() {
    MacroRecorder macroRecorder = new MacroRecorder();
    macroRecorder.setStartTimeInMs(System.currentTimeMillis());

    macroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorder.released(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_RELEASED));
    macroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorder.released(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_RELEASED));
    macroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorder.released(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_RELEASED));
    macroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorder.released(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_RELEASED));

    List<MacroAction> resultingMacroActionList = macroRecorder.getMacroActionList();

    assertThat(resultingMacroActionList)
        .filteredOn(
            macroAction -> macroAction.getNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_PRESSED)
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");

    assertThat(resultingMacroActionList)
        .filteredOn(
            macroAction -> macroAction.getNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_RELEASED)
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");
  }
}
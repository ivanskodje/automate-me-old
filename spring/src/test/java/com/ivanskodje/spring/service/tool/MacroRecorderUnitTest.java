package com.ivanskodje.spring.service.tool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
import java.awt.event.KeyEvent;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

  @Test
  public void testToggle_expectRecordingState() {
    MacroRecorder macroRecorder = Mockito.spy(MacroRecorder.class);

    doNothing().when(macroRecorder).enableMacroKeyListener();

    macroRecorder.toggle();

    assertThat(macroRecorder.getMacroState()).isEqualTo(MacroState.RECORDING);
  }

  @Test
  public void testToggle_expectStoppedState() {
    MacroRecorder macroRecorder = Mockito.spy(MacroRecorder.class);

    doNothing().when(macroRecorder).enableMacroKeyListener();
    doNothing().when(macroRecorder).disableMacroKeyListener();

    macroRecorder.toggle();
    macroRecorder.toggle();

    assertThat(macroRecorder.getMacroState()).isEqualTo(MacroState.STOPPED);
    verify(macroRecorder, times(1)).enableMacroKeyListener();
  }
}
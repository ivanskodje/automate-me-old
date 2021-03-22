package com.ivanskodje.spring.service.tool.macrorecorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import com.ivanskodje.spring.service.tool.GlobalMacroState;
import java.awt.event.KeyEvent;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MacroRecorderUnitTest extends TestKeyPressing {

  private MacroRecorder spyMacroRecorder;
  private GlobalMacroState globalMacroState;

  @Before
  public void before() {
    this.globalMacroState = new GlobalMacroState();
    this.spyMacroRecorder = spy(
        new MacroRecorder(globalMacroState));
  }

  @Test
  public void testKeyPressing() {
    MacroRecorder macroRecorder = new MacroRecorder(new GlobalMacroState());
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
            macroAction -> macroAction.getBetterNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_PRESSED)
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");

    assertThat(resultingMacroActionList)
        .filteredOn(
            macroAction -> macroAction.getBetterNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_RELEASED)
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");
  }

  @Test
  public void testToggle_expectRecordingState() {
    doNothing().when(spyMacroRecorder).startKeyboardRecording();

    spyMacroRecorder.toggle();

    assertTrue(globalMacroState.isRecording());
  }

  @Test
  public void testToggle_expectStoppedState() {
    doNothing().when(spyMacroRecorder).startKeyboardRecording();
    doNothing().when(spyMacroRecorder).stopRecording();

    spyMacroRecorder.toggle();
    spyMacroRecorder.toggle();

    assertTrue(globalMacroState.isStopped());
    verify(spyMacroRecorder, times(1)).startKeyboardRecording();
  }

  @Test
  public void testMacroActionPurificationAfterStoppingTheRecording_expectNoShortcutInList() {
    spyMacroRecorder.setStartTimeInMs(System.currentTimeMillis());

    NativeKeyEvent nativeKeyEventF9Release = buildNativeKeyEvent(KeyEvent.VK_F9,
        NativeKeyEvent.NATIVE_KEY_RELEASED);
    spyMacroRecorder.pressed(nativeKeyEventF9Release);
    spyMacroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorder
        .released(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorder
        .released(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorder
        .released(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorder
        .pressed(buildNativeKeyEvent(KeyEvent.VK_F10, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorder
        .pressed(buildNativeKeyEvent(KeyEvent.VK_F10, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorder.pressed(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorder
        .released(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorder
        .pressed(buildNativeKeyEvent(KeyEvent.VK_F9, NativeKeyEvent.NATIVE_KEY_PRESSED));

    doNothing().when(spyMacroRecorder).stopRecording();

    List<MacroAction> macroActionList = spyMacroRecorder.getMacroActionList();
    assertThat(macroActionList.get(0).getRawCode()).isEqualTo(KeyEvent.VK_F9);
    assertThat(macroActionList.get(7).getRawCode()).isEqualTo(KeyEvent.VK_F10);
    assertThat(macroActionList.get(8).getRawCode()).isEqualTo(KeyEvent.VK_F10);
    assertThat(macroActionList.get(11).getRawCode()).isEqualTo(KeyEvent.VK_F9);

    spyMacroRecorder.stop();

    assertThat(macroActionList).extracting(MacroAction::getRawCode).doesNotContain(KeyEvent.VK_F9)
        .doesNotContain(KeyEvent.VK_F10);
  }
}
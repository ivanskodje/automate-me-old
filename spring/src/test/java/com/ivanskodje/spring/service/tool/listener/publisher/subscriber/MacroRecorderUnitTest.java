package com.ivanskodje.spring.service.tool.listener.publisher.subscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
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

  private MacroRecorderSubscriber spyMacroRecorderSubscriber;
  private GlobalMacroState globalMacroState;

  @Before
  public void before() {
    this.globalMacroState = new GlobalMacroState();
    this.spyMacroRecorderSubscriber = spy(
        new MacroRecorderSubscriber(globalMacroState));
  }

  @Test
  public void testKeyPressing() {
    MacroRecorderSubscriber macroRecorderSubscriber = new MacroRecorderSubscriber(new GlobalMacroState());
    macroRecorderSubscriber.setStartTimeInMs(System.currentTimeMillis());

    macroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorderSubscriber.released(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_RELEASED));
    macroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorderSubscriber.released(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_RELEASED));
    macroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorderSubscriber.released(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_RELEASED));
    macroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_PRESSED));
    macroRecorderSubscriber.released(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_RELEASED));

    List<MacroAction> resultingMacroActionList = macroRecorderSubscriber.getMacroActionList();

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
    doNothing().when(spyMacroRecorderSubscriber).startRecording();

    spyMacroRecorderSubscriber.toggle();

    assertTrue(globalMacroState.isRecording());
  }

  @Test
  public void testToggle_expectStoppedState() {
    doNothing().when(spyMacroRecorderSubscriber).startRecording();
    doNothing().when(spyMacroRecorderSubscriber).stopRecording();

    spyMacroRecorderSubscriber.toggle();
    spyMacroRecorderSubscriber.toggle();

    assertTrue(globalMacroState.isStopped());
    verify(spyMacroRecorderSubscriber, times(1)).startRecording();
  }

  @Test
  public void testMacroActionPurificationAfterStoppingTheRecording_expectNoShortcutInList() {
    spyMacroRecorderSubscriber.setStartTimeInMs(System.currentTimeMillis());

    NativeKeyEvent nativeKeyEventF9Release = buildNativeKeyEvent(KeyEvent.VK_F9,
        NativeKeyEvent.NATIVE_KEY_RELEASED);
    spyMacroRecorderSubscriber.pressed(nativeKeyEventF9Release);
    spyMacroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorderSubscriber
        .released(buildNativeKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorderSubscriber
        .released(buildNativeKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorderSubscriber
        .released(buildNativeKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorderSubscriber
        .pressed(buildNativeKeyEvent(KeyEvent.VK_F10, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorderSubscriber
        .pressed(buildNativeKeyEvent(KeyEvent.VK_F10, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorderSubscriber.pressed(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_PRESSED));
    spyMacroRecorderSubscriber
        .released(buildNativeKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_RELEASED));
    spyMacroRecorderSubscriber
        .pressed(buildNativeKeyEvent(KeyEvent.VK_F9, NativeKeyEvent.NATIVE_KEY_PRESSED));

    doNothing().when(spyMacroRecorderSubscriber).stopRecording();

    List<MacroAction> macroActionList = spyMacroRecorderSubscriber.getMacroActionList();
    assertThat(macroActionList.get(0).getRawCode()).isEqualTo(KeyEvent.VK_F9);
    assertThat(macroActionList.get(7).getRawCode()).isEqualTo(KeyEvent.VK_F10);
    assertThat(macroActionList.get(8).getRawCode()).isEqualTo(KeyEvent.VK_F10);
    assertThat(macroActionList.get(11).getRawCode()).isEqualTo(KeyEvent.VK_F9);

    spyMacroRecorderSubscriber.stop();

    assertThat(macroActionList).extracting(MacroAction::getRawCode).doesNotContain(KeyEvent.VK_F9)
        .doesNotContain(KeyEvent.VK_F10);
  }
}
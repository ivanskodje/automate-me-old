package com.ivanskodje.spring.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.service.macro.MacroAction;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import java.awt.event.KeyEvent;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MacroKeyListenerTest extends TestKeyPressing {

  private MacroKeyListener macroKeyListener;

  @Before
  public void before() {

  }

  @Test
  public void testKeysPressedAreStored() {
    macroKeyListener = new MacroKeyListener(System.currentTimeMillis());

    simulateKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_RELEASED);
    simulateKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_V, NativeKeyEvent.NATIVE_KEY_RELEASED);
    simulateKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_A, NativeKeyEvent.NATIVE_KEY_RELEASED);
    simulateKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_N, NativeKeyEvent.NATIVE_KEY_RELEASED);

    List<MacroAction> macroActionList = macroKeyListener.getMacroActionList();
    assertThat(macroActionList)
        .filteredOn(
            macroAction -> macroAction.getNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_PRESSED)
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");

    assertThat(macroActionList)
        .filteredOn(
            macroAction -> macroAction.getNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_RELEASED)
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");

  }

  private void simulateKeyEvent(int keyEvent, int nativeKeyEventCode) {
    NativeKeyEvent nativeKeyEvent = buildNativeKeyEvent(keyEvent, nativeKeyEventCode);

    if (NativeKeyEvent.NATIVE_KEY_PRESSED == nativeKeyEventCode) {
      macroKeyListener.nativeKeyPressed(nativeKeyEvent);
    } else if (NativeKeyEvent.NATIVE_KEY_RELEASED == nativeKeyEventCode) {
      macroKeyListener.nativeKeyReleased(nativeKeyEvent);
    }
  }

  @Test
  public void testKeysPressedAreNotStoredTwiceBeforeReleased() {
    macroKeyListener = new MacroKeyListener(System.currentTimeMillis());

    simulateKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, NativeKeyEvent.NATIVE_KEY_RELEASED);

    List<MacroAction> macroActionList = macroKeyListener.getMacroActionList();

    assertThat(macroActionList)
        .filteredOn(
            macroAction -> macroAction.getNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_PRESSED)
        .hasSize(1)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I");

    assertThat(macroActionList)
        .filteredOn(
            macroAction -> macroAction.getNativeKeyEvent().getID() == NativeKeyEvent.NATIVE_KEY_RELEASED)
        .hasSize(1)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I");
  }
}
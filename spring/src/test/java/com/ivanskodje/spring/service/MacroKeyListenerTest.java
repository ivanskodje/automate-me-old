package com.ivanskodje.spring.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ivanskodje.spring.service.macro.ActionEvent;
import com.ivanskodje.spring.service.macro.MacroAction;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import java.awt.event.KeyEvent;
import java.util.List;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
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
    macroKeyListener = new MacroKeyListener();

    simulateKeyEvent(KeyEvent.VK_I, ActionEvent.KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, ActionEvent.KEY_RELEASE);
    simulateKeyEvent(KeyEvent.VK_V, ActionEvent.KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_V, ActionEvent.KEY_RELEASE);
    simulateKeyEvent(KeyEvent.VK_A, ActionEvent.KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_A, ActionEvent.KEY_RELEASE);
    simulateKeyEvent(KeyEvent.VK_N, ActionEvent.KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_N, ActionEvent.KEY_RELEASE);

    List<MacroAction> macroActionList = macroKeyListener.getMacroActionList();

    assertThat(macroActionList)
        .filteredOn(macroAction -> ActionEvent.KEY_PRESSED.equals(macroAction.getActionEvent()))
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");

    assertThat(macroActionList)
        .filteredOn(macroAction -> ActionEvent.KEY_RELEASE.equals(macroAction.getActionEvent()))
        .hasSize(4)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I", "V", "A", "N");

  }

  private void simulateKeyEvent(int keyEvent, ActionEvent actionEvent) {
    NativeKeyEvent nativeKeyEvent = buildNativeKeyEvent(keyEvent, actionEvent);

    if (ActionEvent.KEY_PRESSED.equals(actionEvent)) {
      macroKeyListener.nativeKeyPressed(nativeKeyEvent);
    } else if (ActionEvent.KEY_RELEASE.equals(actionEvent)) {
      macroKeyListener.nativeKeyReleased(nativeKeyEvent);
    }
  }

  @Test
  public void testKeysPressedAreNotStoredTwiceBeforeReleased() {
    macroKeyListener = new MacroKeyListener();

    simulateKeyEvent(KeyEvent.VK_I, ActionEvent.KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, ActionEvent.KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, ActionEvent.KEY_PRESSED);
    simulateKeyEvent(KeyEvent.VK_I, ActionEvent.KEY_RELEASE);

    List<MacroAction> macroActionList = macroKeyListener.getMacroActionList();

    assertThat(macroActionList)
        .filteredOn(macroAction -> ActionEvent.KEY_PRESSED.equals(macroAction.getActionEvent()))
        .hasSize(1)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I");

    assertThat(macroActionList)
        .filteredOn(macroAction -> ActionEvent.KEY_RELEASE.equals(macroAction.getActionEvent()))
        .hasSize(1)
        .extracting(MacroAction::getKeyName)
        .containsExactly("I");

  }
}
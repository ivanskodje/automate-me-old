package com.ivanskodje.spring.service;

import com.ivanskodje.spring.service.stuff.MacroAction;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

@Slf4j
public class MacroKeyListener implements NativeKeyListener {

  private final List<MacroAction> macroActionList = new ArrayList<>();

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    log.info("Pressed {}", nativeKeyEvent.paramString());
    MacroAction macroAction = new MacroAction(nativeKeyEvent);
    macroActionList.add(macroAction);
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    log.info("Release {}", nativeKeyEvent.paramString());
    MacroAction macroAction = new MacroAction(nativeKeyEvent);
    macroActionList.add(macroAction);
  }

  @Override
  public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    log.error("(Something got typed?): {}", nativeKeyEvent.paramString());
  }

  public List<MacroAction> getMacroActions() {
    return macroActionList;
  }
}

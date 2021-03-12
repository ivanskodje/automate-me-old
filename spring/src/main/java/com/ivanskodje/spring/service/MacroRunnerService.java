package com.ivanskodje.spring.service;

import com.ivanskodje.spring.service.stuff.MacroAction;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MacroRunnerService {

  private final MacroKeyListener macroKeyListener;

  public MacroRunnerService() throws NativeHookException {
    macroKeyListener = new MacroKeyListener();
    GlobalScreen.registerNativeHook();

    // TODO: Add a separate listener that starts/stops the service
  }

  public void startRecording() {
    GlobalScreen.addNativeKeyListener(macroKeyListener);
    GlobalScreen.

    // w is 17

    // start a listener

    // wait until a key is pressed

  }

  public void stopRecording() {

    GlobalScreen.removeNativeKeyListener(macroKeyListener);

    List<MacroAction> macroActionList = macroKeyListener.getMacroActions();

    for (MacroAction action : macroActionList) {
      log.debug("KEY: {}, ACTION: {}, TIME: {}", action.getKeyName(), action.getActionEvent().name(),
          action.getTimeOfEventInMs());
    }

    macroActionList.clear(); // todo: temp for quick testing
    // start a listener

    // wait until a key is pressed

  }
}
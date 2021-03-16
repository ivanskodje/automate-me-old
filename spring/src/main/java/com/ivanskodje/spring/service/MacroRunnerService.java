package com.ivanskodje.spring.service;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.runner.MacroActionRunner;
import com.ivanskodje.spring.service.tool.MacroRecorder;
import com.ivanskodje.spring.service.tool.MacroState;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MacroRunnerService {

  private final MacroRecorder macroRecorder;


  public MacroRunnerService() throws NativeHookException {
    GlobalScreen.registerNativeHook();
    macroRecorder = new MacroRecorder();
  }

  public void toggleRecording() {
    macroRecorder.toggle();
  }

  public void startRecording() {
    macroRecorder.start();
  }

  public void stopRecording() {
    macroRecorder.stop();
  }

  public void playRecording() { // TODO: Look into separating the responsibility into a class. We are currently asking the macro recorder for multiple "things" and is doing the work ourselves, we DONT want that.
    if (macroRecorder.getMacroState() == MacroState.STOPPED) {
      log.debug("Playing last recording");
      List<MacroAction> macroActionList = macroRecorder.getMacroActionList();
      ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

      for (MacroAction macroAction : macroActionList) {
        scheduleMacroAction(executorService, macroAction);
      }
    }
    log.debug("We are in state {}", macroRecorder.getMacroState().name());
  }

  void scheduleMacroAction(ScheduledExecutorService executorService, MacroAction macroAction) {
    executorService
        .schedule(new MacroActionRunner(macroAction), macroAction.getDelayInMs(), TimeUnit.MILLISECONDS);
  }

  public List<MacroAction> getMacroActionList() {
    return macroRecorder.getMacroActionList();
  }
}
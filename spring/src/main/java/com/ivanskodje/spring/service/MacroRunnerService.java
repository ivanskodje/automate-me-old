package com.ivanskodje.spring.service;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.ivanskodje.spring.service.macro.MacroAction;
import com.ivanskodje.spring.service.macro.MacroActionRunner;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MacroRunnerService {

  @Setter
  private MacroKeyListener macroKeyListener;

  @Setter
  private Long startTimeInMs;

  public MacroRunnerService() throws NativeHookException {
    this.macroKeyListener = new MacroKeyListener();
    GlobalScreen.registerNativeHook();
  }


  public void startRecording() {
    this.macroKeyListener = new MacroKeyListener(); // TODO: remove later, we dont want to clean every time we start recording?
    GlobalScreen.addNativeKeyListener(macroKeyListener);
    this.startTimeInMs = System.currentTimeMillis();
  }

  public void stopRecording() {
    GlobalScreen.removeNativeKeyListener(macroKeyListener);
  }

  public void playRecording() {
    List<MacroAction> macroActionList = macroKeyListener.getMacroActionList();
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    for (MacroAction macroAction : macroActionList) {
      long diffUntilMacroAction = macroAction.getTimeOfEventInMs() - startTimeInMs;
      scheduleMacroAction(executorService, macroAction, diffUntilMacroAction);
    }
  }

  void scheduleMacroAction(ScheduledExecutorService executorService, MacroAction macroAction,
      long diffUntilMacroAction) {
    executorService.schedule(new MacroActionRunner(macroAction), diffUntilMacroAction, TimeUnit.MILLISECONDS);
  }
}
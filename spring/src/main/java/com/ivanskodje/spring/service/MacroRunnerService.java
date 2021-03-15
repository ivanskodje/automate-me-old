package com.ivanskodje.spring.service;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.runner.MacroActionRunner;
import com.ivanskodje.spring.service.tool.MacroRecorder;
import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
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

  private final MacroRecorder macroRecorder;
  @Setter
  private MacroKeyListener macroKeyListener;

  public MacroRunnerService() throws NativeHookException {
    GlobalScreen.registerNativeHook();
    macroRecorder = new MacroRecorder();
  }

  public void startRecording() {
    macroRecorder.start();
  }

  public void stopRecording() {
    macroRecorder.stop();
  }

  public void playRecording() {
    List<MacroAction> macroActionList = macroRecorder.getMacroActionList();
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    for (MacroAction macroAction : macroActionList) {
      scheduleMacroAction(executorService, macroAction);
    }
  }

  void scheduleMacroAction(ScheduledExecutorService executorService, MacroAction macroAction) {
    executorService
        .schedule(new MacroActionRunner(macroAction), macroAction.getDelayInMs(), TimeUnit.MILLISECONDS);
  }
}
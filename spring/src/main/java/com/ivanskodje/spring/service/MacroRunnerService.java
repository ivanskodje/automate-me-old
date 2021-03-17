package com.ivanskodje.spring.service;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.MacroPlayer;
import com.ivanskodje.spring.service.tool.MacroRecorder;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MacroRunnerService {

  private final MacroRecorder macroRecorder;
  private final MacroPlayer macroPlayer;

  public MacroRunnerService(MacroRecorder macroRecorder, MacroPlayer macroPlayer)
      throws NativeHookException {
    GlobalScreen.registerNativeHook();
    this.macroRecorder = macroRecorder;
    this.macroPlayer = macroPlayer;
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

  public void togglePlayRecording() {
    List<MacroAction> macroActionList = macroRecorder.getMacroActionList();
    macroPlayer.play(macroActionList);
  }

  public List<MacroAction> getMacroActionList() {
    return macroRecorder.getMacroActionList();
  }
}
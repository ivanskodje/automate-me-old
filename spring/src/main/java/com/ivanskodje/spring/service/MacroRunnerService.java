package com.ivanskodje.spring.service;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.macroplayer.MacroPlayer;
import com.ivanskodje.spring.service.tool.listener.publisher.subscriber.MacroRecorderSubscriber;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MacroRunnerService {

  private final MacroRecorderSubscriber macroRecorderSubscriber;
  private final MacroPlayer macroPlayer;

  public MacroRunnerService(MacroRecorderSubscriber macroRecorderSubscriber, MacroPlayer macroPlayer)
      throws NativeHookException {
    GlobalScreen.registerNativeHook();
    this.macroRecorderSubscriber = macroRecorderSubscriber;
    this.macroPlayer = macroPlayer;
  }

  public void toggleRecording() {
    macroRecorderSubscriber.toggle();
  }

  public void startRecording() {
    macroRecorderSubscriber.start();
  }

  public void stopRecording() {
    macroRecorderSubscriber.stop();
  }

  public void togglePlayRecording() {
    List<MacroAction> macroActionList = macroRecorderSubscriber.getMacroActionList();
    macroPlayer.togglePlay(macroActionList);
  }

  public List<MacroAction> getMacroActionList() {
    return macroRecorderSubscriber.getMacroActionList();
  }
}
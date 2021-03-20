package com.ivanskodje.spring.service;

import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.macrorecorder.MacroRecorder;
import com.ivanskodje.spring.service.tool.macroplayer.MacroPlayer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeyboardMacroService implements MacroService {

  private final MacroRecorder macroRecorder;
  private final MacroPlayer macroPlayer;

  public KeyboardMacroService(MacroRecorder macroRecorder, MacroPlayer macroPlayer) {
    this.macroRecorder = macroRecorder;
    this.macroPlayer = macroPlayer;
  }

  @Override
  public void startRecording() {
    macroRecorder.startKeyboard();
  }

  @Override
  public void stopRecording() {
    macroRecorder.stop();
  }

  public void toggleRecording() {
    macroRecorder.toggle();
  }

  @Override
  public void startPlayback() {
    List<MacroAction> macroActionList = macroRecorder.getMacroActionList();
    macroPlayer.startPlayback(macroActionList);
  }

  @Override
  public void stopPlayback() {
    macroPlayer.stopPlayback();
  }

  @Override
  public void togglePlayback() {
    List<MacroAction> macroActionList = macroRecorder.getMacroActionList();
    macroPlayer.togglePlay(macroActionList);
  }
}
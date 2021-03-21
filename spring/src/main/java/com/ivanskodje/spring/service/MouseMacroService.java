package com.ivanskodje.spring.service;

import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.macroplayer.MacroPlayer;
import com.ivanskodje.spring.service.tool.macrorecorder.MacroRecorder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MouseMacroService implements MacroService {

  private final MacroRecorder macroRecorder;
  private final MacroPlayer macroPlayer;

  @Override
  public void startRecording() {
    macroRecorder.startMouse();
  }

  @Override
  public void stopRecording() {
    macroRecorder.stop();
  }

  @Override
  public void toggleRecording() {
    macroRecorder.toggleMouse();
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

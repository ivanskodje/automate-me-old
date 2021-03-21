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
public class KeyboardMacroService {

  private final MacroRecorder macroRecorder;
  private final MacroPlayer macroPlayer;

  public void startRecording(List<String> input) {

    if (input == null || input.contains("keyboard") && input.contains("mouse")) {
      macroRecorder.startKeyboardAndMouse();
    } else if (input.contains("keyboard")) {
      macroRecorder.startKeyboard();
    } else if (input.contains("mouse")) {
      macroRecorder.startMouse();
    }
  }

  public void stopRecording() {
    macroRecorder.stop();
  }

  public void toggleRecording() {
    macroRecorder.toggle();
  }

  public void startPlayback(Integer loop) {
    List<MacroAction> macroActionList = macroRecorder.getMacroActionList();
    macroPlayer.startPlayback(macroActionList, loop);
  }

  public void stopPlayback() {
    macroPlayer.stopPlayback();
  }

  public void togglePlayback() {
    List<MacroAction> macroActionList = macroRecorder.getMacroActionList();
    macroPlayer.togglePlay(macroActionList);
  }
}
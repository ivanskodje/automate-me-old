package com.ivanskodje.spring.service;

import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.listener.publisher.subscriber.MacroRecorderSubscriber;
import com.ivanskodje.spring.service.tool.macroplayer.MacroPlayer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeyboardMacroService implements MacroService {

  private final MacroRecorderSubscriber macroRecorderSubscriber;
  private final MacroPlayer macroPlayer;

  public KeyboardMacroService(MacroRecorderSubscriber macroRecorderSubscriber, MacroPlayer macroPlayer) {
    this.macroRecorderSubscriber = macroRecorderSubscriber;
    this.macroPlayer = macroPlayer;
  }

  @Override
  public void startRecording() {
    macroRecorderSubscriber.start();
  }

  @Override
  public void stopRecording() {
    macroRecorderSubscriber.stop();
  }

  public void toggleRecording() {
    macroRecorderSubscriber.toggle();
  }

  @Override
  public void startPlayback() {
    List<MacroAction> macroActionList = macroRecorderSubscriber.getMacroActionList();
    macroPlayer.startPlayback(macroActionList);
  }

  @Override
  public void stopPlayback() {
    macroPlayer.stopPlayback();
  }

  @Override
  public void togglePlayback() {
    List<MacroAction> macroActionList = macroRecorderSubscriber.getMacroActionList();
    macroPlayer.togglePlay(macroActionList);
  }

  public List<MacroAction> getMacroActionList() {
    return macroRecorderSubscriber.getMacroActionList();
  }
}
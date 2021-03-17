package com.ivanskodje.spring.service.tool;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class GlobalMacroState {

  @Getter
  private MacroState macroState = MacroState.STOPPED;

  public void changeToPlaying() {
    this.macroState = MacroState.PLAYING;
  }

  public void changeToStopped() {
    this.macroState = MacroState.STOPPED;
  }

  public void changeToRecording() {
    this.macroState = MacroState.RECORDING;
  }

  public boolean isPlaying() {
    return MacroState.PLAYING.equals(macroState);
  }

  public boolean isStopped() {
    return MacroState.STOPPED.equals(macroState);
  }

  public boolean isRecording() {
    return MacroState.RECORDING.equals(macroState);
  }
}

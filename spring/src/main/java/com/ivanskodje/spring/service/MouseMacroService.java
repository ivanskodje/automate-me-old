package com.ivanskodje.spring.service;

import com.ivanskodje.spring.service.tool.macrorecorder.MacroRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Service
@RequiredArgsConstructor
public class MouseMacroService implements MacroService {

  private final MacroRecorder macroRecorder;

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

  }

  @Override
  public void stopPlayback() {

  }

  @Override
  public void togglePlayback() {

  }
}

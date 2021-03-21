package com.ivanskodje.spring.controller;

import com.ivanskodje.spring.service.KeyboardMacroService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MacroController {

  private final KeyboardMacroService keyboardMacroService;

  @GetMapping("/record/start")
  public ResponseEntity<String> startRecording(@RequestParam(required = false) List<String> input) {
    log.debug("Running startRecording()");
    keyboardMacroService.startRecording(input);
    return ResponseEntity.ok("Start");
  }

  @GetMapping("/stopRecording")
  public ResponseEntity<String> stopRecording() {
    log.debug("Running stopRecording()");
    keyboardMacroService.stopRecording();
    return ResponseEntity.ok("Stopped Recording");
  }

  @GetMapping("/togglePlayback")
  public ResponseEntity<String> togglePlayback() {
    log.debug("Running togglePlayback()");
    keyboardMacroService.togglePlayback();
    return ResponseEntity.ok("Toggled Playback");
  }

  @GetMapping("/startPlayback")
  public ResponseEntity<String> startPlayback() {
    log.debug("Running startPlayback()");
    keyboardMacroService.startPlayback();
    return ResponseEntity.ok("Playing");
  }

  @GetMapping("/stopPlayback")
  public ResponseEntity<String> stopPlayback() {
    log.debug("Running stopPlayback()");
    keyboardMacroService.stopPlayback();
    return ResponseEntity.ok("Stopping");
  }
}

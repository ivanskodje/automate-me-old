package com.ivanskodje.spring.controller;

import com.ivanskodje.spring.service.MacroRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MacroController {

  private final MacroRunnerService macroRunnerService;

  @GetMapping("/start")
  public ResponseEntity<String> start() {
    macroRunnerService.startRecording();
    return ResponseEntity.ok("Start");
  }

  @GetMapping("/stop")
  public ResponseEntity<String> stop() {
    macroRunnerService.stopRecording();
    return ResponseEntity.ok("Stop");
  }

  @GetMapping("/play")
  public ResponseEntity<String> play() {
    macroRunnerService.playRecording();
    return ResponseEntity.ok("Playing");
  }

}

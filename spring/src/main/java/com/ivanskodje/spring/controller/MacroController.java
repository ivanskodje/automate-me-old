package com.ivanskodje.spring.controller;

import com.ivanskodje.spring.service.MacroRunnerService;
import com.ivanskodje.spring.service.action.MacroAction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MacroController {

  private final MacroRunnerService macroRunnerService;

  @GetMapping("/toggle")
  public ResponseEntity<String> toggle() {
    log.debug("Running toggle()");
    macroRunnerService.toggleRecording();
    return ResponseEntity.ok("Toggle");
  }

  @GetMapping("/start")
  public ResponseEntity<String> start() {
    log.debug("Running start()");
    macroRunnerService.startRecording();
    return ResponseEntity.ok("Start");
  }

  @GetMapping("/stop")
  public ResponseEntity<String> stop() {
    log.debug("Running stop()");
    macroRunnerService.stopRecording();
    return ResponseEntity.ok("Stop");
  }

  @GetMapping("/play")
  public ResponseEntity<String> play() {
    log.debug("Running play()");
    macroRunnerService.togglePlayRecording();
    return ResponseEntity.ok("Playing");
  }

  @GetMapping("/list")
  public ResponseEntity<List<MacroAction>> list() {
    log.debug("Running list()");
    List<MacroAction> macroActionList = macroRunnerService.getMacroActionList();
    return ResponseEntity.ok(macroActionList);
  }

}

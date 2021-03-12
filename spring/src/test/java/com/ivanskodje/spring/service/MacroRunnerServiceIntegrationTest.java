package com.ivanskodje.spring.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class MacroRunnerServiceIntegrationTest {

  @Autowired
  private MacroRunnerService macroRunnerService;

  @Test
  public void recordKeyboard() {

    macroRunnerService.startRecording();

    // USER PRESSED START RECORDING

    // STARTING THE RECORDING: after 2 seconds pass, start "start time" (store system time)
    // Press W [KeyEvent (int), currentTime (system time), action: press]
    // Press Left Shift [KeyEvent (int), currentTime, action: press]
    // Release W [KeyEvent (int), currentTime (system time), action: release]
    // Release Left Shift [KeyEvent (int), currentTime, action: release]
    // STOPPED THE RECORDING: 1 second after releasing "end time" (system time)



    // verify that keys have in fact been pressed

  }

}
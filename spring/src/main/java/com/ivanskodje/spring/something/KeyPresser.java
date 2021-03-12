package com.ivanskodje.spring.something;

import java.awt.AWTException;
import java.awt.Robot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyPresser {

  private final Robot robot;

  public KeyPresser() throws AWTException {
    this.robot = new Robot();
    this.robot.setAutoDelay(25);

  }

  public void clickButton(int keyEvent) {
    keyPress(keyEvent);
    keyRelease(keyEvent);
  }

  public void keyPress(int keyEvent) {
    robot.keyPress(keyEvent);
  }

  public void keyRelease(int keyEvent) {
    robot.keyRelease(keyEvent);
  }
}

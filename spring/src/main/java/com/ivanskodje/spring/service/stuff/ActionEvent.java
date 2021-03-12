package com.ivanskodje.spring.service.stuff;

import lombok.Getter;

public enum ActionEvent {

  KEY_PRESSED(2401),
  KEY_RELEASE(2402),
  UNKNOWN(0);

  @Getter
  private final Integer keyCode;

  ActionEvent(Integer keyCode) {
    this.keyCode = keyCode;
  }

  public static ActionEvent getActionEvent(Integer integer) {
    for (ActionEvent actionEvent : ActionEvent.values()) {
      if (integer.equals(actionEvent.getKeyCode())) {
        return actionEvent;
      }
    }
    return UNKNOWN;
  }
}
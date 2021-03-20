package com.ivanskodje.spring.service.tool.macroplayer;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.GlobalMacroState;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PlayingThread extends Thread {

  private final List<MacroAction> macroActionList;
  private final GlobalMacroState globalMacroState;
  private boolean isRunning = true;

  public PlayingThread(List<MacroAction> macroActionList, GlobalMacroState globalMacroState) {
    this.macroActionList = macroActionList;
    this.globalMacroState = globalMacroState;
  }

  @Override
  public void run() {
    for (MacroAction macroAction : macroActionList) {
      pause(macroAction.getDelayInMs());
      if (!isRunning) {
        log.debug("Stopped Manually");
        globalMacroState.changeToStopped();
        return;
      }
      GlobalScreen.postNativeEvent(macroAction.getNativeEvent());
    }
    log.debug("Playing finished");
    globalMacroState.changeToStopped();
  }

  void pause(Long ms) {
    long end = System.nanoTime() + ms * 1000000;
    long current = System.nanoTime();
    while (current < end) {
      if (!isRunning) {
        return;
      }
      current = System.nanoTime();
    }
  }

  public void shutdown() {
    this.isRunning = false;
  }
}

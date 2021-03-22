package com.ivanskodje.spring.service.tool.macroplayer;

import org.jnativehook.GlobalScreen;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.GlobalMacroState;
import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PlayingThread extends Thread {

  private final List<MacroAction> macroActionList;
  private final GlobalMacroState globalMacroState;
  private volatile boolean isRunning = true;
  @Setter
  private Integer loop;

  public PlayingThread(List<MacroAction> macroActionList, GlobalMacroState globalMacroState) {
    this.macroActionList = macroActionList;
    this.globalMacroState = globalMacroState;
  }

  @Override
  public void run() {
    setupLoop();
    int loopCount = 0;
    while (loop == -1 || loopCount < loop) {
      for (MacroAction macroAction : macroActionList) {
        pause(macroAction.getDelayInMs());
        if (!isRunning) {
          log.debug("Stopped Manually");
          globalMacroState.changeToStopped();
          return;
        }
        GlobalScreen.postNativeEvent(macroAction.getNativeEvent());
      }
      loopCount++;
    }

    log.debug("Playing finished");
    globalMacroState.changeToStopped();
  }

  private void setupLoop() {
    if (loop == null) {
      loop = 1;
    }
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

package com.ivanskodje.spring.service.tool.macroplayer;


import com.ivanskodje.spring.aop.aspect.maintainer.NativeKeyPressMaintainer;
import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.tool.GlobalMacroState;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MacroPlayer {

  private final GlobalMacroState globalMacroState;
  private final NativeKeyPressMaintainer nativeKeyPressMaintainer;
  private PlayingThread playingThread;

  public void togglePlay(List<MacroAction> macroActionList) {
    if (globalMacroState.isStopped()) {
      startPlayback(macroActionList, -1);
    } else if (globalMacroState.isPlaying()) {
      stopPlayback();
    } else {
      log.debug("We cannot play/stop because we are in state {}", globalMacroState.getMacroState());
    }
  }

  public void startPlayback(List<MacroAction> macroActionList, Integer loop) {
    if (globalMacroState.isStopped()) {
      log.debug("Playing recording");
      globalMacroState.changeToPlaying();
      this.playingThread = new PlayingThread(macroActionList, globalMacroState);
      this.playingThread.setLoop(loop);
      this.playingThread.start();
    } else {
      log.debug("Cannot start because we are " + globalMacroState.getMacroState());
    }
  }

  @SneakyThrows
  public void stopPlayback() {
    if (globalMacroState.isPlaying()) {
      log.debug("Stop playing");
      playingThread.shutdown();
      playingThread.join();
      nativeKeyPressMaintainer.releasePressedKeys();
    }
  }
}

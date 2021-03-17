package com.ivanskodje.spring.service.tool;

import static org.junit.Assert.assertTrue;

import com.ivanskodje.spring.service.action.MacroAction;
import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MacroPlayerTest extends TestKeyPressing {

  @Test
  public void testPlaying_expectPlayingStatus() {
    GlobalMacroState globalMacroState = new GlobalMacroState();
    MacroPlayer macroPlayer = new MacroPlayer(globalMacroState);

    List<MacroAction> macroActionList = write("Hello");
    List<MacroAction> playedMacroActionList = new ArrayList<>();



    macroPlayer.play(macroActionList);


//    assertTrue(globalMacroState.isPlaying());

  }
}
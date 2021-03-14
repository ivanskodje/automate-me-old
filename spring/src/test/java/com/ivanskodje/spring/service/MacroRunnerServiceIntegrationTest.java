//package com.ivanskodje.spring.service;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.ivanskodje.spring.service.tool.listener.MacroKeyListener;
//import com.ivanskodje.spring.service.macro.MacroAction;
//import com.ivanskodje.spring.service.testhelp.TestKeyPressing;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.NONE)
//public class MacroRunnerServiceIntegrationTest extends TestKeyPressing {
//
//  @SpyBean
//  private MacroRunnerService macroRunnerService;
//
//  @Mock
//  private MacroKeyListener macroKeyListener;
//
//  @Before
//  public void before() {
//    MockitoAnnotations.initMocks(this);
//  }
//
//  @Test
//  public void playKeyboardMacroRecording() {
//
//    MacroKeyListener macroKeyListener = Mockito.mock(MacroKeyListener.class);
//    List<MacroAction> macroActionList = write("IVAN");
//    when(macroKeyListener.getMacroActionList()).thenReturn(macroActionList);
//    macroRunnerService.setMacroKeyListener(macroKeyListener);
//
//    List<MacroAction> scheduledMacroActionList = new ArrayList<>();
//    doAnswer(invocation -> scheduledMacroActionList.add((MacroAction) invocation.getArguments()[1]))
//        .when(macroRunnerService).scheduleMacroAction(any(), any());
//
//    macroRunnerService.playRecording();
//
//    verify(macroRunnerService, times(8)).scheduleMacroAction(any(), any());
//
//    Assertions.assertThat(scheduledMacroActionList).containsAll(macroActionList);
//  }
//}
package com.ivanskodje.spring;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

  public static void main(String[] args) throws NativeHookException {
    System.setProperty("java.awt.headless", "false");
    GlobalScreen.registerNativeHook();
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        try {
          log.info("Unregistering hook");
          GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
          log.info(ex.getMessage(), ex);
          ex.printStackTrace();
        }
      }
    });

    SpringApplication.run(Application.class, args);
  }
}

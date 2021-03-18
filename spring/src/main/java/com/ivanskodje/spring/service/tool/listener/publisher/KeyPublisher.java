package com.ivanskodje.spring.service.tool.listener.publisher;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.ivanskodje.spring.aop.aspect.OnlyPressOnce;
import com.ivanskodje.spring.service.tool.listener.publisher.subscriber.KeySubscriber;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyPublisher {

  private final List<KeySubscriber> keySubscriberList = new CopyOnWriteArrayList<>();

  public void subscribe(KeySubscriber keySubscriber) {
    keySubscriberList.add(keySubscriber);
  }

  @OnlyPressOnce
  public void pressed(NativeKeyEvent nativeEvent) {

    Iterator<KeySubscriber> keySubscriberIterator = keySubscriberList.iterator();
    while (keySubscriberIterator.hasNext()) {
      KeySubscriber subscriber = keySubscriberIterator.next();
      subscriber.pressed(nativeEvent);
    }
  }

  @OnlyPressOnce
  public void released(NativeKeyEvent nativeEvent) {
    Iterator<KeySubscriber> keySubscriberIterator = keySubscriberList.iterator();
    while (keySubscriberIterator.hasNext()) {
      KeySubscriber subscriber = keySubscriberIterator.next();
      subscriber.released(nativeEvent);
    }
  }

  public void unsubscribe(KeySubscriber keySubscriber) {
    keySubscriberList.remove(keySubscriber);
  }
}

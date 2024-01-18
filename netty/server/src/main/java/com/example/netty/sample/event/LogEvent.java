package com.example.netty.sample.event;

import java.util.function.Supplier;

public class LogEvent {

  public static void addLog(Supplier<String> content) {
    System.out.println(content.get());
  }
}

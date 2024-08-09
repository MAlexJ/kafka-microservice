package com.malex.filteringservice.utils;

import java.util.Optional;

public class MessageFormatUtils {
  private static final String SHORT_MASSAGE_FORMAT = "%s ...";
  private static final String EMPTY_STRING = "";

  private MessageFormatUtils() {
    // not use
  }

  public static String shortMessageInfo(Object obj) {
    return Optional.ofNullable(obj)
        .map(Object::toString)
        .filter(message -> message.length() > 200)
        .map(message -> String.format(SHORT_MASSAGE_FORMAT, message.substring(0, 200)))
        .or(() -> Optional.ofNullable(obj).map(o -> o.getClass().getSimpleName()))
        .orElse(EMPTY_STRING);
  }
}

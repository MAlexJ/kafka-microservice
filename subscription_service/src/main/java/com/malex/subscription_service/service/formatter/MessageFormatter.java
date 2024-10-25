package com.malex.subscription_service.service.formatter;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MessageFormatter {

  private static final String SHORT_MASSAGE_FORMAT = "%s ...";
  private static final String ERROR_MESSAGE_TEMPLATE = "'%s' is a mandatory parameter";
  private static final String EMPTY_STRING = "";


  public String shortMessageInfo(Object obj) {
    return Optional.ofNullable(obj)
        .map(Object::toString)
        .filter(message -> message.length() > 200)
        .map(message -> String.format(SHORT_MASSAGE_FORMAT, message.substring(0, 200)))
        .or(() -> Optional.ofNullable(obj).map(o -> o.getClass().getSimpleName()))
        .orElse(EMPTY_STRING);
  }
}

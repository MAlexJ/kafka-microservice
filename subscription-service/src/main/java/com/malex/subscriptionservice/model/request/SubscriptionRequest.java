package com.malex.subscriptionservice.model.request;

import java.util.List;
import java.util.Objects;

public record SubscriptionRequest(
    Long chatId,
    String templateId,
    String customizationId,
    String rss,
    List<String> filterIds,
    boolean isActive) {

  private static final String ERROR_MESSAGE_TEMPLATE = "'%s' is a mandatory parameter";

  public SubscriptionRequest {
    Objects.requireNonNull(chatId, errorMessage("chatId"));
    Objects.requireNonNull(rss, errorMessage("rss"));
  }

  public String errorMessage(String parameter) {
    return String.format(ERROR_MESSAGE_TEMPLATE, parameter);
  }
}

package com.malex.subscriptionservice.model.request;

import static com.malex.subscriptionservice.utils.MessageFormatUtils.errorMessage;

import java.util.List;
import java.util.Objects;

public record SubscriptionRequest(
    Long chatId,
    String templateId,
    String customizationId,
    String rss,
    List<String> filterIds,
    boolean isActive) {

  public SubscriptionRequest {
    Objects.requireNonNull(chatId, errorMessage("chatId"));
    Objects.requireNonNull(rss, errorMessage("rss"));
  }
}

package com.malex.subscriptionservice.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SubscriptionDto(
    String id,
    Long chatId,
    String templateId,
    String customizationId,
    String rss,
    List<String> filterIds,
    boolean isActive,
    LocalDateTime created) {}

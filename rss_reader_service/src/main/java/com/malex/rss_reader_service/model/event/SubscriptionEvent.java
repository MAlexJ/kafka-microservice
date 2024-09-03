package com.malex.rss_reader_service.model.event;

import java.time.LocalDateTime;
import java.util.List;

public record SubscriptionEvent(
    String id,
    Long chatId,
    String templateId,
    String customizationId,
    String rss,
    List<String> filterIds,
    boolean isActive,
    LocalDateTime created) {}

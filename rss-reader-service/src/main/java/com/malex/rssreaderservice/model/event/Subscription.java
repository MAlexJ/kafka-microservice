package com.malex.rssreaderservice.model.event;

import java.time.LocalDateTime;
import java.util.List;

public record Subscription(
    String id,
    Long chatId,
    String templateId,
    String customizationId,
    String rss,
    List<String> filterIds,
    boolean isActive,
    LocalDateTime created) {}

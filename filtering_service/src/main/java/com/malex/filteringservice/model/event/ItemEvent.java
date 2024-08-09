package com.malex.filteringservice.model.event;

import java.util.List;

public record ItemEvent(
    // item info
    String link,
    String title,
    String description,
    String md5Hash,
    // subscription info
    String subscriptionId,
    Long chatId,
    String templateId,
    String customizationId,
    List<String> filterIds) {}

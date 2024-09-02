package com.malex.subscription_service.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record SubscriptionResponse(String id,
                                   Long chatId,
                                   String templateId,
                                   String customizationId,
                                   String rss,
                                   List<String> filterIds,
                                   boolean isActive,
                                   LocalDateTime created) {}

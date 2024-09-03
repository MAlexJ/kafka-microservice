package com.malex.storage_service.model.event;

public record ItemEvent(
    String link,
    String title,
    String description,
    Long chatId,
    String templateId,
    String customizationId) {}

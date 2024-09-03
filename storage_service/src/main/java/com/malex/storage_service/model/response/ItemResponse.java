package com.malex.storage_service.model.response;

public record ItemResponse(
    Long id,
    String link,
    String title,
    String description,
    Long chatId,
    String templateId,
    String customizationId,
    boolean isActive) {}

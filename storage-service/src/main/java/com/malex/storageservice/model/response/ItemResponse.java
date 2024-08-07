package com.malex.storageservice.model.response;

public record ItemResponse(
    Long id,
    String link,
    String title,
    String description,
    Long chatId,
    String templateId,
    String customizationId,
    boolean isActive) {}

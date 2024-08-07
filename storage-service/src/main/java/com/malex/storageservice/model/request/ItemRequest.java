package com.malex.storageservice.model.request;

import java.util.Objects;

public record ItemRequest(
    String link,
    String title,
    String description,
    Long chatId,
    String templateId,
    String customizationId) {

  public ItemRequest {
    Objects.requireNonNull(link, "link cannot be null");
    Objects.requireNonNull(title, "title cannot be null");
    Objects.requireNonNull(description, "description cannot be null");
  }
}

package com.malex.storageservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("items")
public class ItemEntity {

  @Id
  @Column("id")
  private Long id;

  @Column("link")
  private String link;

  @Column("title")
  private String title;

  @Column("description")
  private String description;

  @Column("chatId")
  private Long chatId;

  @Column("templateId")
  private String templateId;

  @Column("customizationId")
  private String customizationId;

  @Column("isActive")
  private boolean isActive;
}

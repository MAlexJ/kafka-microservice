package com.malex.filteringservice.model.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "md5-hashes")
@TypeAlias("Md5HashEntity")
public class Md5HashEntity {

  @MongoId private String id;

  private String md5Hash;

  @CreatedDate private LocalDateTime created;
}

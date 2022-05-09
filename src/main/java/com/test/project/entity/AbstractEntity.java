package com.test.project.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AbstractEntity {

  @CreationTimestamp
  @Column(name = "created_date", updatable = false)
  LocalDateTime createdDate;
  @UpdateTimestamp
  @Column(name = "updated_date", insertable = false)
  LocalDateTime updatedDate;
}

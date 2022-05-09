package com.test.project.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jkh")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JKH extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jkh_id_sequence")
  @SequenceGenerator(name = "jkh_id_sequence", sequenceName = "jkh_id_sequence", allocationSize = 1)
  Long id;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
  Plumber plumber;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
  House house;

  public void removeRecordFromRegister() {
    this.setHouse(null);
    this.setPlumber(null);
  }
}

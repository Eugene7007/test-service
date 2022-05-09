package com.test.project.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "house")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class House extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_id_sequence")
  @SequenceGenerator(name = "house_id_sequence", sequenceName = "house_id_sequence", allocationSize = 1)
  Long id;

  String address;
  @Column(unique = true)
  String cadastralNo;

  @Builder.Default
  @ToString.Exclude
  @OneToMany(mappedBy = "house", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  Set<JKH> jkh = new HashSet<>();

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final House that = (House) o;
    return id.equals(that.id) && cadastralNo.equals(that.cadastralNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

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
@Table(name = "plumber")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Plumber extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plumber_id_sequence")
    @SequenceGenerator(name = "plumber_id_sequence", sequenceName = "plumber_id_sequence", allocationSize = 1)
    Long id;

    @Column(unique = true)
    String pinfl;
    String phone;
    String lastname;
    String firstname;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "plumber", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<JKH> jkh = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plumber plumber = (Plumber) o;
        return Objects.equals(id, plumber.id) && Objects.equals(pinfl, plumber.pinfl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

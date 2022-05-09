package com.test.project.repository;

import com.test.project.entity.Plumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlumberRepository extends JpaRepository<Plumber, Long> {
  @Query("SELECT p " +
      "     FROM Plumber p " +
      "    WHERE p.pinfl = :pinfl")
  Optional<Plumber> findPlumberByPinfl(@Param("pinfl") String pinfl);
}

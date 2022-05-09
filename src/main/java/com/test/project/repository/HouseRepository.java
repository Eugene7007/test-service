package com.test.project.repository;

import com.test.project.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {
  @Query("SELECT house " +
      "     FROM House house " +
      "    WHERE house.cadastralNo = :cadastralNo")
  Optional<House> findByCadastralNo(@Param("cadastralNo") String cadastralNo);
}

package com.test.project.repository;

import com.test.project.entity.JKH;
import com.test.project.entity.Plumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JKHRepository extends JpaRepository<JKH, Long> {
  @Query("SELECT jkh " +
      "     FROM JKH jkh " +
      "     JOIN FETCH jkh.house h " +
      "     JOIN FETCH jkh.plumber p " +
      "    WHERE p.pinfl = :pinfl " +
      "      AND h.cadastralNo = :cadastralNo")
  Optional<JKH> findJkhRecordByPinflAndCadastralNo(@Param("pinfl") String pinfl,
                                                   @Param("cadastralNo") String cadastralNo);


  @Query("SELECT jkh " +
      "     FROM JKH jkh " +
      "     JOIN FETCH jkh.house h " +
      "     JOIN FETCH jkh.plumber p " +
      "    WHERE p.pinfl = :pinfl")
  List<JKH> findHousesInfoByPinfl(@Param("pinfl") String pinfl);

  @Query("SELECT jkh " +
      "     FROM JKH jkh " +
      "     JOIN FETCH jkh.house h " +
      "     JOIN FETCH jkh.plumber p " +
      "    WHERE h.cadastralNo = :cadastralNo")
  Optional<JKH> findPlumberInfoByCadastralNo(@Param("cadastralNo") String cadastralNo);
}

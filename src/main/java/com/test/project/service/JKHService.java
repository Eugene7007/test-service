package com.test.project.service;

import com.test.project.dto.JKHReqDto;
import com.test.project.entity.House;
import com.test.project.entity.JKH;
import com.test.project.entity.Plumber;

import java.util.List;
import java.util.Optional;

public interface JKHService {

  JKH save(JKH jkh);
  void bindPlumberToHouse(JKHReqDto dto);

  void unbindPlumberFromHouse(JKHReqDto dto);

  List<House> findHousesInfoByPinfl(String pinfl);

  Plumber findPlumberInfoByCadastralNo(String cadastralNo);
}

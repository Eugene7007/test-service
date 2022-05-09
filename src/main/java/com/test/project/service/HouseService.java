package com.test.project.service;

import com.test.project.dto.HouseReqDto;
import com.test.project.entity.House;

import java.util.Optional;

public interface HouseService {
  Optional<House> findHouseByCadastralNo(String cadastralNo);

  House addHouseInfo(HouseReqDto dto);

  House updateHouseInfo(House house);

  void deleteHouseInfo(String cadastralNo);
}

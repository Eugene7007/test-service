package com.test.project.service.impl;

import com.test.project.dto.HouseReqDto;
import com.test.project.entity.House;
import com.test.project.exception.BusinessObjectNotFoundException;
import com.test.project.repository.HouseRepository;
import com.test.project.service.HouseService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class HouseServiceImplTest {

  @MockBean
  HouseRepository repository;
  @SpyBean
  HouseService houseService;
  @Autowired
  ModelMapper modelMapper;
  private static final EasyRandom generator = new EasyRandom();

  @Test
  void addHouseInfo() {
    HouseReqDto dto = generator.nextObject(HouseReqDto.class);
    House house = modelMapper.map(dto, House.class);
    Mockito
        .when(repository.save(Mockito.any()))
        .thenReturn(house);

    House result = houseService.addHouseInfo(dto);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getCadastralNo(), dto.getCadastralNo());
  }

  @Test
  void updateHouseInfo() {
    HouseReqDto dto = generator.nextObject(HouseReqDto.class);
    House house = modelMapper.map(dto, House.class);
    Mockito
        .when(repository.save(Mockito.any()))
        .thenReturn(house);

    House result = houseService.updateHouseInfo(house);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getCadastralNo(), dto.getCadastralNo());
  }

  @Test
  void deleteHouseInfo() {
    String cadastralNo = generator.nextObject(String.class);
    House house = generator.nextObject(House.class);
    Mockito
        .when(repository.findByCadastralNo(cadastralNo))
        .thenReturn(Optional.of(house));

    houseService.deleteHouseInfo(cadastralNo);
    Mockito.verify(houseService, Mockito.times(1)).deleteHouseInfo(cadastralNo);
  }

  @Test
  void deleteHouseInfoException() {
    String cadastralNo = generator.nextObject(String.class);
    Mockito
        .when(repository.findByCadastralNo(cadastralNo))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(BusinessObjectNotFoundException.class, ()-> houseService.deleteHouseInfo(cadastralNo));
  }

  @Test
  void findHouseByCadastralNo() {
    String cadastralNo = generator.nextObject(String.class);
    House house = generator.nextObject(House.class);
    Mockito
        .when(repository.findByCadastralNo(cadastralNo))
        .thenReturn(Optional.of(house));

    Optional<House> result = houseService.findHouseByCadastralNo(cadastralNo);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(result.get(), house);
  }
}
package com.test.project.service.impl;

import com.test.project.dto.JKHReqDto;
import com.test.project.entity.House;
import com.test.project.entity.JKH;
import com.test.project.entity.Plumber;
import com.test.project.exception.AllowableHousesException;
import com.test.project.exception.BusinessObjectDuplicateException;
import com.test.project.exception.BusinessObjectNotFoundException;
import com.test.project.exception.HouseAlreadyBindException;
import com.test.project.repository.JKHRepository;
import com.test.project.service.HouseService;
import com.test.project.service.JKHService;
import com.test.project.service.PlumberService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class JKHServiceImplTest {

  @MockBean
  HouseService houseService;
  @MockBean
  PlumberService plumberService;
  @MockBean
  JKHRepository repository;
  @SpyBean
  JKHService jkhService;
  @Autowired
  ModelMapper modelMapper;
  private static final EasyRandom generator = new EasyRandom();

  @Test
  void save() {
    JKH jkh = generator.nextObject(JKH.class);
    Mockito
        .when(repository.save(Mockito.any()))
        .thenReturn(jkh);

    JKH result = jkhService.save(jkh);
    Assertions.assertNotNull(result);
  }

  @Test
  void bindPlumberToHouse() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    House house = House.builder().cadastralNo(dto.getCadastralNo()).build();
    Plumber plumber = Plumber.builder().pinfl(dto.getPinfl()).build();
    List<JKH> jkh = generator.objects(JKH.class, 3).collect(Collectors.toList());
    Mockito
        .when(repository.findHousesInfoByPinfl(dto.getPinfl()))
        .thenReturn(jkh);
    Mockito
        .when(repository.findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo()))
        .thenReturn(Optional.empty());
    Mockito
        .when(houseService.findHouseByCadastralNo(dto.getCadastralNo()))
        .thenReturn(Optional.of(house));
    Mockito
        .when(plumberService.findByPinfl(dto.getPinfl()))
        .thenReturn(Optional.of(plumber));

    jkhService.bindPlumberToHouse(dto);
    Mockito.verify(jkhService, Mockito.times(1)).bindPlumberToHouse(dto);
  }

  @Test
  void bindPlumberToHouseMaxAllowableHousesException() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    List<JKH> jkh = generator.objects(JKH.class, 6).collect(Collectors.toList());
    Mockito
        .when(repository.findHousesInfoByPinfl(dto.getPinfl()))
        .thenReturn(jkh);
    Assertions.assertThrows(AllowableHousesException.class, ()-> jkhService.bindPlumberToHouse(dto));
  }

  @Test
  void bindPlumberToHouseAlreadyBindException() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    JKH jkh = modelMapper.map(dto, JKH.class);
    House house = House.builder().cadastralNo(dto.getCadastralNo()).build();
    jkh.setHouse(house);
    List<JKH> records = generator.objects(JKH.class, 3).collect(Collectors.toList());
    records.add(jkh);

    Mockito
        .when(repository.findHousesInfoByPinfl(dto.getPinfl()))
        .thenReturn(records);

    Assertions.assertThrows(HouseAlreadyBindException.class, ()-> jkhService.bindPlumberToHouse(dto));
  }

  @Test
  void bindPlumberToHouseDuplicateException() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    JKH jkh = modelMapper.map(dto, JKH.class);
    List<JKH> records = generator.objects(JKH.class, 3).collect(Collectors.toList());

    Mockito
        .when(repository.findHousesInfoByPinfl(dto.getPinfl()))
        .thenReturn(records);
    Mockito
        .when(repository.findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo()))
        .thenReturn(Optional.of(jkh));

    Assertions.assertThrows(BusinessObjectDuplicateException.class, ()-> jkhService.bindPlumberToHouse(dto));
  }

  @Test
  void bindPlumberToHouseHouseNotFoundException() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    House house = House.builder().cadastralNo(dto.getCadastralNo()).build();
    List<JKH> records = generator.objects(JKH.class, 3).collect(Collectors.toList());

    Mockito
        .when(repository.findHousesInfoByPinfl(dto.getPinfl()))
        .thenReturn(records);
    Mockito
        .when(repository.findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo()))
        .thenReturn(Optional.empty());
    Mockito
        .when(houseService.findHouseByCadastralNo(dto.getCadastralNo()))
        .thenReturn(Optional.of(house));

    Assertions.assertThrows(BusinessObjectNotFoundException.class, ()-> jkhService.bindPlumberToHouse(dto));
  }

  @Test
  void bindPlumberToHousePlumberNotFoundException() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    Plumber plumber = Plumber.builder().pinfl(dto.getPinfl()).build();
    List<JKH> records = generator.objects(JKH.class, 3).collect(Collectors.toList());

    Mockito
        .when(repository.findHousesInfoByPinfl(dto.getPinfl()))
        .thenReturn(records);
    Mockito
        .when(repository.findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo()))
        .thenReturn(Optional.empty());
    Mockito
        .when(houseService.findHouseByCadastralNo(dto.getCadastralNo()))
        .thenReturn(Optional.empty());
    Mockito
        .when(plumberService.findByPinfl(dto.getPinfl()))
        .thenReturn(Optional.of(plumber));

    Assertions.assertThrows(BusinessObjectNotFoundException.class, ()-> jkhService.bindPlumberToHouse(dto));
  }

  @Test
  void unbindPlumberFromHouse() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    JKH jkh = modelMapper.map(dto, JKH.class);
    Mockito
        .when(repository.findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo()))
        .thenReturn(Optional.of(jkh));

    jkhService.unbindPlumberFromHouse(dto);
    Mockito.verify(jkhService, Mockito.times(1)).unbindPlumberFromHouse(dto);
  }

  @Test
  void unbindPlumberFromHouseNotFoundException() {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    Mockito
        .when(repository.findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo()))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(BusinessObjectNotFoundException.class, ()-> jkhService.unbindPlumberFromHouse(dto));
  }

  @Test
  void findHousesInfoByPinfl() {
    String pinfl = generator.nextObject(String.class);
    List<JKH> jkh = generator.objects(JKH.class, 5).collect(Collectors.toList());
    Mockito
        .when(repository.findHousesInfoByPinfl(pinfl))
        .thenReturn(jkh);

    List<House> result = jkhService.findHousesInfoByPinfl(pinfl);
    Assertions.assertFalse(result.isEmpty());
  }

  @Test
  void findPlumberInfoByCadastralNo() {
    String cadastralNo = generator.nextObject(String.class);
    JKH jkh = generator.nextObject(JKH.class);
    Mockito
        .when(repository.findPlumberInfoByCadastralNo(cadastralNo))
        .thenReturn(Optional.of(jkh));

    Plumber result = jkhService.findPlumberInfoByCadastralNo(cadastralNo);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getPinfl(), jkh.getPlumber().getPinfl());
  }

  @Test
  void findPlumberInfoByCadastralNoNotFoundException() {
    String cadastralNo = generator.nextObject(String.class);
    Mockito
        .when(repository.findPlumberInfoByCadastralNo(cadastralNo))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(BusinessObjectNotFoundException.class, ()-> jkhService.findPlumberInfoByCadastralNo(cadastralNo));
  }
}
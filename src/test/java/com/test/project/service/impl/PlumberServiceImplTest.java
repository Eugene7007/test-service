package com.test.project.service.impl;

import com.test.project.dto.PlumberReqDto;
import com.test.project.entity.House;
import com.test.project.entity.Plumber;
import com.test.project.exception.BusinessObjectDuplicateException;
import com.test.project.exception.BusinessObjectNotFoundException;
import com.test.project.repository.PlumberRepository;
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

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class PlumberServiceImplTest {

  @MockBean
  PlumberRepository repository;
  @SpyBean
  PlumberService plumberService;
  @Autowired
  ModelMapper modelMapper;
  private static final EasyRandom generator = new EasyRandom();

  @Test
  void hirePlumber() {
    PlumberReqDto dto = generator.nextObject(PlumberReqDto.class);
    Plumber plumber = modelMapper.map(dto, Plumber.class);
    Mockito
        .when(repository.findPlumberByPinfl(Mockito.any()))
        .thenReturn(Optional.empty());
    Mockito
        .when(repository.save(Mockito.any()))
        .thenReturn(plumber);

    Plumber result = plumberService.hirePlumber(dto);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(result.getPinfl(), dto.getPinfl());
  }

  @Test
  void hirePlumberDuplicateException() {
    PlumberReqDto dto = generator.nextObject(PlumberReqDto.class);
    Plumber plumber = modelMapper.map(dto, Plumber.class);
    Mockito
        .when(repository.findPlumberByPinfl(Mockito.any()))
        .thenReturn(Optional.of(plumber));

    Assertions.assertThrows(BusinessObjectDuplicateException.class, ()-> plumberService.hirePlumber(dto));
  }

  @Test
  void findByPinfl() {
    String pinfl = generator.nextObject(String.class);
    Plumber plumber = Plumber.builder().pinfl(pinfl).build();
    Mockito
        .when(repository.findPlumberByPinfl(Mockito.any()))
        .thenReturn(Optional.of(plumber));

    Optional<Plumber> result = plumberService.findByPinfl(pinfl);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(result.get().getPinfl(), pinfl);
  }

  @Test
  void dismissPlumber() {
    String pinfl = generator.nextObject(String.class);
    Plumber plumber = Plumber.builder().pinfl(pinfl).build();
    Mockito
        .when(repository.findPlumberByPinfl(Mockito.any()))
        .thenReturn(Optional.of(plumber));

    plumberService.dismissPlumber(pinfl);
    Mockito.verify(plumberService, Mockito.times(1)).dismissPlumber(pinfl);
  }

  @Test
  void dismissPlumberNotFoundException() {
    String pinfl = generator.nextObject(String.class);
    Mockito
        .when(repository.findPlumberByPinfl(Mockito.any()))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(BusinessObjectNotFoundException.class, ()-> plumberService.dismissPlumber(pinfl));
  }
}
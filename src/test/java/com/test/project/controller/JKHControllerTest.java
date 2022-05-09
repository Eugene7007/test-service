package com.test.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.project.dto.JKHReqDto;
import com.test.project.entity.House;
import com.test.project.entity.Plumber;
import com.test.project.service.JKHService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class JKHControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  JKHService jkhService;
  @Autowired
  private ObjectMapper mapper;
  private static final EasyRandom generator = new EasyRandom();

  @Test
  void bindPlumberToHouse() throws Exception {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    String reqBody = mapper.writeValueAsString(dto);

    mvc.perform(post("/jkh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(reqBody))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  void unbindPlumberFromHouse() throws Exception {
    JKHReqDto dto = generator.nextObject(JKHReqDto.class);
    String reqBody = mapper.writeValueAsString(dto);

    mvc.perform(delete("/jkh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(reqBody))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  void findAllHousesInfoByPinfl() throws Exception {
    String pinfl = generator.nextObject(String.class);
    List<House> houses = generator.objects(House.class, 5).collect(Collectors.toList());
    Mockito
        .when(jkhService.findHousesInfoByPinfl(pinfl))
        .thenReturn(houses);

    mvc.perform(get("/jkh/house-info/" + pinfl)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void findPlumberInfoByCadastralNo() throws Exception {
    String cadastralNo = generator.nextObject(String.class);
    Plumber plumber = generator.nextObject(Plumber.class);
    Mockito
        .when(jkhService.findPlumberInfoByCadastralNo(cadastralNo))
        .thenReturn(plumber);

    mvc.perform(get("/jkh/plumber-info/" + cadastralNo)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }
}
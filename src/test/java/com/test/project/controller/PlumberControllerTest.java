package com.test.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.project.dto.PlumberReqDto;
import com.test.project.entity.Plumber;
import com.test.project.service.PlumberService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class PlumberControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  PlumberService plumberService;
  @Autowired
  private ObjectMapper mapper;
  private static final EasyRandom generator = new EasyRandom();

  @Test
  void hirePlumber() throws Exception {
    PlumberReqDto dto = generator.nextObject(PlumberReqDto.class);
    String reqBody = mapper.writeValueAsString(dto);
    Mockito
        .when(plumberService.hirePlumber(dto))
        .thenReturn(generator.nextObject(Plumber.class));

    mvc.perform(post("/plumbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(reqBody))
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  void dismissPlumber() throws Exception {
    String pinfl = generator.nextObject(String.class);
    mvc.perform(delete("/plumbers/" + pinfl)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }
}
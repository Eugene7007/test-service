package com.test.project.controller;

import com.test.project.dto.HouseReqDto;
import com.test.project.dto.HouseRespDto;
import com.test.project.entity.House;
import com.test.project.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HouseController {

  ModelMapper modelMapper;
  HouseService houseService;

  @PostMapping
  @Operation(summary = "Операция добавления информации о новом доме")
  public ResponseEntity<HouseRespDto> createHouse(@RequestBody HouseReqDto dto) {
    House house = houseService.addHouseInfo(dto);
    HouseRespDto resp = modelMapper.map(house, HouseRespDto.class);
    return new ResponseEntity<>(resp, HttpStatus.CREATED);
  }

  @DeleteMapping("/{cadastralNo}")
  @Operation(summary = "Операция удаления информации о доме по кадастровому номеру")
  public ResponseEntity<Void> deleteHouse(@PathVariable("cadastralNo") String cadastralNo) {
    houseService.deleteHouseInfo(cadastralNo);
    return ResponseEntity.noContent().build();
  }
}

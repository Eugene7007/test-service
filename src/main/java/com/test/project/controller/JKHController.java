package com.test.project.controller;

import com.test.project.dto.HouseRespDto;
import com.test.project.dto.JKHReqDto;
import com.test.project.dto.PlumberRespDto;
import com.test.project.entity.House;
import com.test.project.entity.Plumber;
import com.test.project.service.JKHService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/jkh")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JKHController {

  JKHService jkhService;
  ModelMapper modelMapper;

  @PostMapping
  @Operation(summary = "Операция привязки сантехника к определенному дому по пинфл и кадастровому номеру")
  public ResponseEntity<Void> bindPlumberToHouse(@RequestBody JKHReqDto dto) {
    jkhService.bindPlumberToHouse(dto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  @Operation(summary = "Операция открепления сантехника от определенного дома по пинфл")
  public ResponseEntity<Void> unbindPlumberFromHouse(@RequestBody JKHReqDto dto) {
    jkhService.unbindPlumberFromHouse(dto);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/house-info/{pinfl}")
  public ResponseEntity<List<HouseRespDto>> findAllHousesInfoByPinfl(@PathVariable String pinfl) {
    List<House> houses = jkhService.findHousesInfoByPinfl(pinfl);
    return ResponseEntity.ok(houses
        .parallelStream()
        .map(v -> modelMapper.map(v, HouseRespDto.class))
        .collect(Collectors.toList()));
  }

  @GetMapping("/plumber-info/{cadastralNo}")
  public ResponseEntity<PlumberRespDto> findPlumberInfoByCadastralNo(@PathVariable String cadastralNo) {
    Plumber plumber = jkhService.findPlumberInfoByCadastralNo(cadastralNo);
    return ResponseEntity.ok(modelMapper.map(plumber, PlumberRespDto.class));
  }
}

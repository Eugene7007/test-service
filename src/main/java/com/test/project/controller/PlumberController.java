package com.test.project.controller;

import com.test.project.dto.PlumberReqDto;
import com.test.project.dto.PlumberRespDto;
import com.test.project.entity.Plumber;
import com.test.project.service.PlumberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/plumbers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlumberController {

  ModelMapper modelMapper;
  PlumberService plumberService;

  @PostMapping
  @Operation(summary = "Операция найма сантехника")
  public ResponseEntity<PlumberRespDto> hirePlumber(@RequestBody PlumberReqDto dto) {
    Plumber plumber = plumberService.hirePlumber(dto);
    PlumberRespDto resp = modelMapper.map(plumber, PlumberRespDto.class);
    return new ResponseEntity<>(resp, HttpStatus.CREATED);
  }

  @DeleteMapping("/{pinfl}")
  @Operation(summary = "Операция увольнения сантехника по пинфл")
  public ResponseEntity<Void> dismissPlumber(@PathVariable("pinfl") String pinfl) {
    plumberService.dismissPlumber(pinfl);
    return ResponseEntity.noContent().build();
  }
}

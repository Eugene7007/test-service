package com.test.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlumberRespDto {

  String pinfl;
  String phone;
  String lastname;
  String firstname;
}

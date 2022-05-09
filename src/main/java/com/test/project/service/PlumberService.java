package com.test.project.service;

import com.test.project.dto.PlumberReqDto;
import com.test.project.entity.Plumber;

import java.util.Optional;

public interface PlumberService {
  Plumber hirePlumber(PlumberReqDto dto);

  Optional<Plumber> findByPinfl(String pinfl);
  void dismissPlumber(String pinfl);
}

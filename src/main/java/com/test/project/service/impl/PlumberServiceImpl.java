package com.test.project.service.impl;

import com.test.project.dto.PlumberReqDto;
import com.test.project.entity.Plumber;
import com.test.project.exception.BusinessObjectDuplicateException;
import com.test.project.exception.BusinessObjectNotFoundException;
import com.test.project.repository.PlumberRepository;
import com.test.project.service.PlumberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.test.project.common.Constants.PLUMBER_DUPLICATE_MSG;
import static com.test.project.common.Constants.PLUMBER_NOT_FOUND_MSG;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlumberServiceImpl implements PlumberService {

  ModelMapper modelMapper;
  PlumberRepository repository;

  @Override
  @Transactional
  public Plumber hirePlumber(PlumberReqDto dto) {
    Optional<Plumber> optional = findByPinfl(dto.getPinfl());
    if (optional.isPresent())
      throw new BusinessObjectDuplicateException(PLUMBER_DUPLICATE_MSG + dto.getPinfl());

    return repository.save(modelMapper.map(dto, Plumber.class));
  }

  @Override
  public Optional<Plumber> findByPinfl(final String pinfl) {
    return repository.findPlumberByPinfl(pinfl);
  }

  @Override
  @Transactional
  public void dismissPlumber(final String pinfl) {
    final Plumber plumber = findByPinfl(pinfl)
        .orElseThrow(()-> new BusinessObjectNotFoundException(PLUMBER_NOT_FOUND_MSG + pinfl));

    repository.delete(plumber);
  }
}

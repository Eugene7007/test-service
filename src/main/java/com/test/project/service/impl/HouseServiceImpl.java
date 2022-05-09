package com.test.project.service.impl;

import com.test.project.dto.HouseReqDto;
import com.test.project.entity.House;
import com.test.project.exception.BusinessObjectDuplicateException;
import com.test.project.exception.BusinessObjectNotFoundException;
import com.test.project.repository.HouseRepository;
import com.test.project.service.HouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.test.project.common.Constants.HOUSE_DUPLICATE_MSG;
import static com.test.project.common.Constants.HOUSE_NOT_FOUND_MSG;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HouseServiceImpl implements HouseService {

  ModelMapper modelMapper;
  HouseRepository repository;

  @Override
  @Transactional
  public House addHouseInfo(final HouseReqDto dto) {
    findHouseByCadastralNo(dto.getCadastralNo())
        .ifPresent(v -> {
          throw new BusinessObjectDuplicateException(HOUSE_DUPLICATE_MSG + dto.getCadastralNo());
        });

    return repository.save(modelMapper.map(dto, House.class));
  }

  @Override
  public House updateHouseInfo(House house) {
    return repository.save(house);
  }

  @Override
  @Transactional
  public void deleteHouseInfo(final String cadastralNo) {
    final House house = findHouseByCadastralNo(cadastralNo)
        .orElseThrow(() -> new BusinessObjectNotFoundException(HOUSE_NOT_FOUND_MSG + cadastralNo));

    repository.delete(house);
  }

  @Override
  public Optional<House> findHouseByCadastralNo(final String cadastralNo) {
    return repository.findByCadastralNo(cadastralNo);
  }
}

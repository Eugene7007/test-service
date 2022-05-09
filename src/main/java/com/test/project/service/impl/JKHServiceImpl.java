package com.test.project.service.impl;

import com.test.project.dto.JKHReqDto;
import com.test.project.entity.House;
import com.test.project.entity.JKH;
import com.test.project.entity.Plumber;
import com.test.project.exception.AllowableHousesException;
import com.test.project.exception.BusinessObjectDuplicateException;
import com.test.project.exception.BusinessObjectNotFoundException;
import com.test.project.exception.HouseAlreadyBindException;
import com.test.project.repository.JKHRepository;
import com.test.project.service.HouseService;
import com.test.project.service.JKHService;
import com.test.project.service.PlumberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.test.project.common.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JKHServiceImpl implements JKHService {

  JKHRepository repository;
  HouseService houseService;
  PlumberService plumberService;

  @Override
  @Transactional
  public JKH save(final JKH jkh) {
    return repository.save(jkh);
  }

  @Override
  public void bindPlumberToHouse(final JKHReqDto dto) {
    List<JKH> records = repository.findHousesInfoByPinfl(dto.getPinfl());
    if (records.size() > MAX_ALLOWABLE_HOUSES)
      throw new AllowableHousesException(MAX_ALLOWABLE_HOUSES_MSG);

    records
        .stream()
        .filter(v-> v.getHouse().getCadastralNo().equals(dto.getCadastralNo()))
        .findAny()
        .ifPresent(v-> {throw new HouseAlreadyBindException(HOUSE_ALREADY_BIND_MSG);});

    repository
        .findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo())
        .ifPresent(v -> {
          throw new BusinessObjectDuplicateException(JKH_DUPLICATE_MSG);
        });

    final House house = houseService
        .findHouseByCadastralNo(dto.getCadastralNo())
        .orElseThrow(() -> new BusinessObjectNotFoundException(HOUSE_NOT_FOUND_MSG + dto.getCadastralNo()));

    final Plumber plumber = plumberService
        .findByPinfl(dto.getPinfl())
        .orElseThrow(() -> new BusinessObjectNotFoundException(PLUMBER_NOT_FOUND_MSG + dto.getPinfl()));

    save(JKH.builder()
        .house(house)
        .plumber(plumber)
        .build());
  }

  @Override
  @Transactional
  public void unbindPlumberFromHouse(final JKHReqDto dto) {
    final JKH record = repository
        .findJkhRecordByPinflAndCadastralNo(dto.getPinfl(), dto.getCadastralNo())
        .orElseThrow(() -> new BusinessObjectNotFoundException(JKH_NOT_FOUND_MSG));

    record.removeRecordFromRegister();

    repository.delete(record);
  }

  @Override
  public List<House> findHousesInfoByPinfl(final String pinfl) {
    return repository
        .findHousesInfoByPinfl(pinfl)
        .stream()
        .map(JKH::getHouse)
        .collect(Collectors.toList());
  }

  @Override
  public Plumber findPlumberInfoByCadastralNo(String cadastralNo) {
    Optional<JKH> optional = repository.findPlumberInfoByCadastralNo(cadastralNo);
    if (optional.isEmpty())
      throw new BusinessObjectNotFoundException(PLUMBER_NOT_FOUND_MSG + cadastralNo);

    return optional.get().getPlumber();
  }
}

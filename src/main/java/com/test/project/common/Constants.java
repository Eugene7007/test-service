package com.test.project.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

  public static final String JKH_DUPLICATE_MSG = "Record already exists with";
  public static final String JKH_NOT_FOUND_MSG = "Record can't be find in register";
  public static final String PLUMBER_DUPLICATE_MSG = "Plumber already exists with pinfl - ";
  public static final String PLUMBER_NOT_FOUND_MSG = "Plumber can't be find with pinfl - ";

  public static final String HOUSE_DUPLICATE_MSG = "House already exists with cadastralNo - ";
  public static final String HOUSE_NOT_FOUND_MSG = "House can't be find with cadastralNo - ";
  public static final String HOUSE_ALREADY_BIND_MSG = "House already bind";
  public static final long MAX_ALLOWABLE_HOUSES = 5;
  public static final String MAX_ALLOWABLE_HOUSES_MSG = "Max allowable limit houses was reached for per plumber";
}

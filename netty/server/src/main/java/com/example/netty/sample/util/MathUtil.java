package com.example.netty.sample.util;

import java.math.BigInteger;

public class MathUtil {

  public static int convertToInt(String resource) {
    return new BigInteger(resource).intValue();
  }

  public static long convertToLong(String resource) {
    return new BigInteger(resource).longValue();
  }
}

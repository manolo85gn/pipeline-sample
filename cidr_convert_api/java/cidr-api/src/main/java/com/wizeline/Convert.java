package com.wizeline;

import java.util.StringJoiner;

public class Convert {

  /**
   * Method to calculate the mask by the cidr
   * @param cidr e.g. 24
   * @return
   */
  public static String cidrToMask(String cidr) {

    if(cidr == null || !cidrValidation(cidr) ) {
      return "Invalid";
    }

    String plainMask = generatePlainMask(cidr);
    String[] tokens = plainMaskToTokens(plainMask);
    return maskTokensToString(tokens);
  }

  /**
   * Method to calculate the cidr by the mask
   *
   * @param mask e.g. 255.0.0.0
   * @return cidr
   */
  public static String maskToCidr(String mask) {

    if(mask == null || !maskValidation(mask)) {
      return "Invalid";
    }

    return calculateCidr(mask).toString();
  }

  /**
   * IP validation
   *
   * @param ip e.g. 198.12.1.1
   * @return
   */
  public static Boolean ipv4Validation(String ip) {

    String[] tokens = ip != null ? ip.split("\\.") : null;
    if (tokens == null || tokens.length != 4) {
      return false;
    }

    boolean result = true;
    for (int i = 0; i < tokens.length; i++) {
      result = result && integerValidation(tokens[i]) && rangeValidation(tokens[i]);
      if(!result) {
        break;
      }
    }

    return result;
  }

  private static String generatePlainMask(String input) {
    String tempMask = "";
    Integer cidrAsInt = new Integer(input);
    for(int i = 0; i < cidrAsInt; i++) {
      tempMask += "1";
    }

    for(int i=cidrAsInt; i < 32; i++) {
      tempMask += "0";
    }

    return tempMask;
  }

  private static String[] plainMaskToTokens(String plainMask) {
    String[] tokens = new String[4];
    for(int tokenIndex = 0, from = 0, to = 8; to <= 32; from+=8, to+=8, tokenIndex++) {
      tokens[tokenIndex] = plainMask.substring(from, to);
    }

    return tokens;
  }

  private static String maskTokensToString(String[] tokens) {
    StringJoiner joiner = new StringJoiner(".", "", "");
    for(int i = 0; i < tokens.length; i++) {
      int tokenInt = Integer.parseInt(tokens[i], 2);
      joiner.add(tokenInt + "");
    }
    return joiner.toString();
  }

  private static Integer calculateCidr(String input) {
    String[] maskTokens = input.split("\\.");
    Integer cidr = 0;
    for(int i = 0; i < maskTokens.length; i++) {
      String binaryToken = Integer.toBinaryString(new Integer(maskTokens[i]));
      cidr += countBits(binaryToken.toCharArray());
    }
    return cidr;
  }

  private static Integer countBits(char[] chars) {
    Integer count = 0;
    for(int y = 0; y < chars.length; y++) {
      if(chars[y] == '1') {
        count++;
      }
    }
    return count;
  }

  private static Boolean rangeValidation(String token) {
    String[] tokenArr = token.split("\\.");
    boolean result = true;
    for(int i=0; i <  tokenArr.length; i++) {
      Integer tokenAsInt = Integer.parseInt(tokenArr[i]);
      result = tokenAsInt >= 0 && tokenAsInt <= 255;
      if(!result) {
        break;
      }
    }

    return result;
  }

  private static Boolean integerValidation(String token) {
    try {
      Integer.parseInt(token);
    } catch (NumberFormatException e) {
      return false;
    }

    return true;
  }

  private static Boolean integerTokensValidation(String input) {
    String[] tokens = input.split("\\.");
    boolean result = true;
    for(int i = 0; i < tokens.length; i++) {
      result = result && integerValidation(tokens[i]);
    }
    return result;
  }

  private static Boolean maskLengthValidation(String input) {
    String[] tokens = input.split("\\.");
    if (tokens.length != 4) {
      return false;
    }

    return true;
  }

  private static Boolean firstGreaterThanZero(String input) {
    String[] tokens = input.split("\\.");
    return Integer.parseInt(tokens[0]) > 0;
  }

  private static Boolean cidrValidation(String input) {
    return integerValidation(input) && integerGreaterThanZero(input);
  }

  private static Boolean maskValidation(String input) {
    return maskLengthValidation(input) && integerTokensValidation(input) && firstGreaterThanZero(input) && rangeValidation(input);
  }

  private static Boolean integerGreaterThanZero(String input) {
    return Integer.parseInt(input) > 0;
  }
}

package org.iakuh.skeleton.common.exception;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;

public abstract class BaseException extends Exception {

  private final String errorCode;
  private final String messageCode;
  private final String[] args;

  public BaseException(String errorCode, String messageCode, Object... args) {
    this.errorCode = errorCode;
    this.messageCode = messageCode;
    this.args = new String[args.length];
    Arrays.stream(args).map(String::valueOf).collect(toList()).toArray(this.args);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessageCode() {
    return messageCode;
  }

  public String[] getArgs() {
    return args;
  }
}

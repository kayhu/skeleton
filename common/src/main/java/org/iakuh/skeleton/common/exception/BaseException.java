package org.iakuh.skeleton.common.exception;

public abstract class BaseException extends Exception {

  private final String errorCode;
  private final String messageCode;
  private final Object[] args;

  public BaseException(String errorCode, String messageCode, Object... args) {
    this.errorCode = errorCode;
    this.messageCode = messageCode;
    this.args = args;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessageCode() {
    return messageCode;
  }

  public Object[] getArgs() {
    return args;
  }
}

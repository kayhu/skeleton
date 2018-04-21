package org.iakuh.skeleton.api.exception;

import org.iakuh.skeleton.common.error.ErrorCode;
import org.iakuh.skeleton.common.exception.BaseException;

public class NotFoundException extends BaseException {

  public NotFoundException(String messageCode) {
    super(ErrorCode.E_RESOURCE_NOT_FOUND, messageCode);
  }
}

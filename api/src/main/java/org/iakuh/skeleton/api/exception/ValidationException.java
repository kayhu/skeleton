package org.iakuh.skeleton.api.exception;

import org.iakuh.skeleton.common.error.ErrorCode;
import org.iakuh.skeleton.common.exception.BaseException;
import org.springframework.validation.AbstractErrors;

public class ValidationException extends BaseException {

  private final AbstractErrors errors;

  public ValidationException(AbstractErrors errors) {
    super(ErrorCode.E_VALIDATION_FAILURE, "error.object.not.valid");
    this.errors = errors;
  }

  public AbstractErrors getErrors() {
    return errors;
  }
}

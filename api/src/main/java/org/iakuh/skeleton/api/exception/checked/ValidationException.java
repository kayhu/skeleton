package org.iakuh.skeleton.api.exception.checked;

import org.springframework.validation.Errors;

public class ValidationException extends Exception {

  private Errors errors;

  public ValidationException(Errors errors) {
    this.errors = errors;
  }

  public Errors getErrors() {
    return errors;
  }

  public void setErrors(Errors errors) {
    this.errors = errors;
  }
}

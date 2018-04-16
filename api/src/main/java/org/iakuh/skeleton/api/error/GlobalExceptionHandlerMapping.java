package org.iakuh.skeleton.api.error;

import static org.iakuh.skeleton.common.error.ErrorCode.E_RESOURCE_NOT_FOUND;
import static org.iakuh.skeleton.common.error.ErrorCode.E_VALIDATION_FAILURE;

import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.api.exception.checked.ValidationException;
import org.iakuh.skeleton.common.error.ExceptionHandlerMapping;
import org.iakuh.skeleton.common.model.vo.ErrorVo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GlobalExceptionHandlerMapping extends ExceptionHandlerMapping {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ErrorVo onNotFoundException(NotFoundException e) {
    return new ErrorVo(E_RESOURCE_NOT_FOUND, e.getMessage(), null);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public ErrorVo onValidationException(ValidationException e) {
    ErrorVo error = new ErrorVo(E_VALIDATION_FAILURE, "Invalid request object", null);
    for (FieldError fieldError : e.getErrors().getFieldErrors()) {
      error.addDetail(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }
    return error;
  }
}

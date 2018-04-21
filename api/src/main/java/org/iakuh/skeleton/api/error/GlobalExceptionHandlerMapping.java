package org.iakuh.skeleton.api.error;

import org.iakuh.skeleton.api.exception.NotFoundException;
import org.iakuh.skeleton.api.exception.ValidationException;
import org.iakuh.skeleton.common.error.ExceptionHandlerMapping;
import org.iakuh.skeleton.common.model.vo.ErrorVo;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GlobalExceptionHandlerMapping extends ExceptionHandlerMapping {

  public GlobalExceptionHandlerMapping(ApplicationContext applicationContext) {
    super(applicationContext);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ErrorVo onNotFoundException(NotFoundException e) {
    return convert(e);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public ErrorVo onValidationException(ValidationException e) {
    ErrorVo error = convert(e);
    for (FieldError fieldError : e.getErrors().getFieldErrors()) {
      error.addDetail(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }
    return error;
  }
}

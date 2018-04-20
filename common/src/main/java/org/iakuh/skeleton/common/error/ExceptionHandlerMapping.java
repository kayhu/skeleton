package org.iakuh.skeleton.common.error;

import static org.iakuh.skeleton.common.error.ErrorCode.E_AUTHENTICATION_ERROR;
import static org.iakuh.skeleton.common.error.ErrorCode.E_RESOURCE_ACCESS_DENIED;
import static org.iakuh.skeleton.common.error.ErrorCode.E_UNKNOWN_ERROR;

import org.iakuh.skeleton.common.model.vo.ErrorVo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ExceptionHandlerMapping {

  protected static final String HANDLER_METHOD = "_skeleton_handler_method_";

  @ResponseBody
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  public ErrorVo onAccessDeniedException(AccessDeniedException e) {
    return new ErrorVo(E_RESOURCE_ACCESS_DENIED, e.getMessage(), null);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthenticationException.class)
  public ErrorVo onAuthenticationException(AuthenticationException e) {
    return new ErrorVo(E_AUTHENTICATION_ERROR, e.getMessage(), null);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ErrorVo onRuntimeException(RuntimeException e) {
    return new ErrorVo(E_UNKNOWN_ERROR, "System unknown error", e.getMessage());
  }
}

package org.iakuh.skeleton.common.error;

import static org.iakuh.skeleton.common.error.ErrorCode.E_AUTHENTICATION_ERROR;
import static org.iakuh.skeleton.common.error.ErrorCode.E_INTERNAL_SERVER_ERROR;
import static org.iakuh.skeleton.common.error.ErrorCode.E_RESOURCE_ACCESS_DENIED;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.iakuh.skeleton.common.exception.BaseException;
import org.iakuh.skeleton.common.helper.ServletCtxHelper;
import org.iakuh.skeleton.common.model.vo.ErrorVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public class ExceptionHandlerMapping {

  protected static final String HANDLER_METHOD = "_skeleton_handler_method_";

  private ApplicationContext applicationContext;

  public ExceptionHandlerMapping(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  protected ErrorVo convert(BaseException e) {
    String code = e.getErrorCode();
    String message = getMessage(e.getMessageCode(), e.getArgs());
    return new ErrorVo(code, message, null);
  }

  private String getMessage(String messageCode, Object[] args) {
    try {
      return applicationContext.getMessage(messageCode, args, getLocale());
    } catch (NoSuchMessageException e) {
      return messageCode;
    }
  }

  private Locale getLocale() {
    try {
      return ServletCtxHelper.getRequest().getLocale();
    } catch (Exception e) {
      log.warn(e.getMessage());
      return Locale.getDefault();
    }
  }

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
    log.error("INTERNAL_SERVER_ERROR:", e);
    String message = applicationContext.getMessage(
        "error.system.unknown.exception", null,
        ServletCtxHelper.getRequest().getLocale());
    return new ErrorVo(E_INTERNAL_SERVER_ERROR, message, null);
  }
}

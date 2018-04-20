package org.iakuh.skeleton.api.exception.checked;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.AbstractErrors;

@Getter
@Setter
@AllArgsConstructor
public class ValidationException extends Exception {

  private final AbstractErrors errors;
}

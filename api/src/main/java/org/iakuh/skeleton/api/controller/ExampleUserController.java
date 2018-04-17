package org.iakuh.skeleton.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.api.exception.checked.ValidationException;
import org.iakuh.skeleton.api.service.UserService;
import org.iakuh.skeleton.common.model.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/users")
public class ExampleUserController {

  @Autowired
  private UserService userService;

  @ApiOperation(value = "获取用户信息", httpMethod = "GET")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = UserVo.class)})
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity getUser(@PathVariable Long id) throws NotFoundException {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @ApiOperation(value = "添加用户信息", httpMethod = "POST")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = UserVo.class)})
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity addUser(@Validated @RequestBody UserVo vo, Errors errors)
      throws ValidationException {
    if (errors.hasFieldErrors()) {
      throw new ValidationException(errors);
    }
    return new ResponseEntity<>(userService.addUser(vo), HttpStatus.OK);
  }

  @ApiOperation(value = "上传身份证", httpMethod = "POST")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @RequestMapping(value = "/{id}/idCard", method = RequestMethod.POST)
  public ResponseEntity uploadIdCard(@PathVariable Long id, @RequestPart MultipartFile file) {
    return new ResponseEntity<>(Collections.EMPTY_MAP, HttpStatus.OK);
  }
}

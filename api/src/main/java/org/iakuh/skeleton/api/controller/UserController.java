package org.iakuh.skeleton.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.iakuh.skeleton.api.exception.checked.NotFoundException;
import org.iakuh.skeleton.api.exception.checked.ValidationException;
import org.iakuh.skeleton.api.service.UserService;
import org.iakuh.skeleton.common.model.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class UserController {

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
    public ResponseEntity addUser(@Validated @RequestBody UserVo vo, Errors errors) throws ValidationException {
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

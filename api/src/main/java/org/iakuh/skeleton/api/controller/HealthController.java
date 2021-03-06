package org.iakuh.skeleton.api.controller;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthController {

  @ApiOperation(value = "检查服务状态", httpMethod = "GET")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @RequestMapping(value = "/health", method = RequestMethod.GET)
  @Counted(name = "health")
  public ResponseEntity health() {
    log.debug("I'm fine!");
    return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
  }
}

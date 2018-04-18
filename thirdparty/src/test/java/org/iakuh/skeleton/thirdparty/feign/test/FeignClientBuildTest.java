package org.iakuh.skeleton.thirdparty.feign.test;

import static org.junit.Assert.assertEquals;

import feign.Param;
import feign.RequestLine;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.iakuh.skeleton.thirdparty.feign.FeignClientBuilder;
import org.iakuh.skeleton.thirdparty.feign.FeignClientConfig;
import org.junit.Test;

@Slf4j
public class FeignClientBuildTest {

  @Test
  public void testFeignClientBuilder() {
    FeignClientConfig<PostmanEchoApi> clientConfig = new FeignClientConfig<>();
    clientConfig.setApiType(PostmanEchoApi.class);
    clientConfig.setUrl("https://postman-echo.com");
    PostmanEchoApi postmanEchoApi = FeignClientBuilder.build(clientConfig);

    final int status = 200;
    Status echo = postmanEchoApi.echo(status);
    assertEquals(echo.getStatus(), status);
  }

  @Test
  public void testFeignClientBuilder2() {
    FeignClientConfig<PostmanEchoApi> clientConfig = new FeignClientConfig<>();
    clientConfig.setApiType(PostmanEchoApi.class);
    clientConfig.setUrl("https://postman-echo.com");
    clientConfig.setDecode404(true);
    clientConfig.setCloseAfterDecode(false);
    PostmanEchoApi postmanEchoApi = FeignClientBuilder.build(clientConfig);

    final int status = 200;
    Status echo = postmanEchoApi.echo(status);
    assertEquals(echo.getStatus(), status);
  }
}

@Getter
@Setter
class Status {

  private int status;
}

interface PostmanEchoApi {

  @RequestLine("GET /status/{status}")
  Status echo(@Param("status") int status);
}
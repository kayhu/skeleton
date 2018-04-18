package org.iakuh.skeleton.thirdparty.feign;

import feign.Feign;

public class FeignClientBuilder {

  public static <T> T build(FeignClientConfig<T> clientConfig) {
    Feign.Builder builder = Feign.builder();

    if (clientConfig.isDecode404()) {
      builder.decode404();
    }

    if (!clientConfig.isCloseAfterDecode()) {
      builder.doNotCloseAfterDecode();
    }

    return builder.requestInterceptors(clientConfig.getRequestInterceptors())
        .logLevel(clientConfig.getLogLevel())
        .contract(clientConfig.getContract())
        .client(clientConfig.getClient())
        .retryer(clientConfig.getRetryer())
        .logger(clientConfig.getLogger())
        .encoder(clientConfig.getEncoder())
        .decoder(clientConfig.getDecoder())
        .errorDecoder(clientConfig.getErrorDecoder())
        .options(clientConfig.getOptions())
        .invocationHandlerFactory(clientConfig.getInvocationHandlerFactory())
        .target(clientConfig.getApiType(), clientConfig.getUrl());
  }
}

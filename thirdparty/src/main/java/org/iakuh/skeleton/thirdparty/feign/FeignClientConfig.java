package org.iakuh.skeleton.thirdparty.feign;

import com.google.common.collect.Lists;
import feign.Client;
import feign.Contract;
import feign.InvocationHandlerFactory;
import feign.Logger;
import feign.Logger.Level;
import feign.Request.Options;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeignClientConfig<T> {

  private final Iterable<RequestInterceptor> requestInterceptors =
      Lists.newArrayList(new NoOpRequestInterceptor());

  private Level logLevel = Level.FULL;

  private Contract contract = new Contract.Default();

  private Client client = new Client.Default(null, null);

  private Retryer retryer = Retryer.NEVER_RETRY;

  private Logger logger = new Slf4jLogger();

  private Encoder encoder = new JacksonEncoder();

  private Decoder decoder = new JacksonDecoder();

  private ErrorDecoder errorDecoder = new ErrorDecoder.Default();

  private Options options = new Options();

  private InvocationHandlerFactory invocationHandlerFactory =
      new InvocationHandlerFactory.Default();

  private boolean decode404;

  private boolean closeAfterDecode = true;

  private Class<T> apiType;

  private String url;

  static class NoOpRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
      // do nothing
    }
  }
}
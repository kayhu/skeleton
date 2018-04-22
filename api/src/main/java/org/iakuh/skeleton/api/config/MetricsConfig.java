package org.iakuh.skeleton.api.config;

import static com.codahale.metrics.servlets.HealthCheckServlet.HEALTH_CHECK_REGISTRY;
import static com.codahale.metrics.servlets.MetricsServlet.METRICS_REGISTRY;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.CpuProfileServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.codahale.metrics.servlets.PingServlet;
import com.codahale.metrics.servlets.ThreadDumpServlet;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.ServletWrappingController;

@Configuration
public class MetricsConfig implements ServletContextAware {

  @Autowired
  @Qualifier("metricsRegistry")
  private MetricRegistry metricsRegistry;

  @Autowired
  @Qualifier("healthCheckRegistry")
  private HealthCheckRegistry healthCheckRegistry;

  @Bean("metricsRegistry")
  public MetricRegistry metricsRegistry() {
    MetricRegistry metricRegistry = new MetricRegistry();
    metricRegistry.register("jvm.gc", new GarbageCollectorMetricSet());
    metricRegistry.register("jvm.memory", new MemoryUsageGaugeSet());
    metricRegistry.register("jvm.thread.states", new ThreadStatesGaugeSet());
    metricRegistry.register("jvm.fd.usage", new FileDescriptorRatioGauge());
    return metricRegistry;
  }

  @Bean("healthCheckRegistry")
  public HealthCheckRegistry healthCheckRegistry() {
    HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
    healthCheckRegistry.register("threadDeadlock", new ThreadDeadlockHealthCheck());
    return healthCheckRegistry;
  }

  @Bean("adminController")
  public Controller adminController() {
    ServletWrappingController controller = new ServletWrappingController();
    controller.setServletClass(AdminServlet.class);
    controller.setServletName("adminServlet");
    return controller;
  }

  @Bean("metricsController")
  public Controller metricsController() {
    ServletWrappingController controller = new ServletWrappingController();
    controller.setServletClass(MetricsServlet.class);
    controller.setServletName("metricsServlet");
    return controller;
  }

  @Bean("pingController")
  public Controller pingController() {
    ServletWrappingController controller = new ServletWrappingController();
    controller.setServletClass(PingServlet.class);
    controller.setServletName("pingServlet");
    return controller;
  }

  @Bean("threadDumpController")
  public Controller threadDumpController() {
    ServletWrappingController controller = new ServletWrappingController();
    controller.setServletClass(ThreadDumpServlet.class);
    controller.setServletName("threadDumpServlet");
    return controller;
  }

  @Bean("healthCheckController")
  public Controller healthCheckController() {
    ServletWrappingController controller = new ServletWrappingController();
    controller.setServletClass(HealthCheckServlet.class);
    controller.setServletName("healthCheckServlet");
    return controller;
  }

  @Bean("cpuProfileController")
  public Controller cpuProfileController() {
    ServletWrappingController controller = new ServletWrappingController();
    controller.setServletClass(CpuProfileServlet.class);
    controller.setServletName("cpuProfileServlet");
    return controller;
  }

  @Bean
  public HandlerMapping handlerMapping() {
    SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
    Properties mappings = new Properties();
    mappings.setProperty("/statistics", "adminController");
    mappings.setProperty("/statistics/metrics", "metricsController");
    mappings.setProperty("/statistics/ping", "pingController");
    mappings.setProperty("/statistics/threads", "threadDumpController");
    mappings.setProperty("/statistics/healthcheck", "healthCheckController");
    mappings.setProperty("/statistics/pprof", "cpuProfileController");
    handlerMapping.setMappings(mappings);
    handlerMapping.setOrder(1000);
    handlerMapping.setAlwaysUseFullPath(true);
    return handlerMapping;
  }

  @Override
  public void setServletContext(ServletContext servletContext) {
    servletContext.setAttribute(METRICS_REGISTRY, metricsRegistry);
    servletContext.setAttribute(HEALTH_CHECK_REGISTRY, healthCheckRegistry);
  }
}

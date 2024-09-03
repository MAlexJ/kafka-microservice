package com.malex.storage_service;

import com.malex.storage_service.configuration.rabbitmq.binding.ForwardQueueConfiguration;
import com.malex.storage_service.configuration.rabbitmq.binding.ReplayQueueConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class StorageServiceApplicationTests {

  /*
   * solution: https://stackoverflow.com/questions/39729752/java-spring-boot-test-how-to-exclude-java-configuration-class-from-test-context
   * You can also just mock the configuration you don't need. For example:
   *
   * @MockBean
   * private AnyConfiguration conf;
   *
   * Put it into your test class. This should help to avoid that the real AnyConfiguration is being loaded.
   */
  @MockBean private ForwardQueueConfiguration forwardQueueConfiguration;

  @MockBean private ReplayQueueConfiguration replayQueueConfiguration;

  @Test
  void contextLoads() {}
}

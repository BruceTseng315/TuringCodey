package turing.turingcodey.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author JasonC
 * @date 2017-10-30-0030
 */
@Configuration
public class CustomBeans {


  @Bean(name = "baseMapperUtil")
  public DozerBeanMapper getMapper() {
    return new DozerBeanMapper();
  }

}

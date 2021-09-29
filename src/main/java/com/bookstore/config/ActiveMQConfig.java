package com.bookstore.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ActiveMQConfig {

  /**
   * 发布-订阅模式的ListenerContainer
   */
  @Bean
  public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory factory,
      DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
    configurer.configure(jmsListenerContainerFactory, factory);
    jmsListenerContainerFactory.setConnectionFactory(factory);
    return jmsListenerContainerFactory;
  }

  @Bean // Serialize message content to json using TextMessage
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

}

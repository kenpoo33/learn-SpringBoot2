package com.sample.project.appcmn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * AppConfigクラス.
 * <p>
 * アプリケーションの設定を定義.</br>
 * </p>
 *
 * @author user
 *
 */
@Configuration
public class AppConfig {

  /**
   * リクエストロガー定義.
   *
   * @return ModelMapper
   */
  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeClientInfo(true);
    filter.setIncludeQueryString(true);
    filter.setIncludeHeaders(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(1024);
    return filter;
  }
}

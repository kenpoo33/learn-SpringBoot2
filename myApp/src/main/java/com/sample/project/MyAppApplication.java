package com.sample.project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.Transactional;

/**
 * アプリケーション.
 *
 * @author user
 *
 */
@SpringBootApplication
public class MyAppApplication extends SpringBootServletInitializer {

  /**
   * main.
   * <p>
   * Springアプリケーションを起動する。
   * </p>
   *
   * @param args 引数リスト
   */
  @Transactional(rollbackFor = Exception.class)
  public static void main(String[] args) {
    SpringApplication.run(MyAppApplication.class, args);
  }

  /**
   * configure(SpringApplicationBuilder).
   * <p>
   * war作成時に呼び出される処理をoverrideするる.
   *</p>
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(MyAppApplication.class);
  }
}

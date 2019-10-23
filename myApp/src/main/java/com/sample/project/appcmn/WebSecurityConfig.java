package com.sample.project.appcmn;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.sample.project.cns.BcryptCns;

/**
 * WebSecurityクラス.
 * <p>
 * サーバ側へのアクセス制御をおこなう。<br>
 * ・CSRF(クロスサイトリクエストフォージェリ)対策の設定<br>
 * ・CORS(クロスドメイン通信)対策の設定<br>
 * ・画面のアクセス権限<br>
 * ・ログイン認証時にパスワード照合する際のハッシュ変換用アルゴリズム指定<br>
 * ログイン認証について<br>
 * ログイン認証に成功すると、DBのテーブル上にセッションが登録されます。<br>
 * （ちなみにセッション登録用のテーブルレイアウトはフレームワークで固定されています。）<br>
 * その際に作成されたセッションIDを、Httpレスポンスヘッダーに渡しやりとりすることで、<br>
 * 認証済み（ログイン済み）であるかやAPI実行権限があるかを確認しています。<br>
 * </p>
 *
 * @author user
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /** クライアント側テストアクセス用アドレス */
  private static final String LOCAL_CLIENT_TEST_IP = "http://localhost:3000";
  /** サーバ側テストアクセス用アドレス */
  private static final String LOCAL_SERVER_TEST_IP = "http://localhost:8081";
  /** 認証チェック不要エンドポイント */
  private static final String PERMIT_ALL_ADDRESS1 = "/loginStatus";
  private static final String PERMIT_ALL_ADDRESS2 = "/login";


  /**
   * WebSecurityコンフィグ.
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    // 全てのリソースアクセス不可
    web.ignoring().antMatchers("/images/**", "/scss/**", "/javascript/**", "/webjars/**");
  }


  /**
   * HttpSecurityコンフィグ.
   * <p>
   * ・CSRF適用設定<br>
   * ・CORS適用設定<br>
   * ・アクセス制御設定<br>
   * </p>
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    /**
     * CSRF適用URL判断クラス. CSRF(クロスサイトリクエストフォージェリ)対策で、<br>
     * 全く関係のないWEBサイトからのリクエストを受け付けないようにします。<br>
     */
    RequestMatcher csrfRequestMatcher = new RequestMatcher() {

      // TODO CSRFチェックは余力があったら（クライアントと相性悪そう）
      /*
       * CSRFチェックするバージョン
       */
      // Loginのパス以外は全てCSRFチェックを行う
      private AntPathRequestMatcher[] requestMatchers =
          {new AntPathRequestMatcher(PERMIT_ALL_ADDRESS1),
              new AntPathRequestMatcher(PERMIT_ALL_ADDRESS2)};

      @Override
      public boolean matches(HttpServletRequest request) {
        for (AntPathRequestMatcher rm : requestMatchers) {
          return false;
          // if (rm.matches(request)) {
          // return false;
          // }
        }
        return true;
      }
    };

    // CSRFチェック対象パターンを登録
    http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher);
    // CSRFトークンを設定
    http.addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class);

    /*
     * 認可の設定
     */
    http.cors().configurationSource(this.corsConfigurationSource()).and().authorizeRequests()
        .antMatchers(PERMIT_ALL_ADDRESS1).permitAll().antMatchers(PERMIT_ALL_ADDRESS2).permitAll()
        .anyRequest().authenticated().and().csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    // /*
    // * 認可の設定 全て認可OKの設定
    // */
    // http.cors().configurationSource(this.corsConfigurationSource()).and().authorizeRequests()
    // .antMatchers("/**").permitAll();
  }

  /**
   * CORSコンフィグ.
   *
   * @return corsSource CORS設定情報
   */
  private CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
    corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
    corsConfiguration.setAllowedOrigins(Arrays.asList(LOCAL_CLIENT_TEST_IP, LOCAL_SERVER_TEST_IP));
    corsConfiguration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
    corsSource.registerCorsConfiguration("/**", corsConfiguration);

    return corsSource;
  }

  /**
   * AuthenticationManagerをDIする.<br>
   * SpringSecurityのおまじない的なものの為、説明無<br>
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * GlobalAuthenticaionConfig. 認証時のパスワード照合方式の指定。<br>
   * 照合時に必要な情報を取得するサービスクラスの指定を行う。<br>
   *
   * @author user
   *
   */
  @Configuration
  public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    /**
     * パスワード方式の指定.<br>
     * Bvryptでパスワードを照合する。<br>
     */
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
      // 認証するユーザーを設定する
      auth.userDetailsService(userDetailsService())
          // 入力値をbcryptでハッシュ化した値でパスワード認証を行う
          .passwordEncoder(new BCryptPasswordEncoder(BcryptCns.BCRYPT_SALT_CNT));
    }

    /**
     * 照合時のユーザ情報取得クラスの指定.<br>
     */
    @Bean
    UserDetailsService userDetailsService() {
      return new LoginUserDetailService();
    }
  }
}

package com.sample.project.appcmn;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * <p>
 * CsrfCookieFilter
 * </p>
 * CSRFフィルター.<br>
 * ログイン時にCSRFトークンを作成する<br>
 *
 * @author user
 *
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

  /** CookieにCSRFを設定する際の名称 */
  private static final String CSRF_COOKIE_NAME = "X-XSRF-TOKEN";

  /** CookieにCSRFを設定する際の有効範囲 */
  private static final String CSRF_COOKIE_PATH = "/";

  /**
   * doFilterInternal.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // CSRFトークン取得
    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

    if (csrf != null) {
      // トークンの値を取得
      String token = csrf.getToken();
      // リクエストCookieにトークンを取得
      Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
      // リクエストのトークンとCookieのトークンが一致するか確認する
      if (cookie == null || token != null && !token.equals(cookie.getValue())) {

        // 一致しない場合はトークンを再作成する
        cookie = new Cookie(CSRF_COOKIE_NAME, token);
        cookie.setPath(CSRF_COOKIE_PATH);
        response.addCookie(cookie);
      }
    }

    filterChain.doFilter(request, response);
  }
}

package com.sample.project.appcmn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * <p>
 * ハンドラー処理.
 * </p>
 *
 * @author user
 *
 */
public class HandlerInterceptor extends HandlerInterceptorAdapter {

  /**
   * <p>
   * コントローラ実行前処理.
   * </p>
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println("preHandle");
    return true;
  }

  /**
   * <p>
   * コントローラ実行後処理.
   * </p>
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    System.out.println("afterCompletion");
  }
}

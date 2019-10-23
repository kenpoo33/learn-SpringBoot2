package com.sample.project.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sample.project.appcmn.CmnController;
import com.sample.project.appcmn.CmnException;
import com.sample.project.appcmn.CmnForm;
import com.sample.project.service.LoginBizService;
import com.sample.project.web.form.LoginForm;

/**
 * ログイン画面コントローラ.
 *
 * @author user
 *
 */
@RestController
@Validated
public class LoginBizController extends CmnController {

  @Autowired
  LoginBizService loginBizService;

  /**
   * ログインボタン押下.
   *
   * <p>
   * １．ログインサービスを呼び出す。<br>
   * ２．
   * </p>
   *
   * @param LoginForm ログイン情報フォーム.
   * @return ログイン情報フォーム
   * @throws CmnException 例外.
   */
  @RequestMapping(value = "/login", method = {RequestMethod.POST},
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public LoginForm login(@RequestBody @Valid LoginForm loginForm) throws CmnException {
    loginForm = loginBizService.login(loginForm);

    return (LoginForm) after(loginForm);
  }

  /**
   * ログイン者情報取得.
   *
   * @return ログイン情報フォーム.
   * @throws CmnException 例外.
   */
  @RequestMapping(value = "/loginInfo")
  public LoginForm loginInfo() throws CmnException {
    LoginForm loginForm = loginBizService.getLoginSts();

    return (LoginForm) after(loginForm);
  }

  /**
   * ログアウトボタン押下.
   *
   * <p>
   * １．セッションを破棄する。<br>
   * </p>
   *
   * @param request リクエスト.
   * @throws RuntimeException 例外.
   */
  @RequestMapping(value = "/logout", method = {RequestMethod.POST},
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CmnForm logout(HttpServletRequest request) {
    try {
      request.logout();
      return new CmnForm();

    } catch (ServletException e) {
      throw new RuntimeException(e);
    }
  }
}

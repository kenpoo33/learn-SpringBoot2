package com.sample.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.sample.project.appcmn.CmnException;
import com.sample.project.appcmn.LoginUserDetail;
import com.sample.project.web.form.LoginForm;


/**
 * ログインサービス.
 *
 * @author user
 *
 */
@Service
public class LoginBizService {

  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private SessionService sessionService;

  /**
   * ログイン処理.
   * <p>
   * １．DBに入っているログインID、パスワードを取得する。<br>
   * （UserDetailService#loadUserByUsername()をコール。）<br>
   * ２．入力されたパスワードと上記１で取得したパスワードで照合する。<br>
   * ３．認証OKの場合は、認証結果をcontextholderに設定する。<br>
   * ４．認証済みユーザ情報を格納<br>
   * </p>
   *
   * @param loginForm ログイン用フォーム
   * @return ログイン用フォーム
   * @throws CmnException 例外
   */
  public LoginForm login(LoginForm loginForm) throws CmnException {

    // 認証クラスを作成
    Authentication auth =
        new UsernamePasswordAuthenticationToken(loginForm.getId(), loginForm.getPasswd());
    // パスワード照合する。
    Authentication result = authManager.authenticate(auth);
    // 認証OKの場合は、認証結果をcontextholderに設定(ここで自動でDB登録される。)
    SecurityContextHolder.getContext().setAuthentication(result);
    return loginForm;
  }

  /**
   * ログイン状況取得.
   * <p>
   * １．セッションに有効なログイン情報が残っている場合は、statusに1をセット<br>
   * </p>
   *
   * @param loginForm ログイン用フォーム
   * @return ログイン用フォーム
   * @throws CmnException 例外
   */
  public LoginForm getLoginSts() throws CmnException {
    LoginForm loginForm = new LoginForm();
    LoginUserDetail loginUserDetail = sessionService.getLoginUserData();
    // 認証が成功している場合
    if (loginUserDetail != null) {
      loginForm.setLoginSts("1"); // ログイン済み
    } else {
      loginForm.setLoginSts("0"); // 未ログイン
    }
    return loginForm;
  }
}

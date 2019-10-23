package com.sample.project.service;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.sample.project.appcmn.LoginUserDetail;

/**
 * セッションユーティリティクラス.
 *
 * @author user
 *
 */
@Service
public class SessionService {

  @Autowired
  HttpSession session;

  /**
   * ログインユーザデータ取得.
   * <p>
   * ログインユーザ情報を取得する
   * </p>
   *
   * @return ログインユーザ情報
   */
  public LoginUserDetail getLoginUserData() {
    LoginUserDetail loginUserDetail = null;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // ログイン情報
    if (principal instanceof LoginUserDetail) {
      loginUserDetail = (LoginUserDetail) principal;
      if (!loginUserDetail.isEnabled()) {
        loginUserDetail = null;
      }
    }
    return loginUserDetail;
  }
}

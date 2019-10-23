package com.sample.project.appcmn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import com.sample.project.service.SessionService;

/**
 * 共通コントローラ.
 *
 * @author user
 *
 */
@RestController
@Validated
public class CmnController {

  @Autowired
  private SessionService sessionService;

  /**
   * 終了処理.
   *
   * <p>
   * １．認証情報sessionから共通レスポンス情報をセットする。<br>
   * </p>
   *
   * @param cmnForm 共通フォーム.
   * @return 共通フォーム(共通情報設定済み).
   */
  public CmnForm after(CmnForm cmnForm) {
    LoginUserDetail loginUserDetail = sessionService.getLoginUserData();
    // 認証が成功している場合
    if (loginUserDetail != null) {
      cmnForm.setLoginId(loginUserDetail.getLoginId());
      cmnForm.setLoginName(loginUserDetail.getLoginName());
      cmnForm.setLoginRoleCds(loginUserDetail.getLoginRoleCds());
      cmnForm.setLoginKengenCds(loginUserDetail.getLoginKengenCds());
    }
    return cmnForm;
  }
}

package com.sample.project.appcmn;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * ]
 * <p>
 * LoginUserDetailService
 * </p>
 * Spring-Securityが提供するJavaのインターフェース.<br>
 * ユーザー名とパスワードを保存し、アカウントの情報を<br>
 * セットする。<br>
 *
 * @author user
 *
 */
@Service
public class LoginUserDetailService implements UserDetailsService {

  @Autowired
  private LoginUserDetailMapper loginUserDetailMapper;

  /**
   * loadUserByUsername.
   * <p>
   * IDをキーにログイン共通情報を取得し、UserDtailsに設定する。<br>
   * </p>
   *
   * @author user
   *
   */
  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    // ログイン共通情報を取得
    LoginUserDetailDomain ludd = loginUserDetailMapper.select001(id);
    if (ludd == null) {
      // Controller Adviceで最後メッセージは上書き
      throw new UsernameNotFoundException("User not found for login id: " + id);
    }
    // ロールリストを取得
    List<String> loginRoleList = loginUserDetailMapper.select002(id);
    if (loginRoleList.isEmpty()) {
      // Controller Adviceで最後メッセージは上書き
      throw new UsernameNotFoundException("User not found for login id: " + id);
    }

    // 権限リストを取得
    List<String> loginKengenList = loginUserDetailMapper.select003(id);
    if (loginKengenList.isEmpty()) {
      // Controller Adviceで最後メッセージは上書き
      throw new UsernameNotFoundException("User not found for login id: " + id);
    }

    StringBuilder loginRolecds = new StringBuilder();
    StringBuilder loginKengencds = new StringBuilder();
    // 取得した共通情報数分ループ
    for (String loginRole : loginRoleList) {
      loginRolecds.append(loginRole);
      loginRolecds.append(",");
    }
    // 取得した共通情報数分ループ
    for (String loginKengencd : loginKengenList) {
      loginKengencds.append(loginKengencd);
      loginKengencds.append(",");
    }
    // 末尾のカンマを削除
    loginRolecds.setLength(loginRolecds.length() - 1);
    loginKengencds.setLength(loginKengencds.length() - 1);


    return new LoginUserDetail(ludd, loginRolecds.toString(), loginKengencds.toString());

  }
}

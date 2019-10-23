package com.sample.project.appcmn;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * <p>
 * ログインユーザ情報.
 * </p>
 * Spring-Securityが提供するUserクラスにログインユーザ情報を付加し管理する<br>
 *
 * @author user
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginUserDetail extends User {

  private static final long serialVersionUID = 9127151324717885285L;

  /** ログイン者のID. */
  private String loginId;
  /** ログイン者の名. */
  private String loginName;
  /** ログイン者のパスワード. */
  private String loginPasswd;
  /** ログイン者のロールコード. */
  private String loginRoleCds;
  /** ログイン者の権限コード. */
  private String loginKengenCds;

  /**
   * <p>
   * ログインユーザ情報設定.
   * </p>
   *
   * @param kludd ログイン共通情報.
   * @param loginRoleCds ログイン者ロールコード(複数).
   * @param loginKengenCds ログイン者権限コード(複数).
   */
  public LoginUserDetail(LoginUserDetailDomain kludd, String loginRoleCds, String loginKengenCds) {

    super(kludd.getLoginId(), kludd.getLoginPasswd(), true, true, true, true,
        AuthorityUtils.createAuthorityList(loginKengenCds.split(",", -1)));

    this.loginId = kludd.getLoginId();
    this.loginName = kludd.getLoginName();
    this.loginPasswd = kludd.getLoginPasswd();
    this.loginRoleCds = loginRoleCds;
    this.loginKengenCds = loginKengenCds;
  }
}

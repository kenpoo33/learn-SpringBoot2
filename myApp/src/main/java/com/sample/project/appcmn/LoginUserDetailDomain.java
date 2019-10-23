package com.sample.project.appcmn;

import lombok.Data;

/**
 * ログイン用ドメインクラス.
 *
 * @author user
 *
 */
@Data
public class LoginUserDetailDomain {
  /** ログイン者のID. */
  private String loginId;
  /** ログイン者の名前. */
  private String loginName;
  /** ログイン者のパスワード. */
  private String loginPasswd;
}

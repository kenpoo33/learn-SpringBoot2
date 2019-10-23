package com.sample.project.appcmn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 共通フォーム.
 *
 * @author user
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CmnForm {
  /** ログイン者のID. */
  private String loginId;
  /** ログイン者の名前. */
  private String loginName;
  /** ログイン者のロールコード. */
  private String loginRoleCds;
  /** ログイン者の権限コード. */
  private String loginKengenCds;
}

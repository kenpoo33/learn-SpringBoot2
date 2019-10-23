package com.sample.project.web.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sample.project.appcmn.CmnForm;
import com.sample.project.web.validator.Id;
import lombok.Data;

/**
 * ログインフォーム.
 *
 * @author user
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginForm extends CmnForm {
  /** ID. */
  @Id
  private String id;
  /** パスワード. */
  private String passwd;
  /** ステータス */
  private String loginSts;
}

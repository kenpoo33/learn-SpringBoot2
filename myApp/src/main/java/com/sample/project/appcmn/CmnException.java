package com.sample.project.appcmn;

import lombok.Data;

/**
 * 共通Exception.
 *
 * @author user
 *
 */
@Data
public class CmnException extends Exception {

  /** エラーコード **/
  private String errorCd;
  /** エラー内容（概略） **/
  private String errorMessage;
  /** エラー内容（詳細） **/
  private String errorMessageDetail;

  /**
   * コンストラクタ.
   *
   * @param errorCd エラーコード.
   * @param errorMessage エラー内容（概略）.
   * @param errorMessageDetail エラー内容（詳細）.
   */
  public CmnException(String errorCd, String errorMessage, String errorMessageDetail) {
    this.setErrorCd(errorCd);
    this.setErrorMessage(errorMessage);
    this.setErrorMessageDetail(errorMessageDetail);
  }
}

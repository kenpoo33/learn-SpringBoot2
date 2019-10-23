package com.sample.project.appcmn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.Data;

/**
 * エラー用レスポンスクラス.
 *
 * @author user
 *
 */
@Data
public class ErrorResponse {

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
  public ErrorResponse(String errorCd, String errorMessage, String errorMessageDetail) {
    this.setErrorCd(errorCd);
    this.setErrorMessage(errorMessage);
    this.setErrorMessageDetail(errorMessageDetail);
  }

  /**
   * ResponseEntity作成.
   * <p>
   * エラー時のレスポンスを作成する。<br>
   * </p>
   *
   * @param status ステータス.
   * @return エラーレスポンス.
   */
  public ResponseEntity<ErrorResponse> createResponse(HttpStatus status) {
    return new ResponseEntity<ErrorResponse>(this, status);
  }

  /**
   * ErrorResponse作成.
   * <p>
   * エラー時のレスポンスを作成する。<br>
   * </p>
   *
   * @param e Exception 例外.
   * @return エラーレスポンス.
   */
  public static ResponseEntity<ErrorResponse> createResponse(CmnException e) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse(e.getErrorCd(), e.getErrorMessage(), e.getErrorMessageDetail()),
        HttpStatus.BAD_REQUEST);
  }
}

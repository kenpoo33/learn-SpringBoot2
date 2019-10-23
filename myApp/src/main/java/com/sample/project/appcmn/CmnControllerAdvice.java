package com.sample.project.appcmn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.project.cns.ErrorCns;

/**
 * ControllerAdviceクラス.
 * <p>
 * エラーハンドリングの共通処理を定義.<br>
 * </p>
 *
 * @author user
 *
 */
@ControllerAdvice
@RestController
@ComponentScan("")
public class CmnControllerAdvice {

  static Log log = LogFactory.getLog(CmnControllerAdvice.class);

  /**
   * exception発生時のハンドラー.
   * <p>１．exceptionが発生した場合のレスポンスを整備する。</p>
   * </p>
   * @param req httpリクエスト.
   * @param e 例外.
   * @return レスポンス.
   */
  @ExceptionHandler(CmnException.class)
  public ResponseEntity<ErrorResponse> getException(WebRequest request, CmnException e) {
    log.error("Exception " + e.getErrorCd() + "_" + e.getErrorMessage() + "_"
        + e.getErrorMessageDetail() + "リクエスト：" + request);
    return ErrorResponse.createResponse(e);

  }

  /**
   * 想定外エラー.
   *
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> unknownException(WebRequest request, Exception e) {
    log.error("unknownException 想定外エラー.。リクエスト：" + request, e);
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse(ErrorCns.ERR_CD_UNKNOWN, ErrorCns.ERR_MSG_UNKNOWN, ""),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * GETパラメータのバリデーションエラー(400 -BAD_REQUEST)ハンドラー.
   *
   * @param e 例外.
   * @param request リクエスト.
   * @return レスポンス.
   * @throws JsonProcessingException
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> validationErrorGet(HttpServletRequest request,
      ConstraintViolationException e) {
    ArrayList<String> list = new ArrayList<String>();
    for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
      list.add(violation.getPropertyPath().toString());
    }
    // detail部分昇順ソートする（エラーの出力順が制御できなかったため。）
    Collections.sort(list);
    log.error("ConstraintViolationException GETパラメータのバリデーションエラー。リクエスト：" + request, e);

    // Error Msg リスト
    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCns.ERR_CD_GET_VALIDATION,
        ErrorCns.ERR_MSG_GET_VALIDATION, list.toString().replaceAll(" ", "")),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * POSTのバリデーションエラー(400 -Validation Error(POST))ハンドラー.
   *
   * @param e 例外.
   * @param request リクエスト.
   * @return レスポンス.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> validationErrorPost(WebRequest request,
      MethodArgumentNotValidException e) {

    List<String> errmsglist = new ArrayList<String>();
    for (FieldError error : e.getBindingResult().getFieldErrors()) {
      errmsglist.add(error.getField());
    }

    // detail部分昇順ソートする（エラーの出力順が制御できなかったため。）
    Collections.sort(errmsglist);
    log.error("MethodArgumentNotValidException POSTのバリデーションエラー。リクエスト：" + request, e);
    // Error Msg リスト
    return new ResponseEntity<ErrorResponse>(new ErrorResponse(ErrorCns.ERR_CD_POST_VALIDATION,
        ErrorCns.ERR_MSG_POST_VALIDATION, errmsglist.toString().replaceAll(" ", "")),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * 型違いのパラメータが飛んで来た場合（400 - Bad Reque）
   *
   * @param e 例外.
   * @param request リクエスト.
   * @return レスポンス.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleExceptionInternal(WebRequest request,
      HttpMessageNotReadableException e) {
    log.error("HttpMessageNotReadableException 型違いのパラメータエラー。リクエスト：" + request, e);
    // エラーコードとエラーメッセージ（概略）のみセット
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse(ErrorCns.ERR_CD_NO_READABLE, ErrorCns.ERR_MSG_NO_READBLE, ""),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * ログインエラー(403 - Forbidden)のエラーハンドラー.
   *
   * @param req
   * @return
   */
  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<ErrorResponse> unmatchPassword(WebRequest request,
      AuthenticationException e) {
    log.error("AuthenticationException ログインエラー。リクエスト：" + request, e);
    // エラーコードとエラーメッセージ（概略）のみセット
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse(ErrorCns.ERR_CD_PASSWORD_FAILED, ErrorCns.ERR_MSG_PASSWORD_FAILED, ""),
        HttpStatus.NOT_FOUND);
  }

  /**
   * ページがない時(404 - Not Found)のエラーハンドラー.
   *
   * @param req
   * @return
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> notFound(WebRequest request, NoHandlerFoundException e) {
    log.error("NoHandlerFoundException ページ無エラー。リクエスト：" + request, e);
    // エラーコードとエラーメッセージ（概略）のみセット
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse(ErrorCns.ERR_CD_NO_PAGE, ErrorCns.ERR_MSG_NO_PAGE, ""),
        HttpStatus.NOT_FOUND);
  }

  /**
   * 権限エラー(405 - Method Not Allowed)の場合のエラーハンドラー.
   *
   * @param e 例外.
   * @param request リクエスト.
   * @return レスポンス.
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> notSupported(WebRequest request,
      HttpRequestMethodNotSupportedException e) {
    log.error("HttpRequestMethodNotSupportedException 権限無エラー。リクエスト：" + request, e);
    // エラーコードとエラーメッセージ（概略）のみセット
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse(ErrorCns.ERR_CD_NOT_ALLOWED, ErrorCns.ERR_MSG_NOT_ALLOWED, ""),
        HttpStatus.METHOD_NOT_ALLOWED);
  }
}

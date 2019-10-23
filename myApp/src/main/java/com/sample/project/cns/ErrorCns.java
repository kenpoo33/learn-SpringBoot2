package com.sample.project.cns;
/**
 * エラー定数クラス.
 *
 * @author user
 *
 */
public class ErrorCns {
  /*
   * エラーコード.
   */
  /*** Validation Error(GET). */
  public static final String ERR_CD_UNKNOWN = "E9999";
  /*** Validation Error(GET). */
  public static final String ERR_CD_GET_VALIDATION = "E0001";
  /*** Validation Error(POST). */
  public static final String ERR_CD_POST_VALIDATION = "E0002";
  /*** Request Error(NoReadble). */
  public static final String ERR_CD_NO_READABLE = "E0003";
  /*** No Page Error. */
  public static final String ERR_CD_NO_PAGE = "E0004";
  /*** No Page Error. */
  public static final String ERR_CD_NOT_ALLOWED = "E0005";
  /*** No Data Error. */
  public static final String ERR_CD_NO_DATA = "E0006";
  /*** Password Failed Error. */
  public static final String ERR_CD_PASSWORD_FAILED = "E0007";

  /*
   * エラーメッセージ.
   */
  /*** Validation Error(GET). */
  public static final String ERR_MSG_UNKNOWN = "UnKnown Error";
  /*** Validation Error(GET). */
  public static final String ERR_MSG_GET_VALIDATION = "Validation Error(GET)";
  /*** Validation Error(POST). */
  public static final String ERR_MSG_POST_VALIDATION = "Validation Error(POST)";
  /*** Request Error(NoReadble). */
  public static final String ERR_MSG_NO_READBLE = "NotReadable Request Error";
  /*** No Page Error. */
  public static final String ERR_MSG_NO_PAGE = "Not Found Page Error";
  /*** No Page Error. */
  public static final String ERR_MSG_NOT_ALLOWED = "Method Not Allowed Error";
  /*** No Data Error. */
  public static final String ERR_MSG_NO_DATA = "No Data Error";
  /*** Password Failed Error. */
  public static final String ERR_MSG_PASSWORD_FAILED = "There is a mistake in the Id or password";
}

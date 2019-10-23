package com.sample.project.appcmn;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * ログインマッパーインターフェース.
 *
 * @author user
 *
 */
@Mapper
public interface LoginUserDetailMapper {

  /**
   * ログイン情報001SELECT処理.
   * <p>
   * ログイン時の情報を取得する。
   * </p>
   *
   * @param id ID
   * @return ログイン情報１ドメイン
   */
  LoginUserDetailDomain select001(String id);

  /**
   * ログイン情報002SELECT処理.
   * <p>
   * ログインユーザに紐づくロールリスト情報を取得する。
   * </p>
   *
   * @param id ID
   * @return ロールリスト
   */
  List<String> select002(String id);

  /**
   * ログイン情報003SELECT処理.
   * <p>
   * ログインユーザに紐づく権限リスト情報を取得する。
   * </p>
   *
   * @param id ID
   * @return 権限リスト
   */
  List<String> select003(String id);
}

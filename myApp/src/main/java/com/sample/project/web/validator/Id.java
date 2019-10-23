package com.sample.project.web.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.sample.project.web.validator.Id.IdValidator;

/**
 * IDアノテーションインターフェース.
 * <p>
 * １．数値・アルファベットのみで構成されているか.<br>
 * ２．社員コードバリデータによるチェック.<br>
 * </p>
 *
 * @author user
 *
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@NotNull
@Pattern(regexp = "[0-9a-zA-Z_]*")
@Constraint(validatedBy = {IdValidator.class})
@ReportAsSingleViolation
@Documented
public @interface Id {

  /** エラー時のメッセージ. **/
  String message() default "{validation.error.message}";

  /** 固定定義(validationインターフェース作法) **/
  Class<?>[] groups() default {};

  /** 固定定義(validationインターフェース作法) **/
  Class<? extends Payload>[] payload() default {};

  /** 固定定義(validationインターフェース作法) **/
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @Documented
  @interface List {
    Id[] value();
  }
  /**
   * ID Varidatorクラス.
   *
   * @author user
   *
   */
  public class IdValidator implements ConstraintValidator<Id, String> {

    /**
     * 初期化処理.
     */
    public void initialize(Id constraintAnnotation) {}


    /**
     * バリデーションチェック.
     * <p>
     * 特になし.
     * </p>
     *
     * @return true:チェックOK, false:チェックNG
     */
    public boolean isValid(String value, ConstraintValidatorContext context) {
      return true;
    }
  }
}


package org.liquid.common.annotation;

import java.lang.annotation.*;

/**
 * 声明性注解，标记的值不能为null
 *
 * @author _Chf
 * @since 07/05/2018
 */
@Documented
@Inherited
@Target({ElementType.PARAMETER, ElementType.TYPE_PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
}

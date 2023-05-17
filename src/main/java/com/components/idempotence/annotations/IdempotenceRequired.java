package com.components.idempotence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lzn
 * @date 2023/01/10 16:06
 * @description the custom annotation of Interface idempotence verification
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdempotenceRequired {
}

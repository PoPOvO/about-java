package proxy.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 标记要扫描的类
 *
 * @author xl
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface DeputyClass {
}

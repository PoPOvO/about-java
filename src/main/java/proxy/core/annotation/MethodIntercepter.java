package proxy.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * 该注解标记目标类要被拦截的方法
 *
 * @author xl
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface MethodIntercepter {
}

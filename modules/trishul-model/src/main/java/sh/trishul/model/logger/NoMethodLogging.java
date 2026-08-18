package sh.trishul.model.logger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation to opt a method or class out of automatic method-entry logging from
 * {@link sh.trishul.model.aop.LoggingAspect}. Annotate high-frequency or performance-critical
 * methods where per-call debug logging would be too noisy.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NoMethodLogging {
}

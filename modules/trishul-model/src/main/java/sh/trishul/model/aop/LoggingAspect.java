package sh.trishul.model.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.model.logger.NoMethodLogging;

/**
 * AOP aspect that automatically logs method entry for service, controller, manager, and repository
 * classes in the {@code sh.trishul} package tree.
 *
 * <p>
 * Each log line is emitted at DEBUG level and contains the class name, method name, and truncated
 * argument values so that production logs remain clean while detailed call traces are available in
 * lower environments.
 *
 * <p>
 * Individual methods or entire classes can opt out by annotating them with {@link NoMethodLogging}.
 */
@Aspect
public class LoggingAspect {

  /** Maximum length of each argument's {@code toString()} representation in the log line. */
  static final int MAX_ARG_LENGTH = 200;

  /**
   * Matches public methods inside any class whose simple name ends with {@code Service},
   * {@code Controller}, {@code Manager}, {@code Repository}, or {@code Client} inside the
   * {@code sh.trishul} package tree, as long as neither the class nor the method carry
   * {@link NoMethodLogging}.
   */
  @Pointcut("within(sh.trishul..*)" + " && (within(*..*..*Service+) || within(*..*..*Controller+)"
      + " || within(*..*..*Manager+) || within(*..*..*Repository+)" + " || within(*..*..*Client+))"
      + " && !@within(NoMethodLogging)" + " && !@annotation(NoMethodLogging)")
  public void trishulLayerMethods() {
    // pointcut definition only
  }

  /**
   * Before advice: logs the method entry at DEBUG level using the target class's own SLF4J Logger
   * so that log output is attributed to the correct class.
   */
  @Before("trishulLayerMethods()")
  public void logMethodEntry(JoinPoint joinPoint) {
    Class<?> targetClass = joinPoint.getTarget().getClass();
    Logger log = LoggerFactory.getLogger(targetClass);
    if (!log.isDebugEnabled()) {
      return;
    }
    String methodName = joinPoint.getSignature().getName();
    String args = formatArgs(joinPoint.getArgs());
    log.debug("Entering {}.{}() args=[{}]", targetClass.getSimpleName(), methodName, args);
  }

  /**
   * Converts the raw method argument array to a comma-separated string, truncating each argument's
   * {@code toString()} to {@link #MAX_ARG_LENGTH} characters so that large entities do not flood
   * the log.
   */
  static String formatArgs(Object[] args) {
    if (args == null || args.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < args.length; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      String value = String.valueOf(args[i]);
      if (value.length() > MAX_ARG_LENGTH) {
        sb.append(value, 0, MAX_ARG_LENGTH).append("...");
      } else {
        sb.append(value);
      }
    }
    return sb.toString();
  }
}

package io.trishul.model.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper around SLF4J's {@link Logger} to provide contextual logging and standardizing log
 * output.
 */
public class Slf4jLoggerWrapper implements Logger {
  private final Logger log;

  public Slf4jLoggerWrapper(Class<?> clazz) {
    this.log = LoggerFactory.getLogger(clazz);
  }

  public Slf4jLoggerWrapper(Logger log) {
    this.log = log;
  }

  @Override
  public boolean isTraceEnabled(org.slf4j.Marker arg0) {
    return log.isTraceEnabled(arg0);
  }

  @Override
  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  @Override
  public boolean isDebugEnabled(org.slf4j.Marker arg0) {
    return log.isDebugEnabled(arg0);
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  @Override
  public boolean isInfoEnabled(org.slf4j.Marker arg0) {
    return log.isInfoEnabled(arg0);
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  @Override
  public boolean isWarnEnabled() {
    return log.isWarnEnabled();
  }

  @Override
  public boolean isWarnEnabled(org.slf4j.Marker arg0) {
    return log.isWarnEnabled(arg0);
  }

  @Override
  public boolean isErrorEnabled(org.slf4j.Marker arg0) {
    return log.isErrorEnabled(arg0);
  }

  @Override
  public boolean isErrorEnabled() {
    return log.isErrorEnabled();
  }

  @Override
  public java.lang.String getName() {
    return log.getName();
  }

  @Override
  public void info(java.lang.String arg0, java.lang.Object arg1, java.lang.Object arg2) {
    log.info(arg0, arg1, arg2);
  }

  @Override
  public void info(java.lang.String arg0, java.lang.Object arg1) {
    log.info(arg0, arg1);
  }

  @Override
  public void info(java.lang.String arg0) {
    log.info(arg0);
  }

  @Override
  public void info(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Throwable arg2) {
    log.info(arg0, arg1, arg2);
  }

  @Override
  public void info(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2) {
    log.info(arg0, arg1, arg2);
  }

  @Override
  public void info(org.slf4j.Marker arg0, java.lang.String arg1) {
    log.info(arg0, arg1);
  }

  @Override
  public void info(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2,
      java.lang.Object arg3) {
    log.info(arg0, arg1, arg2, arg3);
  }

  @Override
  public void info(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object... arg2) {
    log.info(arg0, arg1, arg2);
  }

  @Override
  public void info(java.lang.String arg0, java.lang.Throwable arg1) {
    log.info(arg0, arg1);
  }

  @Override
  public void info(java.lang.String arg0, java.lang.Object... arg1) {
    log.info(arg0, arg1);
  }

  @Override
  public void trace(java.lang.String arg0) {
    log.trace(arg0);
  }

  @Override
  public void trace(java.lang.String arg0, java.lang.Throwable arg1) {
    log.trace(arg0, arg1);
  }

  @Override
  public void trace(org.slf4j.Marker arg0, java.lang.String arg1) {
    log.trace(arg0, arg1);
  }

  @Override
  public void trace(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2) {
    log.trace(arg0, arg1, arg2);
  }

  @Override
  public void trace(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Throwable arg2) {
    log.trace(arg0, arg1, arg2);
  }

  @Override
  public void trace(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object... arg2) {
    log.trace(arg0, arg1, arg2);
  }

  @Override
  public void trace(java.lang.String arg0, java.lang.Object arg1, java.lang.Object arg2) {
    log.trace(arg0, arg1, arg2);
  }

  @Override
  public void trace(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2,
      java.lang.Object arg3) {
    log.trace(arg0, arg1, arg2, arg3);
  }

  @Override
  public void trace(java.lang.String arg0, java.lang.Object arg1) {
    log.trace(arg0, arg1);
  }

  @Override
  public void trace(java.lang.String arg0, java.lang.Object... arg1) {
    log.trace(arg0, arg1);
  }

  @Override
  public void error(java.lang.String arg0) {
    log.error(arg0);
  }

  @Override
  public void error(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Throwable arg2) {
    log.error(arg0, arg1, arg2);
  }

  @Override
  public void error(org.slf4j.Marker arg0, java.lang.String arg1) {
    log.error(arg0, arg1);
  }

  @Override
  public void error(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2) {
    log.error(arg0, arg1, arg2);
  }

  @Override
  public void error(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2,
      java.lang.Object arg3) {
    log.error(arg0, arg1, arg2, arg3);
  }

  @Override
  public void error(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object... arg2) {
    log.error(arg0, arg1, arg2);
  }

  @Override
  public void error(java.lang.String arg0, java.lang.Object arg1) {
    log.error(arg0, arg1);
  }

  @Override
  public void error(java.lang.String arg0, java.lang.Object arg1, java.lang.Object arg2) {
    log.error(arg0, arg1, arg2);
  }

  @Override
  public void error(java.lang.String arg0, java.lang.Object... arg1) {
    log.error(arg0, arg1);
  }

  @Override
  public void error(java.lang.String arg0, java.lang.Throwable arg1) {
    log.error(arg0, arg1);
  }

  @Override
  public void warn(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2,
      java.lang.Object arg3) {
    log.warn(arg0, arg1, arg2, arg3);
  }

  @Override
  public void warn(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2) {
    log.warn(arg0, arg1, arg2);
  }

  @Override
  public void warn(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object... arg2) {
    log.warn(arg0, arg1, arg2);
  }

  @Override
  public void warn(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Throwable arg2) {
    log.warn(arg0, arg1, arg2);
  }

  @Override
  public void warn(java.lang.String arg0) {
    log.warn(arg0);
  }

  @Override
  public void warn(java.lang.String arg0, java.lang.Object arg1) {
    log.warn(arg0, arg1);
  }

  @Override
  public void warn(java.lang.String arg0, java.lang.Object... arg1) {
    log.warn(arg0, arg1);
  }

  @Override
  public void warn(org.slf4j.Marker arg0, java.lang.String arg1) {
    log.warn(arg0, arg1);
  }

  @Override
  public void warn(java.lang.String arg0, java.lang.Throwable arg1) {
    log.warn(arg0, arg1);
  }

  @Override
  public void warn(java.lang.String arg0, java.lang.Object arg1, java.lang.Object arg2) {
    log.warn(arg0, arg1, arg2);
  }

  @Override
  public void debug(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object... arg2) {
    log.debug(arg0, arg1, arg2);
  }

  @Override
  public void debug(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Throwable arg2) {
    log.debug(arg0, arg1, arg2);
  }

  @Override
  public void debug(org.slf4j.Marker arg0, java.lang.String arg1) {
    log.debug(arg0, arg1);
  }

  @Override
  public void debug(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2,
      java.lang.Object arg3) {
    log.debug(arg0, arg1, arg2, arg3);
  }

  @Override
  public void debug(org.slf4j.Marker arg0, java.lang.String arg1, java.lang.Object arg2) {
    log.debug(arg0, arg1, arg2);
  }

  @Override
  public void debug(java.lang.String arg0) {
    log.debug(arg0);
  }

  @Override
  public void debug(java.lang.String arg0, java.lang.Object arg1) {
    log.debug(arg0, arg1);
  }

  @Override
  public void debug(java.lang.String arg0, java.lang.Object arg1, java.lang.Object arg2) {
    log.debug(arg0, arg1, arg2);
  }

  @Override
  public void debug(java.lang.String arg0, java.lang.Object... arg1) {
    log.debug(arg0, arg1);
  }

  @Override
  public void debug(java.lang.String arg0, java.lang.Throwable arg1) {
    log.debug(arg0, arg1);
  }

}

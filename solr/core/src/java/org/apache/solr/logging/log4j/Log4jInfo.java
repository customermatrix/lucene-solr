package org.apache.solr.logging.log4j;

import org.apache.log4j.Logger;
import org.apache.solr.logging.LoggerInfo;

public class Log4jInfo extends LoggerInfo {
  private final Logger logger;

  public Log4jInfo(String name, Logger logger) {
    super(name);
    this.logger = logger;
  }

  @Override
  public String getLevel() {
    if (!isSet()) {
      return null;
    }
    return logger.getLevel().toString();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isSet() {
    return (logger != null && logger.getLevel() != null);
  }
}

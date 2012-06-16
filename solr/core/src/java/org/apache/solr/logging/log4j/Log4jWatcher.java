package org.apache.solr.logging.log4j;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.common.base.Throwables;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.logging.CircularList;
import org.apache.solr.logging.ListenerConfig;
import org.apache.solr.logging.LogWatcher;
import org.apache.solr.logging.LoggerInfo;

import java.util.*;

import static org.apache.log4j.Level.*;
import static org.apache.log4j.LogManager.getCurrentLoggers;
import static org.apache.log4j.LogManager.getRootLogger;
import static org.apache.log4j.Logger.getLogger;
import static org.apache.solr.logging.LoggerInfo.ROOT_NAME;

public class Log4jWatcher extends LogWatcher<LoggingEvent> {
  public static final String EMPTY_CATEGORY = "";
  private final String name;
  private final EventAppender appender;

  public Log4jWatcher(String name) {
    this.name = "Log4j (" + name + ")";
    this.appender = new EventAppender(this);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<String> getAllLevels() {
    return Arrays.asList(
        ALL.toString(),
        TRACE.toString(),
        DEBUG.toString(),
        INFO.toString(),
        WARN.toString(),
        ERROR.toString(),
        FATAL.toString(),
        OFF.toString()
    );
  }

  @Override
  public void setLogLevel(String category, String levelStr) {
    if (ROOT_NAME.equals(category)) {
      category = EMPTY_CATEGORY;
    }
    Level level = null;
    if (levelStr != null && !levelStr.equals("unset") && !levelStr.equals("null")) {
      level = toLevel(levelStr);
    }
    getLogger(category).setLevel(level);
  }

  @Override
  public Collection<LoggerInfo> getAllLoggers() {
    Map<String, LoggerInfo> map = new HashMap<String, LoggerInfo>();
    Enumeration<?> loggers = getCurrentLoggers();
    while (loggers.hasMoreElements()) {
      Logger logger = (Logger) loggers.nextElement();
      String name = logger.getName();
      if (logger != getRootLogger()) {
        map.put(name, new Log4jInfo(name, logger));
        while (true) {
          int dot = name.lastIndexOf(".");
          if (dot < 0) {
            break;
          }
          name = name.substring(0, dot);
          if (!map.containsKey(name)) {
            map.put(name, new Log4jInfo(name, null));
          }
        }
      }
    }
    map.put(ROOT_NAME, new Log4jInfo(ROOT_NAME, getRootLogger()));
    return map.values();
  }

  @Override
  public void setThreshold(String level) {
    checkAppender();
    appender.setThreshold(toLevel(level));
  }

  @Override
  public String getThreshold() {
    checkAppender();
    return appender.getThreshold().toString();
  }

  @Override
  public void registerListener(ListenerConfig cfg, CoreContainer container) {
    checkHistory();
    history = new CircularList<LoggingEvent>(cfg.size);
    appender.setThreshold(defaultThreshold(cfg));
    getRootLogger().addAppender(appender);
  }

  @Override
  public long getTimestamp(LoggingEvent event) {
    return event.timeStamp;
  }

  @Override
  public SolrDocument toSolrDocument(LoggingEvent event) {
    SolrDocument doc = new SolrDocument();
    doc.setField("time", new Date(event.getTimeStamp()));
    doc.setField("level", event.getLevel().toString());
    doc.setField("logger", event.getLogger().getName());
    doc.setField("message", event.getMessage().toString());
    ThrowableInformation t = event.getThrowableInformation();
    String trace = null;
    if (t != null) {
      trace = Throwables.getStackTraceAsString(t.getThrowable());
    }
    doc.setField("trace", trace);
    return doc;
  }

  private void checkAppender() {
    if (appender == null) {
      throw new IllegalStateException("Must have an appender");
    }
  }

  private void checkHistory() {
    if (history != null) {
      throw new IllegalStateException("History already registered");
    }
  }

  private Priority defaultThreshold(ListenerConfig cfg) {
    Priority priority;
    if (cfg.threshold != null) {
      priority = toLevel(cfg.threshold);
    } else {
      priority = WARN;
    }
    return priority;
  }
}
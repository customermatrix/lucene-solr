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

import org.apache.log4j.Logger;
import org.apache.solr.logging.ListenerConfig;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class Log4jWatcherTest {
  private final Logger logger = Logger.getLogger("test-logger");

  @Test
  public void should_detect_at_least_new_warn_messages() {
    long beforeLogging = System.currentTimeMillis();
    Log4jWatcher watcher = new Log4jWatcher("test");
    watcher.registerListener(new ListenerConfig(), null);
    int count = 2;
    triggerWarnMessages(count);
    triggerInfoMessages(1);
    long afterLogging = System.currentTimeMillis();
    System.out.println(watcher.getHistory(beforeLogging-1, null));
    verify((int) watcher.getHistory(beforeLogging-1, null).getNumFound(), count);
    verify((int) watcher.getHistory(afterLogging, null).getNumFound(), 0);
  }

  // can't change programmatically level in log4j
//  @Test
//  public void should_detect_also_new_info_messages() {
//    long beforeLogging = System.currentTimeMillis();
//    Log4jWatcher watcher = new Log4jWatcher("test");
//    watcher.setLogLevel("test-logger", "INFO");
//    watcher.registerListener(new ListenerConfig(), null);
//    int count = 4;
//    triggerWarnMessages(count/2);
//    triggerInfoMessages(count/2);
//    verify((int) watcher.getHistory(beforeLogging-1, null).getNumFound(), count);
//  }

  private void verify(int numFound, int historyCount) {
    assertThat(numFound, IsEqual.equalTo(historyCount));
  }

  private void triggerInfoMessages(int count) {
    for (int i = 0; i < count; i++) {
      logger.info("info msg[" + i + "]");
    }
  }

  private void triggerWarnMessages(int count) {
    for (int i = 0; i < count; i++) {
      logger.warn("warn msg[" + i + "]");
    }
  }
}

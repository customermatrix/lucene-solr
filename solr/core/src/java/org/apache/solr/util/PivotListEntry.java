package org.apache.solr.util;

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

public enum PivotListEntry {
  
  FIELD("field", 0),
  
  VALUE("value", 1),
  
  COUNT("count", 2),
  
  STATISTICS("statistics", 3),
  
  PIVOT("pivot", 3),
  
  MAPPEDPIVOT("mappedpivot",3),

  WEIGHT("weight", 3);
  
  private final String name;
  
  private final int index;
  
  private PivotListEntry(String name, int index) {
    this.name = name;
    this.index = index;
  }
  
  public String getName() {
    return name;
  }
  
  public int getIndex() {
    return index;
  }

}

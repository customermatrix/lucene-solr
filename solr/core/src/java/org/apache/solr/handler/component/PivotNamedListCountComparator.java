package org.apache.solr.handler.component;

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

import java.util.Comparator;

import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.util.NamedList;

public class PivotNamedListCountComparator implements Comparator<NamedList<Object>> {
  
  @Override
  public int compare(NamedList<Object> o1, NamedList<Object> o2) {
    boolean oneOfTheseIsNull = ((o1.get("value") == null) != (o2.get("value") == null));    
    if(oneOfTheseIsNull) {
      return handleSortWhenOneValueIsNull(o1.get("value"),o2.get("value"));
    }    
    
    Object firstCountObj = o1.get(FacetParams.FACET_SORT_COUNT);
    Object secondCountObj = o2.get(FacetParams.FACET_SORT_COUNT);
    if (firstCountObj instanceof Integer && secondCountObj instanceof Integer) {      
      int comparison = ((Integer) secondCountObj).compareTo((Integer) firstCountObj);
      if (comparison != 0) {      
        return comparison;
      }
      else { //if counts are equal, sort by value
        if(o1.get("value") == null && o2.get("value") == null) //if both are null and counts are equal they are the same
          return comparison;
        
        String firstValue = o1.get("value").toString(); 
        String secondValue = o2.get("value").toString();
        return firstValue.compareTo(secondValue);
      }           
    } else {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,("PivotNamedListCountComparator encountered comparison where one or both counts were not integers."));
    }
  }
  
  private int handleSortWhenOneValueIsNull(Object firstThing, Object secondThing) {
    if(firstThing == null) {
      return 1;
    }
    else
      return -1;
  }

}

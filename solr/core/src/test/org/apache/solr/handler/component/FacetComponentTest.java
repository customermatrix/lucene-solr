package org.apache.solr.handler.component;

import static junit.framework.Assert.assertEquals;
import static org.apache.solr.common.params.FacetParams.FACET_FIELD;
import static org.apache.solr.common.params.FacetParams.FACET_LIMIT;
import static org.apache.solr.common.params.FacetParams.FACET_MINCOUNT;
import static org.apache.solr.common.params.FacetParams.FACET_MISSING;
import static org.junit.Assert.*;
import junit.framework.Assert;

import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.handler.admin.CoreAdminHandler;
import org.apache.solr.handler.component.FacetComponent.FieldFacet;
import org.junit.BeforeClass;
import org.junit.Test;

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

public class FacetComponentTest extends SolrTestCaseJ4 {
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    initCore("solrconfig.xml", "schema.xml");
  }
  
  @Test
  public void honorsLocalParams() throws Exception {
    String fStr = "{!key=myFacet f.field.facet.mincount=5 f.field.facet.missing=true f.field.facet.prefix=$prefix}field";
    
    ResponseBuilder rb = new ResponseBuilder(req(FACET_FIELD, fStr,//
        FACET_LIMIT, "500",//
        FACET_MINCOUNT, "0",//
        FACET_MISSING, "false",//
        "prefix", "somePrefix"),//
        null, null);
    
    FieldFacet facet = new FieldFacet(rb, fStr);
    
    assertEquals(facet.field, "field");
    assertEquals(facet.minCount, 5);
    assertEquals(facet.missing, true);
    assertEquals(facet.prefix, "somePrefix");
    assertEquals(facet.limit, 500);
  }
}

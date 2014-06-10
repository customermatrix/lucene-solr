package org.apache.solr.analysis;

import org.apache.lucene.analysis.TokenStream;

/**
 * Since 6.4.0
 */
public interface FieldNameAware {

  /** Transform the specified input TokenStream */
  public TokenStream create(TokenStream input, String fieldName);
}

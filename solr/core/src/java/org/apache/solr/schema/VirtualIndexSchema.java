package org.apache.solr.schema;

import java.util.HashMap;
import java.util.Map;

public class VirtualIndexSchema extends IndexSchema {
  public void putAllFields(Map<String, SchemaField> fields) {
    this.fields.putAll(fields);
  }

  public void putAllFieldTypes(Map<String, FieldType> fieldTypes) {
    this.fieldTypes.putAll(fieldTypes);
  }

  public void setUniqueKeyField(SchemaField uniqueKeyField) {
    this.uniqueKeyField = uniqueKeyField;
  }

  public void setQueryParserDefaultOperator(String queryParserDefaultOperator) {
    this.queryParserDefaultOperator = queryParserDefaultOperator;
  }

  public void setDefaultSearchFieldName(String defaultSearchFieldName) {
    this.defaultSearchFieldName = defaultSearchFieldName;
  }
}

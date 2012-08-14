package org.apache.solr.schema;

import java.util.HashMap;
import java.util.Map;

public class VirtualIndexSchema extends IndexSchema {
  public void putAllFields(Map<String, SchemaField> fields) {
    this.fields = fields;
  }

  public void putAllFieldTypes(Map<String, FieldType> fieldTypes) {
    this.fieldTypes = fieldTypes;
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

  @Override
  public SchemaField getFieldOrNull(String fieldName) {
    return fields.get(fieldName);
  }

  @Override
  protected FieldType dynFieldType(String fieldName) {
    return null;
  }

  @Override
  public Map<String,SchemaField> getFields() {
    if (fields == null) {
      return new HashMap<String, SchemaField>();
    }
    return fields;
  }
}

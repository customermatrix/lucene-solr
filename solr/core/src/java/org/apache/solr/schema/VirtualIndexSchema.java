package org.apache.solr.schema;

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

  @Override
  public SchemaField getFieldOrNull(String fieldName) {
    return fields.get(fieldName);
  }
}

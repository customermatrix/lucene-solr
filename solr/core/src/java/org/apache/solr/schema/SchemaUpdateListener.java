package org.apache.solr.schema;

import org.apache.solr.core.CoreDescriptor;

public interface SchemaUpdateListener {
  void onUpdate(CoreDescriptor coreDescriptor, IndexSchema schema);
}

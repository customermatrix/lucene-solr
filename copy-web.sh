#!/bin/sh
# Copy web stuff to polyspot workspace directory
DESTINATION=$1/search/solr/src/main/webapp
SOURCE=solr/webapp/web
EXCLUDE=WEB-INF

find $DESTINATION/* | grep -v $EXCLUDE | xargs rm -rf
find $SOURCE/* -xdev -prune | grep -v $EXCLUDE | xargs cp -r -t $DESTINATION

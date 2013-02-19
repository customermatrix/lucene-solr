#!/bin/sh
DESTINATION=../search/solr/src/main/webapp
SOURCE=solr/webapp/web
EXCLUDE=WEB-INF

find $DESTINATION/* | grep -v $EXCLUDE | xargs rm -rf
find $SOURCE/* -xdev -prune | grep -v $EXCLUDE | xargs cp -r -t $DESTINATION

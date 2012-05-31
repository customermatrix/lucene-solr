DESTINATION=../search/solr/src/main/webapp
SOURCE=solr/webapp/web
EXCLUDE=WEB-INF

find $DESTINATION/* | grep -v $EXCLUDE | xargs rm -rf
find $SOURCE/* -type d | grep -v $EXCLUDE | xargs cp -r -t $DESTINATION
cp $SOURCE/admin.html $DESTINATION

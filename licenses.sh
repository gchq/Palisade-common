#!/usr/bin/env bash

FILE=target/generated-resources/licenses.xml
chmod 666 "$FILE"

if [ -f $FILE ]; then

  dependencyCount=$(xmllint --xpath 'count(//licenseSummary/dependencies/dependency)' "$FILE")
  declare -a dependency_array=()

  for (( i = 1; i <= dependencyCount; i++ )); do
    val=""
    val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$i']/licenses/license/url' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')"_"
    val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$i']/groupId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
    val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$i']/artifactId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
    val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$i']/version' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
    dependency_array[$i]="$val"
  done

  n="${#dependency_array[@]}"
  for (( i = 1; i <= n; i++ )); do
    echo "${dependency_array[$i]}"
  done

else
    echo "Cannot find licenses.xml file - have you run mvn install?"
fi
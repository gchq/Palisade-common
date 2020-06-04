#!/usr/bin/env bash

FILE=target/generated-resources/licenses.xml
chmod 666 "$FILE"

if [ -f $FILE ]; then

  dependencyCount=$(xmllint --xpath 'count(//licenseSummary/dependencies/dependency)' "$FILE")
  declare -a dependency_array=()
  int=0
  start=("New" "The")
  apache="Apache 2 Licence"

  for (( a = 1; a <= dependencyCount; a++ )); do
    licenseCount=$(xmllint --xpath 'count(//licenseSummary/dependencies/dependency['$a']/licenses/license)' "$FILE")
    for (( b = 0; b < licenseCount; b++ )); do
      licensePath="/"
      if [ "$licenseCount" == 1 ]; then
        licensePath+="/licenseSummary/dependencies/dependency['$a']/licenses/license/"
      else
        licensePath+="/licenseSummary/dependencies/dependency['$a']/licenses/license['$b']/"
      fi
      printf "License Path: %s\n" "$licensePath"
      entry=""
      newName=$(xmllint --xpath "$licensePath""name" $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
      for (( i = 0; i < "${#start[@]}"; i++ )); do
        if [[ "${start[$i]}" == $(echo "$newName" | awk -F" " '{print $1}') ]]; then
          newName=$(echo "$newName" | cut -f2- -d" ")
        fi
      done
      if [[ "$licenseName" == *Apache* ]]; then
        entry+="$apache""_"
      else
        entry+="$licenseName""_"
      fi
      newUrl=$(xmllint --xpath --xpath "$licensePath""url" $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
      if [[ "$newUrl" == http* ]]; then
        entry+=$(echo "$newUrl" | awk -F"//" '{print $2}')"_"
      fi
      entry+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/groupId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
      entry+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/licenseArtifactId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
      entry+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/version' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
      if [[ $(echo "$entry" | awk -F"_" '{print $2}') == */* ]]; then
        dependency_array[$int]=$(echo "$entry" | tr ' ' '-')
        int=$((int+1))
      fi
      printf "Entry: %s\n" "$entry"
    done
  done
  sorted_array=($(echo "${dependency_array[@]}" | tr ' ' '\n' | sort))
  n="${#sorted_array[@]}"
  currentLicense=""

  for (( i = 0; i < n; i++ )); do
      licenseName=$(echo "${sorted_array[$i]}" | cut -f1 -d_ | tr '-' ' ')
      licenseUrl=$(echo "${sorted_array[$i]}" | cut -f2 -d_)
      licenseArtifact=$(echo "${sorted_array[$i]}" | cut -f3 -d_)
      if [ $i == 0 ]; then
        currentLicense="$licenseName"
        printf '%s (%s)\n\n' "$licenseName" "$licenseUrl" > NOTICES
        printf ' - %s\n' "$licenseArtifact" >> NOTICES
      else
        if [ "$licenseName" == "$currentLicense" ]; then
          printf ' - %s\n' "$licenseArtifact" >> NOTICES
        else
          currentLicense="$licenseName"
          printf '\n\n%s (%s)\n\n' "$licenseName" "$licenseUrl" >> NOTICES
          printf ' - %s\n' "$licenseArtifact" >> NOTICES
        fi
      fi
  done

else
    echo "Cannot find licenses.xml file - have you run mvn install?"
fi
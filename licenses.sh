#!/usr/bin/env bash

FILE=target/generated-resources/licenses.xml
chmod 666 "$FILE"

if [ -f $FILE ]; then

  dependencyCount=$(xmllint --xpath 'count(//licenseSummary/dependencies/dependency)' "$FILE")
  declare -a dependency_array=()
  int=0

  for (( a = 1; a <= dependencyCount; a++ )); do
    licenseCount=$(xmllint --xpath 'count(//licenseSummary/dependencies/dependency['$a']/licenses/license)' "$FILE")
    if [ "$licenseCount" == 1 ]; then
      val=""
      val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/licenses/license/url' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')"_"
      val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/groupId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
      val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/artifactId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
      val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/version' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
      if [[ "${val}" == http* ]]; then
          dependency_array[$int]="$val"
          int=$((int+1))
      fi
    else
      for (( b = 1; b <= licenseCount; b++ )); do
        val=""
        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/licenses/license['$b']/url' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')"_"
        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/groupId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/artifactId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/version' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
        if [[ "${val}" == http* ]]; then
          dependency_array[$int]="$val"
          int=$((int+1))
        fi
      done
    fi
  done

  sorted_array=($(echo "${dependency_array[@]}" | tr ' ' '\n' | sort))
  n="${#sorted_array[@]}"
  curLicense=""

  for (( i = 0; i < n; i++ )); do
      var1=$(echo "${sorted_array[$i]}" | cut -f1 -d_)
      var2=$(echo "${sorted_array[$i]}" | cut -f2 -d_)
      if [ $i == 0 ]; then
        curLicense="$var1"
        printf '%s\n\n' "$var1" >> output.txt
        printf '%s\n' "$var2" >> output.txt
      else
        if [ "$var1" == "$curLicense" ]; then
          printf '%s\n' "$var2" >> output.txt
        else
          curLicense="$var1"
          printf '\n\n%s\n\n' "$var1" >> output.txt
          printf '%s\n' "$var2" >> output.txt
        fi
      fi
  done

else
    echo "Cannot find licenses.xml file - have you run mvn install?"
fi
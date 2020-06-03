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
    if [ "$licenseCount" == 1 ]; then
      val=""
      val2=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/licenses/license/name' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
      for (( i = 0; i < "${#start[@]}"; i++ )); do
        if [[ "${start[$i]}" == $(echo "$val2" | awk -F" " '{print $1}') ]]; then
          val2=$(echo "$val2" | cut -f2- -d" ")
        fi
      done
      if [[ "$val2" == *Apache* ]]; then
        val+="$apache""_"
      else
        val+="$val2""_"
      fi

      val3=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/licenses/license/url' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
      if [[ "$val3" == http* ]]; then
        val+=$(echo "$val3" | awk -F"//" '{print $2}')"_"
      fi

      val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/groupId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
      val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/artifactId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
      val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/version' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')

      if [[ $(echo "$val" | awk -F"_" '{print $2}') == *:* ]]; then
        val=""
      else
        dependency_array[$int]=$(echo "$val" | tr ' ' '-')
        int=$((int+1))
      fi
    else
      for (( b = 1; b <= licenseCount; b++ )); do
        val=""
        val2=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/licenses/license['$b']/name' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
        for (( i = 0; i < "${#start[@]}"; i++ )); do
          if [[ "${start[$i]}" == $(echo "$val2" | awk -F" " '{print $1}') ]]; then
            val2=$(echo "$val2" | cut -f2- -d" ")"_"
          fi
        done
        if [[ "$val2" == *Apache* ]]; then
          val+="$apache""_"
        else
          val+="$val2"
        fi

        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/licenses/license['$b']/url' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')"_"
        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/groupId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/artifactId' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')":"
        val+=$(xmllint --xpath '//licenseSummary/dependencies/dependency['$a']/version' $FILE | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
        if [[ $(echo "${val}" | awk -F"_" '{print $2}') == http* ]]; then
          dependency_array[$int]=$(echo "$val" | awk -F"//" '{print $2}')
          int=$((int+1))
        fi
      done
    fi
  done
  sorted_array=($(echo "${dependency_array[@]}" | tr ' ' '\n' | sort))
  n="${#sorted_array[@]}"
  curLicense=""

  for (( i = 0; i < n; i++ )); do
      var1=$(echo "${sorted_array[$i]}" | cut -f1 -d_ | tr '-' ' ')
      var2=$(echo "${sorted_array[$i]}" | cut -f2 -d_)
      var3=$(echo "${sorted_array[$i]}" | cut -f3 -d_)
      if [[ "${var2}" == *Apache* ]]; then
          var2="Apache 2 Licence"
      fi
      if [ $i == 0 ]; then
        curLicense="$var1"
        printf '%s (%s)\n\n' "$var1" "$var2" > NOTICES.txt
        printf ' - %s\n' "$var3" >> NOTICES.txt
      else
        if [ "$var1" == "$curLicense" ]; then
          printf ' - %s\n' "$var3" >> NOTICES.txt
        else
          curLicense="$var1"
          printf '\n\n%s (%s)\n\n' "$var1" "$var2" >> NOTICES.txt
          printf ' - %s\n' "$var3" >> NOTICES.txt
        fi
      fi
  done

else
    echo "Cannot find licenses.xml file - have you run mvn install?"
fi
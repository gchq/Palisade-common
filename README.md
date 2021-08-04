<!---
Copyright 2018-2021 Crown Copyright

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--->

# <img src="logos/logo.svg" width="180">

## A Tool for Complex and Scalable Data Access Policy Enforcement

# Palisade Common

## Status
<span style="color:red">Palisade is no longer under active development</span>

Windows is not an explicitly supported environment, although where possible Palisade has been made compatible.  
For Windows developer environments, we recommend setting up [WSL](https://docs.microsoft.com/en-us/windows/wsl/).

For an overview of Palisade, start with the Palisade introduction and the accompanying guides: QuickStart Guide; and Developer Guide which are found in the [Palisade README](https://github.com/gchq/Palisade/README.md).

### Prerequisites
1. [Git](https://git-scm.com/)
2. [Maven](https://maven.apache.org/)

## Getting started
For an overview of Palisade, start with the root documentation [Palisade README](https://github.com/gchq/Palisade#readme).

To get started, clone the Palisade Common repo: 

```bash
git clone https://github.com/gchq/Palisade-common.git
cd Palisade-common
```

<details><summary>You will need to configure your ~/.m2/settings.xml:</summary>
<p>

```bash
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                  http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <!-- the path to the local repository - defaults to ~/.m2/repository
  -->
  <!-- <localRepository>/path/to/local/repo</localRepository>
  -->
    <mirrors>
​
    <mirror> <!--Send all requests to the public group -->
      <id>nexus</id>
      <url>*nexusurl*/maven-group/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
​    </mirrors>
  <activeProfiles>
    <!--make the profile active all the time -->
    <activeProfile>nexus</activeProfile>
  </activeProfiles>
  <profiles>
    <profile> 
      <id>default</id> 
      <activation> 
        <activeByDefault>true</activeByDefault> 
      </activation> 
      <properties> 
        <release.url>*nexusurl*/maven-releases/</release.url>
        <snapshot.url>*nexusurl*/maven-snapshots/</snapshot.url> 
      </properties> 
    </profile> 
    <profile>
      <id>nexus</id>
      <!--Override the repository (and pluginRepository) "central" from the Maven Super POM
          to activate snapshots for both! -->
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo.maven.apache.org/maven2/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
​
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>central</id>
          <url>https://repo.maven.apache.org/maven2/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
​
  <pluginGroups>
    <pluginGroup>org.sonatype.plugins</pluginGroup>
  </pluginGroups>
​
  <servers>
​
    <server>
      <id>nexus</id>
      <username>*username*</username>
      <password>*password*</password>
    </server>
  </servers>
</settings>
```
</p>
</details>

You are then ready to build with Maven:
```bash
mvn clean install
```

## License
Palisade-Common is licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0) and is covered by [Crown Copyright](https://www.nationalarchives.gov.uk/information-management/re-using-public-sector-information/copyright-and-re-use/crown-copyright/).

## Contributing
We welcome contributions to the project. Detailed information on our ways of working can be found [here](https://gchq.github.io/Palisade/doc/other/ways_of_working.html).

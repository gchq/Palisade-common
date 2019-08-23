
<!---
Copyright 2018 Crown Copyright

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

### Scalable Data Access Policy Management and Enforcement

## Status
<span style="color:red">
This is not the main Palisade Repository, this is the Repository for Palisade Common. For more information, please visit Palisade
</span>

## Documentation

The documentation for the latest release can be found [here](https://gchq.github.io/Palisade).


### Prerequisites
1. [Git](https://git-scm.com/)
1. [Maven](https://maven.apache.org/)
1. [Docker](https://www.docker.com/)

The examples may have additional prerequisites

<span style="color:red">
We do not currently support Windows as a build environment, If you are running on Windows then you will need this: Microsoft Visual C++ 2010 SP1 Redistributable Package
</span>


## Getting started

To get started, clone the Palisade Common repo: 

```bash
git clone https://github.com/gchq/Palisade-common.git
```


## License

Palisade-Common is licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0) and is covered by [Crown Copyright](https://www.nationalarchives.gov.uk/information-management/re-using-public-sector-information/copyright-and-re-use/crown-copyright/).


## Contributing
We welcome contributions to the project. Detailed information on our ways of working can be found [here](https://gchq.github.io/Palisade/doc/other/ways_of_working.html).


## FAQ

What versions of Java are supported? We are currently using Java 11.

##Local Jenkins via docker 
To spin up Jenkins, have the latest version of docker installed and run this command:
```bash
docker run --rm -u root -p 7070:7070 -v jenkins-data:/var/jenkins_home -v $(which docker):/usr/bin/docker -v /var/run/docker.sock:/var/run/docker.sock -v ~/Documents/Palisade-common:~/Documents/Palisade-common jenkins/jenkins:lts
```
This will then start up a jenkins instance which you can connect to via: http://127.0.0.1:7070. Log in with the secret code in the output of the jenkins file and set up the repo as a Jenkins pipeline. 
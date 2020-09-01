/*
 * Copyright 2020 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//node-affinity
//nodes 1..3 are reserved for Jenkins slave pods.
//node 0 is used for the Jenkins master

timestamps {

    podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      affinity:
        nodeAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
          - weight: 1
            preference:
              matchExpressions:
              - key: palisade-node-name
                operator: In
                values:
                - node1
                - node2
                - node3
      containers:
      - name: jnlp
        image: jenkins/jnlp-slave
        imagePullPolicy: Always
        args:
        - $(JENKINS_SECRET)
        - $(JENKINS_NAME)
        resources:
          requests:
            ephemeral-storage: "4Gi"
          limits:
            ephemeral-storage: "8Gi"

      - name: docker-cmds
        image: 779921734503.dkr.ecr.eu-west-1.amazonaws.com/jnlp-did:200608
        imagePullPolicy: IfNotPresent
        command:
        - sleep
        args:
        - 99d
        env:
          - name: DOCKER_HOST
            value: tcp://localhost:2375
        resources:
          requests:
            ephemeral-storage: "4Gi"
          limits:
            ephemeral-storage: "8Gi"

    ''') {

        node(POD_LABEL) {
            def GIT_BRANCH_NAME
            def COMMON_REVISION

            stage('Bootstrap') {
                container('docker-cmds') {
                    if (env.CHANGE_BRANCH) {
                        GIT_BRANCH_NAME = env.CHANGE_BRANCH
                    } else {
                        GIT_BRANCH_NAME = env.BRANCH_NAME
                    }
                    COMMON_REVISION = "BUILD"
                    if ("${env.BRANCH_NAME}" == "develop") {
                        COMMON_REVISION = "SNAPSHOT"
                    }
                    if ("${env.BRANCH_NAME}" == "main") {
                        COMMON_REVISION = "RELEASE"
                    }
                    echo sh(script: 'env | sort', returnStdout: true)
                }
            }
            stage('Install, Unit Tests, Checkstyle') {
                dir('Palisade-common') {
                    git branch: GIT_BRANCH_NAME, url: 'https://github.com/gchq/Palisade-common.git'
                    container('docker-cmds') {
                        configFileProvider([configFile(fileId: "${env.CONFIG_FILE}", variable: 'MAVEN_SETTINGS')]) {
                            echo 'test1: $COMMON_REVISION'
                            echo "test2: $COMMON_REVISION"
//                             sh 'mvn -s $MAVEN_SETTINGS install -D revision=$COMMON_REVISION'
                        }
                    }
                }
            }

//             stage('SonarQube analysis') {
//                 dir('Palisade-common') {
//                     container('docker-cmds') {
//                         withCredentials([string(credentialsId: "${env.SQ_WEB_HOOK}", variable: 'SONARQUBE_WEBHOOK'),
//                                          string(credentialsId: "${env.SQ_KEY_STORE_PASS}", variable: 'KEYSTORE_PASS'),
//                                          file(credentialsId: "${env.SQ_KEY_STORE}", variable: 'KEYSTORE')]) {
//                             configFileProvider([configFile(fileId: "${env.CONFIG_FILE}", variable: 'MAVEN_SETTINGS')]) {
//                                 withSonarQubeEnv(installationName: 'sonar') {
//                                     sh 'mvn -s $MAVEN_SETTINGS org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar -Dsonar.projectKey="Palisade-Common-${GIT_BRANCH_NAME}" -Dsonar.projectName="Palisade-Common-${GIT_BRANCH_NAME}" -Dsonar.webhooks.project=$SONARQUBE_WEBHOOK -Djavax.net.ssl.trustStore=$KEYSTORE -Djavax.net.ssl.trustStorePassword=$KEYSTORE_PASS'
//                                 }
//                             }
//                         }
//                     }
//                 }
//             }
//
//             stage("SonarQube Quality Gate") {
//                 // Wait for SonarQube to prepare the report
//                 sleep(time: 10, unit: 'SECONDS')
//                 // Just in case something goes wrong, pipeline will be killed after a timeout
//                 timeout(time: 5, unit: 'MINUTES') {
//                     // Reuse taskId previously collected by withSonarQubeEnv
//                     def qg = waitForQualityGate()
//                     if (qg.status != 'OK') {
//                         error "Pipeline aborted due to SonarQube quality gate failure: ${qg.status}"
//                     }
//                 }
//             }
//
//             stage('Maven deploy') {
//                 dir('Palisade-common') {
//                     container('docker-cmds') {
//                         configFileProvider([configFile(fileId: "${env.CONFIG_FILE}", variable: 'MAVEN_SETTINGS')]) {
//                             sh 'mvn -s $MAVEN_SETTINGS deploy -P default,quick,avro -D revision=$COMMON_REVISION'
//                         }
//                     }
//                 }
//             }
        }
    }

}

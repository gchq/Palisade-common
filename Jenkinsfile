// pipeline { 
//     agent { docker { image 'maven' } }
//     stages { 
//         stage('Build') { 
//             steps { 
//                sh 'mvn -version'
//                sh 'mvn install' 
//             }
//         }
//     }
// }  
/**
 * This pipeline will execute a simple Maven build
 */

podTemplate(containers: [
  containerTemplate(name: 'maven', image: 'maven:3.6.1-jdk-11', ttyEnabled: true, command: 'cat')
]) {

  node(POD_LABEL) {
    stage('Build a Maven project') {
      git branch: "pal-134-move-common-contents", url: 'https://github.com/gchq/Palisade-common.git'
      container('maven') {
          sh 'ls && pwd'
        configFileProvider(
        [configFile(fileId: '450d38e2-db65-4601-8be0-8621455e93b5', variable: 'MAVEN_SETTINGS')]) {
//        sh 'mvn -s $MAVEN_SETTINGS install'
//          mvn deploy -Drevision=RELEASE -P pi
        sh 'mvn -s $MAVEN_SETTINGS deploy -Drevision=RELEASE -P pi'
        }
      }
    }
  }
}

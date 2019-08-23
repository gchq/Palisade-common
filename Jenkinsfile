pipeline { 
    agent { docker { image 'maven' } }
    stages { 
        stage('Build') { 
            steps { 
               sh 'mvn -version'
               sh 'mvn install' 
            }
        }
    }
}  
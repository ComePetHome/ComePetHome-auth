pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                script {
                    echo 'Checkout Remote Repository' git branch: 'master',
                        credentialsId: 'ComePetHome',
                        url: 'https://github.com/ComePetHome/ComePetHome-auth'
                }
            }
        }

         stage('Build') {
             steps {
                 echo 'Build With gradlew'
                 script {
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean build'
                 }
             }
         }
    }
}
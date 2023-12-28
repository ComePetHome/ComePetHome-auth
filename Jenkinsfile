pipeline {
    agent any

    environment {
        PROJECT_NAME = 'ComePetHome'
        REPOSITORY_URL = 'https://github.com/ComePetHome/ComePetHome-auth'
        DOCKER_HUB_URL = 'registry.hub.docker.com'
        DOCKER_IMAGE_NAME = "comepethome:${env.BRANCH_NAME}"
        DOCKER_HUB_CREDENTIAL_ID = 'rhw0213@gmail.com'
    }

    stages {
        stage('Set Variables') {
            steps {
                echo 'Set Variables'
            }
        }

        stage('Git Checkout') {
            steps {
                echo 'Checkout Remote Repository'
                script {
                    git branch: 'master',
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

        stage('Build & Push Docker Image') {
            steps {
                echo 'Build & Push Docker Image'
                withCredentials([usernamePassword(
                        credentialsId: DOCKER_HUB_CREDENTIAL_ID,
                        usernameVariable: 'DOCKER_HUB_ID',
                        passwordVariable: 'DOCKER_HUB_PW')]) {

                    script {
                        docker.withRegistry(DOCKER_HUB_URL) {
                            app = docker.build(DOCKER_IMAGE_NAME)
                            app.push('latest')
                        }

                        sh(script: """
                            docker rmi \$(docker images -q \
                            --filter \"before=${DOCKER_IMAGE_NAME}:latest\" \
                            ${DOCKER_IMAGE_NAME})
                            """, returnStatus: true)
                    }
                }
            }
        }
    }
}

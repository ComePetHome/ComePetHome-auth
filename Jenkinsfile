pipeline {
    agent any

    environment {
        // BASIC
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
                // 변수 설정 스테이지
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

        stage('Docker Compose Up') {
            steps {
                echo 'Running Docker Compose'
                script {
                    sh 'docker-compose -f $WORKSPACE/docker-compose/docker-compose.yml up -d'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker Image'
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIAL_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD $DOCKER_HUB_URL"
                        sh "docker tag $DOCKER_IMAGE_NAME $DOCKER_HUB_FULL_URL/$DOCKER_IMAGE_NAME"
                        sh "docker push $DOCKER_HUB_FULL_URL/$DOCKER_IMAGE_NAME"
                    }
                }
            }
        }
    }
}

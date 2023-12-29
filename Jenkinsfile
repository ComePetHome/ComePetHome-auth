pipeline {
    agent any

    environment {
        PROJECT_NAME = 'ComePetHome'
        REPOSITORY_URL = 'https://github.com/ComePetHome/ComePetHome-auth'
        DOCKER_HUB_URL = 'https://registry.hub.docker.com'
        DOCKER_HUB_USER_NAME = 'rhw0213'
        DOCKER_HUB_CREDENTIAL_ID = 'DOCKER_HUB_CREDENTIAL_ID'

        SSH_CREDENTIAL_ID = 'ComePetHome_SSH'
        //SSH_PORT_CREDENTIAL_ID = OPERATION_ENV.toUpperCase() + '_SSH_PORT'
        //SSH_HOST_CREDENTIAL_ID = OPERATION_ENV.toUpperCase() + '_SSH_HOST'
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

                    dir('eureka-server'){
                        script {
                            docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                                app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'eureka-server', '/var/jenkins_home/workspace/ComePetHome_master/eureka-server')
                                app.push('latest')
                            }
                            sh(script: """
                                docker rmi \$(docker images -q \
                                --filter \"before=${DOCKER_HUB_CREDENTIAL_ID}/eureka-server:latest\" \
                                ${DOCKER_HUB_URL}/${DOCKER_HUB_CREDENTIAL_ID}/eureka-server)
                            """, returnStatus: true)
                        }
                    }
                }
            }
        }

        stage('Deploy to Server') {
            steps {
                echo 'Deploy to Server'
                withCredentials([
                    usernamePassword(credentialsId: DOCKER_HUB_CREDENTIAL_ID,
                                        usernameVariable: 'DOCKER_HUB_ID',
                                        passwordVariable: 'DOCKER_HUB_PW'),
                    sshUserPrivateKey(credentialsId: SSH_CREDENTIAL_ID,
                                        keyFileVariable: 'KEY_FILE',
                                        passphraseVariable: 'PW',
                                        usernameVariable: 'USERNAME')]) {
                    script {
                        def remote = [:]
                        remote.name = 'deploy'
                        remote.host = '3.36.75.87'
                        remote.user = USERNAME
                        // remote.password = PW
                        remote.identity = KEY_FILE
                        //remote.port = PORT as Integer
                        remote.allowAnyHosts = true

                        // eureka-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest'
                        sshCommand (remote: remote, failOnError: false, command: 'docker rm -f eureka-server')
                        sshCommand remote: remote, command: ('docker run -d --name eureka-server'
                                                + ' -p 8761:' + 8761
                                                + ' ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest')

                        // 이미지 2 배포
                        //sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/another-image:latest'
                        //sshCommand (remote: remote, failOnError: false, command: 'docker rm -f another-container')
                        //sshCommand remote: remote, command: ('docker run -d --name another-container'
                        //                        + ' -p 8080:' + ANOTHER_INTERNAL_PORT
                        //                        + ' -e \"SOME_ENV_VARIABLE=' + OPERATION_ENV + '\"'
                        //                        + ' ' + DOCKER_HUB_ID + '/another-image:latest')
                    }
                }
            }
        }
    }
}

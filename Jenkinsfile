pipeline {
    agent any

    environment {
        PROJECT_NAME = 'ComePetHome'
        REPOSITORY_URL = 'https://github.com/ComePetHome/ComePetHome-auth'
        DOCKER_HUB_URL_ADDRESS = 'registry.hub.docker.com'
        DOCKER_HUB_URL = 'https://' + DOCKER_HUB_URL_ADDRESS
        DOCKER_HUB_USER_NAME = 'rhw0213'
        DOCKER_HUB_CREDENTIAL_ID = 'DOCKER_HUB_CREDENTIAL_ID'

        SSH_CREDENTIAL_ID = 'ComePetHome_SSH'
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
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_USER_NAME}/eureka-server:latest
                            """, returnStatus: true)
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/eureka-server:latest
                            """, returnStatus: true)

                            docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                                app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'eureka-server', '/var/jenkins_home/workspace/ComePetHome_master/eureka-server')
                                app.push('latest')
                            }
                        }
                    }
                    //dir('gateway-server'){
                    //    script {
                    //        docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                    //            app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'gateway-server', '/var/jenkins_home/workspace/ComePetHome_master/gateway-server')
                    //            app.push('latest')
                    //        }
                    //        sh(script: """
                    //            docker rmi \$(docker images -q \
                    //            --filter \"before=${DOCKER_HUB_CREDENTIAL_ID}/gateway-server:latest\" \
                    //            ${DOCKER_HUB_URL}/${DOCKER_HUB_CREDENTIAL_ID}/gateway-server)
                    //        """, returnStatus: true)
                    //        sh(script: """
                    //            docker rmi ${DOCKER_HUB_URL}/${DOCKER_HUB_CREDENTIAL_ID}/gateway-server
                    //        """, returnStatus: true)

                    //    }
                    //}
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
                    usernamePassword(credentialsId: SSH_CREDENTIAL_ID,
                                        usernameVariable: 'USERNAME',
                                        passwordVariable: 'PW')]) {
                    script {
                        def remote = [:]
                        remote.name = 'deploy'
                        remote.host = '3.36.75.87'
                        remote.user = USERNAME
                        remote.password = PW
                        //remote.identity = KEY_FILE
                        //remote.port = PORT as Integer
                        remote.allowAnyHosts = true

                        // docker 전체 다운 및 삭제
                        sshCommand remote: remote, command: 'docker stop $(docker ps -aq) && docker rm -vf $(docker ps -aq) || true'

                        // eureka-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest'
                        //sshCommand (remote: remote, failOnError: false, command: '')
                        sshCommand remote: remote, command: ('docker run -d --name eureka-server'
                                                + ' -p 8761:' + 8761
                                                + ' ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest')

                        // gateway-server 배포
                        //sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest'
                        //sshCommand (remote: remote, failOnError: false, command: 'docker rm -f gateway-server')
                        //sshCommand remote: remote, command: ('docker run -d --name gateway-server'
                        //                        + ' -p 9001:' + 9001
                        //                        + ' ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest')
                    }
                }
            }
        }
    }
}

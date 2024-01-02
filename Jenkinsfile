pipeline {
    agent any

    environment {
        PROJECT_NAME = 'ComePetHome'
        REPOSITORY_URL = 'https://github.com/ComePetHome/ComePetHome-auth'
        DOCKER_HUB_URL_ADDRESS = 'registry.hub.docker.com'
        DOCKER_HUB_URL = 'https://registry.hub.docker.com'
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
                    dir('gateway-server'){
                        script {
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_USER_NAME}/gateway-server:latest
                            """, returnStatus: true)
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/gateway-server:latest
                            """, returnStatus: true)

                            docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                                app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'gateway-server', '/var/jenkins_home/workspace/ComePetHome_master/gateway-server')
                                app.push('latest')
                            }
                        }
                    }
                    dir('user-query-server'){
                        script {
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_USER_NAME}/user-query-server:latest
                            """, returnStatus: true)
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/user-query-server:latest
                            """, returnStatus: true)

                            docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                                app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'user-query-server', '/var/jenkins_home/workspace/ComePetHome_master/user-query-server')
                                app.push('latest')
                            }
                        }
                    }
                    dir('user-command-server'){
                        script {
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_USER_NAME}/user-command-server:latest
                            """, returnStatus: true)
                            sh(script: """
                                docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/user-command-server:latest
                            """, returnStatus: true)

                            docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                                app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'user-command-server', '/var/jenkins_home/workspace/ComePetHome_master/user-command-server')
                                app.push('latest')
                            }
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
                    usernamePassword(credentialsId: SSH_CREDENTIAL_ID,
                                        usernameVariable: 'USERNAME',
                                        passwordVariable: 'PW')]) {
                    script {
                        def remote = [:]
                        remote.name = 'deploy'
                        remote.host = '3.36.75.87'
                        remote.user = USERNAME
                        remote.password = PW
                        remote.allowAnyHosts = true

                        // docker 전체 다운 및 삭제
                        sshCommand remote: remote, command: 'docker stop $(docker ps -aq) && docker rm -vf $(docker ps -aq) || true'

                        // user-maria-db 배포
                        sshCommand remote: remote, command: 'docker pull mariadb'
                        sshCommand remote: remote, command: ('docker run -d --name user-mariadb'
                                                + ' --hostname user-mariadb'
                                                + ' --net comepethome'
                                                + ' --ip 172.17.0.6'
                                                + ' -p 3306:' + 3306
                                                + ' -e MARIADB_ROOT_PASSWORD=admin'
                                                + ' -e MARIADB_DATABASE=comepethome'
                                                + ' mariadb:latest')

                        // eureka-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name eureka-server'
                                                + ' --hostname eureka-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.17.0.2'
                                                + ' -p 8761:' + 8761
                                                + ' ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest')

                        // gateway-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name gateway-server'
                                                + ' --hostname gateway-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.17.0.3'
                                                + ' -p 9001:' + 9001
                                                + ' ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest')
                        // user-command-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/user-command-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name user-command-server'
                                                + ' --hostname user-command-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.17.0.4'
                                                + ' -p 8081:' + 8081
                                                + ' ' + DOCKER_HUB_USER_NAME + '/user-command-server:latest')
                        // user-query-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/user-query-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name user-query-server'
                                                + ' --hostname user-query-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.17.0.5'
                                                + ' -p 8082:' + 8082
                                                + ' ' + DOCKER_HUB_USER_NAME + '/user-query-server:latest')

                    }
                }
            }
        }
    }
}

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
                        remote.host = '13.124.211.208'
                        remote.user = USERNAME
                        remote.password = PW
                        remote.allowAnyHosts = true

                        // user-mariadb 삭제
                        sshCommand remote: remote, command: 'docker stop user-mariadb || true'
                        sshCommand remote: remote, command: 'docker rm user-mariadb|| true'
                        sshCommand remote: remote, command: 'docker image rm mariadb || true'

                        // user-mongodb 삭제
                        sshCommand remote: remote, command: 'docker stop user-mongodb || true'
                        sshCommand remote: remote, command: 'docker rm user-mongodb|| true'
                        sshCommand remote: remote, command: 'docker image rm mongodb || true'

                        // user-gateway-server 삭제
                        sshCommand remote: remote, command: 'docker stop gateway-server || true'
                        sshCommand remote: remote, command: 'docker rm gateway-server || true'
                        sshCommand remote: remote, command: 'docker image rm rhw0213/gateway-server || true'

                        // user-eureka-server 삭제
                        sshCommand remote: remote, command: 'docker stop eureka-server || true'
                        sshCommand remote: remote, command: 'docker rm eureka-server || true'
                        sshCommand remote: remote, command: 'docker image rm rhw0213/eureka-server || true'

                        // user-command-server 삭제
                        sshCommand remote: remote, command: 'docker stop user-command-server || true'
                        sshCommand remote: remote, command: 'docker rm user-command-server || true'
                        sshCommand remote: remote, command: 'docker image rm rhw0213/user-command-server || true'

                        // user-query-server 삭제
                        sshCommand remote: remote, command: 'docker stop user-query-server || true'
                        sshCommand remote: remote, command: 'docker rm user-query-server || true'
                        sshCommand remote: remote, command: 'docker image rm rhw0213/user-query-server || true'

                        // kafka 삭제
                        sshCommand remote: remote, command: 'docker stop kafka-server || true'
                        sshCommand remote: remote, command: 'docker rm kafka-server || true'
                        sshCommand remote: remote, command: 'docker image rm ubuntu || true'

                        // user-maria-db 배포
                        sshCommand remote: remote, command: 'docker pull mariadb:10.4'
                        sshCommand remote: remote, command: ('docker run -d --name user-mariadb'
                                                + ' --hostname user-mariadb'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.6'
                                                + ' -p 3308:' + 3308
                                                + ' mariadb:10.4')

                        // user-mongo-db 배포
                        sshCommand remote: remote, command: 'docker pull mongo'
                        sshCommand remote: remote, command: ('docker run -d --name user-mongodb'
                                                + ' --hostname user-mongodb'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.9'
                                                //+ ' -p 27017:' + 27017
                                                + ' -e MONGO_INITDB_ROOT_USERNAME=root'
                                                + ' -e MONGO_INITDB_ROOT_PASSWORD=admin'
                                                + ' -e MONGO_INITDB_DATABASE=comepethome'
                                                + ' mongo')

                        // eureka-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name eureka-server'
                                                + ' --hostname eureka-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.2'
                                                + ' -p 8761:' + 8761
                                                + ' ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest')

                        // gateway-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name gateway-server'
                                                + ' --hostname gateway-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.3'
                                                + ' -p 9001:' + 9001
                                                + ' ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest')
                         user-command-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/user-command-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name user-command-server'
                                                + ' --hostname user-command-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.4'
                                                //+ ' -p 8081:' + 8081
                                                + ' ' + DOCKER_HUB_USER_NAME + '/user-command-server:latest')
                        // user-query-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/user-query-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name user-query-server'
                                                + ' --hostname user-query-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.5'
                                                //+ ' -p 8082:' + 8082
                                                + ' ' + DOCKER_HUB_USER_NAME + '/user-query-server:latest')
                        // kafka 배포
                        sshCommand remote: remote, command: 'docker pull ubuntu:20.04'
                        sshCommand remote: remote, command: ('docker run -it -d --name kafka-server'
                                                + ' --hostname kafka-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.7'
                                                + ' ubuntu:20.04')

                    }
                }
            }
        }
    }
}

pipeline {
    agent any

    environment {
        PROJECT_NAME = 'ComePetHome'
        REPOSITORY_URL = 'https://github.com/ComePetHome/ComePetHome-auth'
        DOCKER_HUB_URL_ADDRESS = 'registry.hub.docker.com'
        DOCKER_HUB_URL = 'https://registry.hub.docker.com'
        DOCKER_HUB_USER_NAME = 'rhw0213'
        DOCKER_HUB_CREDENTIAL_ID = 'DOCKER_HUB_CREDENTIAL_ID'
        COME_PET_HOME_DB = 'ComePetHome_DB'
        SSH_CREDENTIAL_ID = 'ComePetHome_SSH'
        AWS_S3_ACCESS_KEY = 'Aws_S3_Access_Key'
        AWS_S3_SECRET_KEY = 'Aws_S3_Secret_Key'

        SERVER_IP = 'SERVER_IP'
        SERVER_PORT= 'SERVER_PORT'

        TEST_ID = "Test_Id"
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

        stage('Yml file update') {
            steps {
                echo 'Yml file update'
                withCredentials([
                        usernamePassword(
                        credentialsId: COME_PET_HOME_DB,
                        usernameVariable: 'DB_ID',
                        passwordVariable: 'DB_PW'),
                        usernamePassword(
                        credentialsId: TEST_ID,
                        usernameVariable: 'ID',
                        passwordVariable: 'PW'),
                        string(credentialsId: AWS_S3_ACCESS_KEY, variable: 'MY_ACCESS_KEY'),
                        string(credentialsId: AWS_S3_SECRET_KEY, variable: 'MY_SECRET_KEY'),
                        string(credentialsId: SERVER_IP, variable: 'IP'),
                        string(credentialsId: SERVER_PORT, variable: 'PORT')]) {
                        script {
                            sh(script: """ yq -i '.spring.datasource.username="${DB_ID}"' /var/jenkins_home/workspace/ComePetHome_master/user-command-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.spring.datasource.password="${DB_PW}"' /var/jenkins_home/workspace/ComePetHome_master/user-command-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.test.id="${ID}"' /var/jenkins_home/workspace/ComePetHome_master/user-command-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.test.pw="${pw}"' /var/jenkins_home/workspace/ComePetHome_master/user-command-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.test.server-ip="${IP}"' /var/jenkins_home/workspace/ComePetHome_master/user-command-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.test.port="${PORT}"' /var/jenkins_home/workspace/ComePetHome_master/user-command-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.spring.datasource.username="${DB_ID}"' /var/jenkins_home/workspace/ComePetHome_master/user-query-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.spring.datasource.password="${DB_PW}"' /var/jenkins_home/workspace/ComePetHome_master/user-query-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.cloud.aws.s3.accessKey="${MY_ACCESS_KEY}"' /var/jenkins_home/workspace/ComePetHome_master/image-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.cloud.aws.s3.secretKey="${MY_SECRET_KEY}"' /var/jenkins_home/workspace/ComePetHome_master/image-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.spring.data.mongodb.username="${DB_ID}"' /var/jenkins_home/workspace/ComePetHome_master/image-server/src/main/resources/application.yml """)
                            sh(script: """ yq -i '.spring.data.mongodb.password="${DB_PW}"' /var/jenkins_home/workspace/ComePetHome_master/image-server/src/main/resources/application.yml """)
                        }
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
                withCredentials([
                        usernamePassword(credentialsId: DOCKER_HUB_CREDENTIAL_ID,
                        usernameVariable: 'DOCKER_HUB_ID',
                        passwordVariable: 'DOCKER_HUB_PW')]) {

                    //dir('eureka-server'){
                    //    script {
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_USER_NAME}/eureka-server:latest
                    //        """, returnStatus: true)
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/eureka-server:latest
                    //        """, returnStatus: true)

                    //        docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                    //            app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'eureka-server', '/var/jenkins_home/workspace/ComePetHome_master/eureka-server')
                    //            app.push('latest')
                    //        }
                    //    }
                    //}
                    //dir('gateway-server'){
                    //    script {
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_USER_NAME}/gateway-server:latest
                    //        """, returnStatus: true)
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/gateway-server:latest
                    //        """, returnStatus: true)

                    //        docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                    //            app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'gateway-server', '/var/jenkins_home/workspace/ComePetHome_master/gateway-server')
                    //            app.push('latest')
                    //        }
                    //    }
                    //}
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
                    //dir('user-command-server'){
                    //    script {
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_USER_NAME}/user-command-server:latest
                    //        """, returnStatus: true)
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/user-command-server:latest
                    //        """, returnStatus: true)

                    //        docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                    //            app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'user-command-server', '/var/jenkins_home/workspace/ComePetHome_master/user-command-server')
                    //            app.push('latest')
                    //        }
                    //    }
                    //}
                    //dir('image-server'){
                    //    script {
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_USER_NAME}/image-server:latest
                    //        """, returnStatus: true)
                    //        sh(script: """
                    //            docker image rmi -f ${DOCKER_HUB_URL_ADDRESS}/${DOCKER_HUB_USER_NAME}/image-server:latest
                    //        """, returnStatus: true)

                    //        docker.withRegistry(DOCKER_HUB_URL, DOCKER_HUB_CREDENTIAL_ID) {
                    //            app = docker.build(DOCKER_HUB_USER_NAME + '/' + 'image-server', '/var/jenkins_home/workspace/ComePetHome_master/image-server')
                    //            app.push('latest')
                    //        }
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
                                        passwordVariable: 'PW'),
                    string(credentialsId: SERVER_IP, variable: 'IP'),
                    string(credentialsId: SERVER_PORT, variable: 'PORT')]) {
                    script {
                        def remote = [:]
                        remote.name = 'deploy'
                        remote.host = IP
                        remote.port = PORT.toInteger()
                        remote.user = USERNAME
                        remote.password = PW
                        remote.allowAnyHosts = true

                        //// user-mariadb 삭제
                        //sshCommand remote: remote, command: 'docker stop user-mariadb || true'
                        //sshCommand remote: remote, command: 'docker rm user-mariadb|| true'
                        //sshCommand remote: remote, command: 'docker image rm mariadb || true'

                        //// user-mysql 삭제
                        //sshCommand remote: remote, command: 'docker stop user-mysqldb || true'
                        //sshCommand remote: remote, command: 'docker rm user-mysqldb || true'
                        //sshCommand remote: remote, command: 'docker image rm mysql || true'

                        //// user-gateway-server 삭제
                        //sshCommand remote: remote, command: 'docker stop gateway-server || true'
                        //sshCommand remote: remote, command: 'docker rm gateway-server || true'
                        //sshCommand remote: remote, command: 'docker image rm rhw0213/gateway-server || true'

                        //// user-eureka-server 삭제
                        //sshCommand remote: remote, command: 'docker stop eureka-server || true'
                        //sshCommand remote: remote, command: 'docker rm eureka-server || true'
                        //sshCommand remote: remote, command: 'docker image rm rhw0213/eureka-server || true'

                        //// user-command-server 삭제
                        //sshCommand remote: remote, command: 'docker stop user-command-server || true'
                        //sshCommand remote: remote, command: 'docker rm user-command-server || true'
                        //sshCommand remote: remote, command: 'docker image rm rhw0213/user-command-server || true'

                        // user-query-server 삭제
                        sshCommand remote: remote, command: 'docker stop user-query-server || true'
                        sshCommand remote: remote, command: 'docker rm user-query-server || true'
                        sshCommand remote: remote, command: 'docker image rm rhw0213/user-query-server || true'

                        //// image-server 삭제
                        //sshCommand remote: remote, command: 'docker stop image-server || true'
                        //sshCommand remote: remote, command: 'docker rm image-server || true'
                        //sshCommand remote: remote, command: 'docker image rm rhw0213/image-server || true'

                        //// kafka 삭제
                        //sshCommand remote: remote, command: 'docker stop kafka-server || true'
                        //sshCommand remote: remote, command: 'docker rm kafka-server || true'
                        //sshCommand remote: remote, command: 'docker image rm ubuntu || true'

                        //// image-mongo삭제
                        //sshCommand remote: remote, command: 'docker stop image-mongodb || true'
                        //sshCommand remote: remote, command: 'docker rm image-mongodb || true'
                        //sshCommand remote: remote, command: 'docker image rm mongo || true'

                        //// user-maria-db 배포
                        //sshCommand remote: remote, command: 'docker pull mariadb:10.4'
                        //sshCommand remote: remote, command: ('docker run -d --name user-mariadb'
                        //                        + ' --hostname user-mariadb'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.6'
                        //                        + ' -p 3308:' + 3306
                        //                        + ' mariadb:10.4')

                        //// user-mysql-db 배포
                        //sshCommand remote: remote, command: 'docker pull mysql'
                        //sshCommand remote: remote, command: ('docker run -d --name user-mysqldb'
                        //                        + ' --hostname user-mysqldb'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.9'
                        //                        + ' -p 3309:' + 3306
                        //                        + ' mysql:latest')

                        //// eureka-server 배포
                        //sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest'
                        //sshCommand remote: remote, command: ('docker run -d --name eureka-server'
                        //                        + ' --hostname eureka-server'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.2'
                        //                        + ' -p 8761:' + 8761
                        //                        + ' ' + DOCKER_HUB_USER_NAME + '/eureka-server:latest')

                        //// gateway-server 배포
                        //sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest'
                        //sshCommand remote: remote, command: ('docker run -d --name gateway-server'
                        //                        + ' --hostname gateway-server'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.3'
                        //                        + ' -p 9001:' + 9001
                        //                        + ' ' + DOCKER_HUB_USER_NAME + '/gateway-server:latest')

                        //// user-command-server 배포
                        //sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/user-command-server:latest'
                        //sshCommand remote: remote, command: ('docker run -d --name user-command-server'
                        //                        + ' --hostname user-command-server'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.4'
                        //                        //+ ' -p 8081:' + 8081
                        //                        + ' ' + DOCKER_HUB_USER_NAME + '/user-command-server:latest')

                        // user-query-server 배포
                        sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/user-query-server:latest'
                        sshCommand remote: remote, command: ('docker run -d --name user-query-server'
                                                + ' --hostname user-query-server'
                                                + ' --net comepethome'
                                                + ' --ip 172.18.0.5'
                                                //+ ' -p 8082:' + 8082
                                                + ' ' + DOCKER_HUB_USER_NAME + '/user-query-server:latest')

                        //// image-server 배포
                        //sshCommand remote: remote, command: 'docker pull ' + DOCKER_HUB_USER_NAME + '/image-server:latest'
                        //sshCommand remote: remote, command: ('docker run -d --name image-server'
                        //                        + ' --hostname image-server'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.10'
                        //                        //+ ' -p 8082:' + 8082
                        //                        + ' ' + DOCKER_HUB_USER_NAME + '/image-server:latest')

                        //// kafka 배포
                        //sshCommand remote: remote, command: 'docker pull ubuntu:20.04'
                        //sshCommand remote: remote, command: ('docker run -it -d --name kafka-server'
                        //                        + ' --hostname kafka-server'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.7'
                        //                        + ' ubuntu:20.04')

                        //// image-mongo-db 배포
                        //sshCommand remote: remote, command: 'docker pull mongo'
                        //sshCommand remote: remote, command: ('docker run -d --name image-mongodb'
                        //                        + ' --hostname image-mongodb'
                        //                        + ' --net comepethome'
                        //                        + ' --ip 172.18.0.11'
                        //                        //+ ' -p 27017:' + 27017
                        //                        + ' mongo')

                    }
                }
            }
        }
    }
}

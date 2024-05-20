pipeline {
    agent any

    stages {
        
        stage('rm directory') {
            steps {
                sh "echo rm directory"
                sh "ls -al"
                sh "pwd"
                sh "rm -rf *"
                sh "echo rm directory2"
                sh "ls -al"
            }
        }
        
        stage('Checkout') {
            steps {
                // 소스 코드를 클론하는 Git 명령어
                sh "ls -al"
                git branch: 'release2', credentialsId: 'GIT_CREDENTIALS_ID', url: 'https://lab.ssafy.com/s10-final/S10P31C105.git'
                sh "ls -al"
            }
        }

        stage('Build and Push Docker Images') {
            steps {
                script {
                    // 각 프로젝트 폴더에서 Docker 이미지를 빌드하고 Docker Hub에 푸시
                    sh "pwd"
                    sh "ls -al"
                    buildAndPushImage('frontend')
                    sh "docker image ls"
                    buildAndPushImage('BackEnd')
                    sh "docker image ls"
                }
            }
        }

        stage('Deploy with Docker') {
            steps {
                script {
                    // 1. 기존 작동중인 컨테이너 삭제 및 중지
                    sh 'docker stop Frontend Backend || true'
                    sh 'docker rm Frontend Backend || true'

                    // 2. 기존 이미지를 Docker Hub에서 최신 버전으로 갱신
                    sh 'docker pull habu2710/stellar-frontend:latest'
                    sh 'docker pull habu2710/stellar-backend:latest'

                    // 3. 새로 갱신된 이미지를 기반으로 컨테이너 실행
                    sh 'docker run -d --name Frontend -p 5173:5173 habu2710/stellar-frontend:latest'
                    sh 'docker run -d --name Backend -p 8080:8080 --env-file /.env habu2710/stellar-backend:latest'
                }
            }
        }
    }
}

def buildAndPushImage(projectName) {
    def lowercaseProjectName = projectName.toLowerCase()
    if("${projectName}" == "BackEnd") {
        dir("${projectName}/StellAR") {
            sh "ls -al"
            sh "docker build -t habu2710/stellar-${lowercaseProjectName}:latest ."
            withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_ID')]) {
                sh "docker login -u $DOCKER_ID -p $DOCKER_PASSWORD"
                sh "docker push habu2710/stellar-${lowercaseProjectName}:latest"
            }
        }
    } else {
        dir("${projectName}") {
            sh "docker build -t habu2710/stellar-${lowercaseProjectName}:latest ."
            withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_ID')]) {
                sh "docker login -u $DOCKER_ID -p $DOCKER_PASSWORD"
                sh "docker push habu2710/stellar-${lowercaseProjectName}:latest"
            }
        }
    }

}

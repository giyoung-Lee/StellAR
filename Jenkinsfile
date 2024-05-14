pipeline {
    agent any
    
    stages {
        stage('Build and Push Docker Images') {
            steps {
                script {
                    // 각 프로젝트 폴더에서 Docker 이미지를 빌드하고 Docker Hub에 푸시
                    sh "docker image ls"
                    buildAndPushImage('Frontend')
                    sh "docker image ls"
                    buildAndPushImage('Backend')
                    sh "docker image ls"
                }
            }
        }
        
        stage('Deploy with Docker') {
            steps {
                script {

                    // 1. 기존 작동중인 컨테이너 삭제 및 중지
                    // 기존 컨테이너 중지
                    sh 'docker stop Frontend Backend || true' 
                    // 기존 컨테이너 삭제

                    sh 'docker rm Frontend Backend || true'   
                    // 2. 기존 이미지를 Docker Hub에서 최신 버전으로 갱신
                    sh 'docker pull habu2710/stellar-frontend:latest'
                    sh 'docker pull habu2710/stellar-backend:latest'

                    // 3. 새로 갱신된 이미지를 기반으로 컨테이너 실행
                    sh 'docker run -d --name Frontend -p 5173:5173 \n'
                    'habu2710/stellar-frontend:latest'
                    
                    sh 'docker run -d \
												--name Backend \
												-p 8080:8080 \
												--env-file /home/ubuntu/.env \
												habu2710/stellar-backend:latest'

                    
                }
            }
        }

    }
}

def buildAndPushImage(projectName) {
    // 프로젝트 폴더로 이동하여 Docker 이미지 빌드 및 푸시
    def lowercaseProjectName = projectName.toLowerCase()
    dir("${projectName}") {
        // Docker 이미지 빌드
        sh "docker build -t habu2710/stellar-${lowercaseProjectName}:latest ."
        sh "echo ${projectName} ${lowercaseProjectName}"
        
        // Docker Hub에 이미지 푸시
        withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_ID')]) {
            sh "docker login -u DOCKER_ID -p $DOCKER_PASSWORD"
            sh "docker push habu2710/stellar-${lowercaseProjectName}:latest"    
        }
    }
}

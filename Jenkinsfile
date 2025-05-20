pipeline {
    agent any
    tools{
        maven 'MAVEN_3_9_9'
    }
    stages {
        stage('Build Maven') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'spring-jenkins', url: 'https://github.com/XMasud/dev-automation.git']])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image') {
            steps{
                script{
                    sh 'docker build -t masud1122/dev-automation .'
                }
            }
        }
        stage('Push docker image to hub') {
            steps{
                script{
                    withCredentials([string(credentialsId: 'docker-pwd', variable: 'dockerhubpwd')]) {
                        sh "echo ${dockerhubpwd} | docker login -u masud1122 --password-stdin"
                    }
                    sh 'docker push masud1122/dev-automation'
                }
            }
        }
        stage('Deploy to k8s'){
            steps{

            }
        }
    }
}

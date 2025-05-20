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
                script{
                    kubeconfig(caCertificate: '''-----BEGIN CERTIFICATE-----
                    MIIDITCCAgmgAwIBAgIBAjANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDEwptaW5p
                    a3ViZUNBMB4XDTI1MDUxODIwMjE1MFoXDTI4MDUxODIwMjE1MFowMTEXMBUGA1UE
                    ChMOc3lzdGVtOm1hc3RlcnMxFjAUBgNVBAMTDW1pbmlrdWJlLXVzZXIwggEiMA0G
                    CSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDb63ud7SPBfRXrSQa1lyNWPXbqJZ0e
                    FTDI9HBAc8OP4KwIF+eX1z1CVJN+Ee/xFGQ8F0i/O+NPgMtccPwX6qBu2IChbIrx
                    tj3xcyluHj762KJ/i8m50UiQP98JOZUVWsWgcd1KMtzNYACpfwE6fxK/X26VWvFy
                    ZoXrYMFkOy4PfYMPzaazSu7cNyGplUqk+On1ipRiRNNXnw2GNVREF1XTdRlvKsvg
                    tutxkkZlRwlsZ8r9YE7odOCv6mGKFOdj5c9hxq4h5bHi9OTBHWSesD3qLlF/WfNl
                    l+74mHbPYCaAUYKgNnNOI58+iZmuSlLjBkZMuXuhFlxz0MZW8F4Sm7d7AgMBAAGj
                    YDBeMA4GA1UdDwEB/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUH
                    AwIwDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBSxOhqp31QGi2wOE6sXaeEZLxlf
                    lDANBgkqhkiG9w0BAQsFAAOCAQEAU4yoFQz8TTqI8HPav/5cK6SW91rTIhROiSIx
                    L2l0zPtUMIzToMRcDa2UytprgPhq/RWxfL5fWYNsG7j2lMWhg1YHtOj7rMBYXqfV
                    zvOO5EqLZtZLoEEnKkqmeT5T4Nak269Q9XelBrLDYz/Pg+u9VQ77oFK4/HDqLEZv
                    pCLrK48vn5j4iMKaBY9kWoH4WWcmHY9a0D9tyUhNbT1KnBewtGm9ifO+/46zyjmT
                    +Rxikh1ySan8yLLmbPxoBtlGxLZAYwQ5M9ZbwPmpHYkXwc6K01x4nXJ2Be82+6uy
                    KTmkNzPaNorN0orKOZ4isc7QKFj9Z3w1yGC06INe5ZTgr+gz9g==
                    -----END CERTIFICATE-----''', credentialsId: 'kubeconfig', serverUrl: 'https://127.0.0.1:59981') {
                        sh 'kubectl apply -f k8s-deployment.yaml'
                        sh 'kubectl apply -f k8s-service.yaml'
                    }
                }
            }
        }
    }
}

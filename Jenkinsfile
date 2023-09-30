pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                bat './gradlew test'
            }
            post {
                always {
                    junit (testResults: '**/build/test-results/test/TEST-*.xml', allowEmptyResults: true)
                }
            }
        }
        stage('Build') {
            steps {
                bat './gradlew clean build'
            }
        }
        stage('Sonar') {
            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                  bat "./gradlew sonar \
                    -Dsonar.projectKey=stage4-module3-task \
                    -Dsonar.projectName='stage4-module3-task' \
                    -Dsonar.host.url=http://127.0.0.1:9000 \
                    -Dsonar.token=sqp_52571e43ada294196d50be57c0644255407d11bc"
                    //-Dsonar.token=sqp_be3f8a0f05d5255ae862971030e30a58e0ca1655
                }
            }
        }
        stage("Quality gate") {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Deploy') {
            steps {
                build 'stage4-module3-task-deploy-job'
            }
        }
    }
}
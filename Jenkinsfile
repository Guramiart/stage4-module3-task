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
                  // TODO: Setup Jenkins
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
                build // TODO: Setup Jenkins
            }
        }
    }
}
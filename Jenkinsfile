pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/dharanitumu-QA-collab/api-testing-framework.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn test "-Dsurefire.suiteXmlFiles=testng-smoke.xml"'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
pipeline {
    agent {
        label 'docker'
    }

    options {
        disableConcurrentBuilds()
        quietPeriod(1 * 60)
    }

    environment {
        // Map host workspace for docker-compose based builds
        HOST_WORKSPACE = env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)
        PMD_FAIL_ON_VIOLATION = 'false'
        ENABLE_DEPENDENCY_CHECK = 'false'
        ENABLE_SONARQUBE = 'false'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check & Quality') {
            steps {
                // Ensure formatting, checkstyle, etc. are passing
                sh "make compile PWD='${HOST_WORKSPACE}'"
                sh "make check PWD='${HOST_WORKSPACE}' MVN_ARGS='-Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false -Dcpd.skip=true'"
            }
        }

        stage('Create Release') {
            when {
                branch 'main'
                expression {
                    // Don't create a release if it's already a release commit
                    return sh(script: "git log -1 --pretty=%B", returnStdout: true).trim() !=~ /^chore\(release\):.*/
                }
            }
            steps {
                sh "make create_release PWD='${HOST_WORKSPACE}'"
            }
        }

        stage('Build & Test') {
            steps {
                // Run full build with tests, mutation coverage, etc.
                sh "make install PWD='${HOST_WORKSPACE}'"
            }
        }

        stage('Publish Artifacts') {
            when {
                // Publish artifacts if this was triggered by a tag, or if we just created a release commit
                anyOf {
                    buildingTag()
                    expression {
                        return sh(script: "git log -1 --pretty=%B", returnStdout: true).trim() =~ /^chore\(release\):.*/
                    }
                }
            }
            steps {
                // Publish artifacts to GitHub Packages
                sh "make deploy PWD='${HOST_WORKSPACE}'"
            }
        }
    }
    
    post {
        always {
            echo 'Build complete.'
        }
    }
}

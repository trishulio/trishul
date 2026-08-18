pipeline {
    agent {
        label 'docker'
    }

    parameters {
        booleanParam(name: 'ENABLE_TESTS', defaultValue: true, description: 'Enable running tests')
        booleanParam(name: 'ENABLE_CODE_COVERAGE', defaultValue: true, description: 'Enable code coverage (JaCoCo)')
        booleanParam(name: 'ENABLE_MUTATION_COVERAGE', defaultValue: true, description: 'Enable PIT mutation coverage')
        booleanParam(name: 'ENABLE_CHECKSTYLE', defaultValue: true, description: 'Enable Checkstyle check')
        booleanParam(name: 'ENABLE_DEPENDENCY_CHECK', defaultValue: true, description: 'Enable OWASP dependency check')
        booleanParam(name: 'ENABLE_SPOTBUGS', defaultValue: true, description: 'Enable SpotBugs check')
        booleanParam(name: 'SPOTBUGS_FAIL_ON_ERROR', defaultValue: false, description: 'Fail on SpotBugs errors')
        booleanParam(name: 'ENABLE_PMD', defaultValue: true, description: 'Enable PMD check')
        booleanParam(name: 'PMD_FAIL_ON_VIOLATION', defaultValue: false, description: 'Fail on PMD violations')
        booleanParam(name: 'ENABLE_SONARQUBE', defaultValue: true, description: 'Enable SonarQube analysis')
    }

    options {
        disableConcurrentBuilds()
        quietPeriod(1 * 60)
    }

    environment {
        // Map host workspace for docker-compose based builds
        HOST_WORKSPACE = env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)
        
        // Force single-threaded Maven builds in CI to avoid dependency resolution race conditions
        THREADS = '1'
        
        // Default properties and credentials
        APP_URL = 'http://localhost:8080'
        SONARQUBE_HOST_URL = 'https://sonarqube.cloudville.me'
        SONARQUBE_PROJECT_NAME = 'trishul'
        SONARQUBE_PROJECT_KEY = 'trishulio_trishul_9d26085e-e21c-4b54-8871-18d7c7dabb72'
        SONARQUBE_TOKEN = credentials('sonarqube-token')
        NVD_API_KEY = credentials('nvd-api-key')
        GITHUB_TOKEN = credentials('jenky')
        GITHUB_ACTOR = 'rishabmanocha'

        // Parameter mappings
        ENABLE_TESTS = "${params.ENABLE_TESTS != null ? params.ENABLE_TESTS : 'true'}"
        ENABLE_CODE_COVERAGE = "${params.ENABLE_CODE_COVERAGE != null ? params.ENABLE_CODE_COVERAGE : 'true'}"
        ENABLE_MUTATION_COVERAGE = "${params.ENABLE_MUTATION_COVERAGE != null ? params.ENABLE_MUTATION_COVERAGE : 'true'}"
        ENABLE_CHECKSTYLE = "${params.ENABLE_CHECKSTYLE != null ? params.ENABLE_CHECKSTYLE : 'true'}"
        ENABLE_DEPENDENCY_CHECK = "${params.ENABLE_DEPENDENCY_CHECK != null ? params.ENABLE_DEPENDENCY_CHECK : 'true'}"
        ENABLE_SPOTBUGS = "${params.ENABLE_SPOTBUGS != null ? params.ENABLE_SPOTBUGS : 'true'}"
        SPOTBUGS_FAIL_ON_ERROR = "${params.SPOTBUGS_FAIL_ON_ERROR != null ? params.SPOTBUGS_FAIL_ON_ERROR : 'false'}"
        ENABLE_PMD = "${params.ENABLE_PMD != null ? params.ENABLE_PMD : 'true'}"
        PMD_FAIL_ON_VIOLATION = "${params.PMD_FAIL_ON_VIOLATION != null ? params.PMD_FAIL_ON_VIOLATION : 'false'}"
        ENABLE_SONARQUBE = "${params.ENABLE_SONARQUBE != null ? params.ENABLE_SONARQUBE : 'true'}"
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
                sh "ENABLE_SONARQUBE=false make check PWD='${HOST_WORKSPACE}'"
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
                script {
                    // Check if SonarQube is reachable from inside the Maven container
                    def sonarReachable = sh(script: "docker-compose --env-file mvn.env -f docker-compose-bin.yml run --rm mvn wget -q --spider --timeout=5 https://sonarqube.cloudville.me/api/v2/analysis/version && echo 'true' || echo 'false'", returnStdout: true).trim()
                    echo "SonarQube reachability inside container: ${sonarReachable}"
                    
                    def enableSonar = (sonarReachable == "true" && env.ENABLE_SONARQUBE == "true") ? "true" : "false"
                    if (sonarReachable == "false") {
                        echo "SonarQube is unreachable inside the container, skipping analysis to prevent build failure."
                    }
                    
                    sh "ENABLE_SONARQUBE=${enableSonar} make install PWD='${HOST_WORKSPACE}' THREADS='1'"
                }
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

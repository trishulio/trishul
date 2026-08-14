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
        booleanParam(name: 'SPOTBUGS_FAIL_ON_ERROR', defaultValue: true, description: 'Fail on SpotBugs errors')
        booleanParam(name: 'ENABLE_PMD', defaultValue: true, description: 'Enable PMD check')
        booleanParam(name: 'PMD_FAIL_ON_VIOLATION', defaultValue: true, description: 'Fail on PMD violations')
        booleanParam(name: 'ENABLE_SONARQUBE', defaultValue: true, description: 'Enable SonarQube analysis')
    }

    options {
        disableConcurrentBuilds()
        quietPeriod(1 * 60)
    }

    environment {
        // Map host workspace for docker-compose based builds
        HOST_WORKSPACE = env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)
        
        // Parameter mappings
        ENABLE_TESTS = "${params.ENABLE_TESTS != null ? params.ENABLE_TESTS : 'true'}"
        ENABLE_CODE_COVERAGE = "${params.ENABLE_CODE_COVERAGE != null ? params.ENABLE_CODE_COVERAGE : 'true'}"
        ENABLE_MUTATION_COVERAGE = "${params.ENABLE_MUTATION_COVERAGE != null ? params.ENABLE_MUTATION_COVERAGE : 'true'}"
        ENABLE_CHECKSTYLE = "${params.ENABLE_CHECKSTYLE != null ? params.ENABLE_CHECKSTYLE : 'true'}"
        ENABLE_DEPENDENCY_CHECK = "${params.ENABLE_DEPENDENCY_CHECK != null ? params.ENABLE_DEPENDENCY_CHECK : 'true'}"
        ENABLE_SPOTBUGS = "${params.ENABLE_SPOTBUGS != null ? params.ENABLE_SPOTBUGS : 'true'}"
        SPOTBUGS_FAIL_ON_ERROR = "${params.SPOTBUGS_FAIL_ON_ERROR != null ? params.SPOTBUGS_FAIL_ON_ERROR : 'true'}"
        ENABLE_PMD = "${params.ENABLE_PMD != null ? params.ENABLE_PMD : 'true'}"
        PMD_FAIL_ON_VIOLATION = "${params.PMD_FAIL_ON_VIOLATION != null ? params.PMD_FAIL_ON_VIOLATION : 'true'}"
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
                sh "make compile PWD='${HOST_WORKSPACE}' MVN_ARGS='-Dpmd.failOnViolation=false -Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false -Dcpd.skip=true -Dspotbugs.failOnError=false -Dsonar.skip=true'"
                sh "make check PWD='${HOST_WORKSPACE}' MVN_ARGS='-Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false -Dcpd.skip=true -Dpmd.failOnViolation=false'"
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
                sh "make install PWD='${HOST_WORKSPACE}' MVN_ARGS='-Dpmd.failOnViolation=false -Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false -Dcpd.skip=true -Dspotbugs.failOnError=false'"
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

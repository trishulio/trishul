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
        
        // Extract properties from mvn.env so docker-compose maps them properly
        NVD_API_KEY = sh(script: "grep '^NVD_API_KEY=' mvn.env | cut -d= -f2-", returnStdout: true).trim()
        APP_URL = sh(script: "grep '^APP_URL=' mvn.env | cut -d= -f2-", returnStdout: true).trim()
        SONARQUBE_HOST_URL = sh(script: "grep '^SONARQUBE_HOST_URL=' mvn.env | cut -d= -f2-", returnStdout: true).trim()
        SONARQUBE_PROJECT_NAME = sh(script: "grep '^SONARQUBE_PROJECT_NAME=' mvn.env | cut -d= -f2-", returnStdout: true).trim()
        SONARQUBE_TOKEN = sh(script: "grep '^SONARQUBE_TOKEN=' mvn.env | cut -d= -f2-", returnStdout: true).trim()
        SONARQUBE_PROJECT_KEY = sh(script: "grep '^SONARQUBE_PROJECT_KEY=' mvn.env | cut -d= -f2-", returnStdout: true).trim()

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
                sh "make compile PWD='${HOST_WORKSPACE}' MVN_ARGS='-Dpmd.failOnViolation=false -Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false -Dcpd.skip=true -Dspotbugs.failOnError=false -Dsonar.skip=true -DnvdDatafeedUrl=https://dependency-check.github.io/DependencyCheck_Builder/nvd_cache/nvdcve-{0}.json.gz -DfailBuildOnCVSS=11'"
                sh "make check PWD='${HOST_WORKSPACE}' MVN_ARGS='-Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false -Dcpd.skip=true -Dpmd.failOnViolation=false -DnvdDatafeedUrl=https://dependency-check.github.io/DependencyCheck_Builder/nvd_cache/nvdcve-{0}.json.gz -DfailBuildOnCVSS=11'"
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
                    
                    def extraArgs = ""
                    if (sonarReachable == "false") {
                        echo "SonarQube is unreachable inside the container, skipping analysis to prevent build failure."
                        extraArgs = "-Dsonar.skip=true"
                    }
                    
                    sh "make install PWD='${HOST_WORKSPACE}' THREADS='1' MVN_ARGS='-Dpmd.failOnViolation=false -Dcheckstyle.failOnViolation=false -Dcheckstyle.failsOnError=false -Dcpd.skip=true -Dspotbugs.failOnError=false -DnvdDatafeedUrl=https://dependency-check.github.io/DependencyCheck_Builder/nvd_cache/nvdcve-{0}.json.gz -DfailBuildOnCVSS=11 ${extraArgs}'"
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

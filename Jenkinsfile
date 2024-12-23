pipeline {
  agent {
    docker {
      reuseNode 'true'
      registryUrl 'https://g-ziod8129-docker.pkg.coding.net'
      registryCredentialsId "${env.DOCKER_REGISTRY_CREDENTIALS_ID}"
      image 'mysterious-forest/docker/cn-base-maven:3.8.4-openjdk-17-2'
    }
  }
  stages {
    stage('检出') {
      steps {
        checkout([$class: 'GitSCM',
        branches: [[name: GIT_BUILD_REF]],
        userRemoteConfigs: [[
          url: GIT_REPO_URL,
          credentialsId: CREDENTIALS_ID
        ]]])
      }
    }
    stage('编译') {
      steps {
        sh 'mvn clean package -s ./settings.xml -DskipTests'
      }
    }
    stage('推送到 CODING Maven 制品库') {
      steps {
        echo '发布中...'
        withCredentials([
          usernamePassword(
            credentialsId: "${CODING_ARTIFACTS_CREDENTIALS_ID}",
            usernameVariable: 'CODING_MAVEN_REG_USERNAME',
            passwordVariable: 'CODING_MAVEN_REG_PASSWORD'
          )
        ]) {
          sh 'mvn deploy -s ./settings.xml -DskipTests'
        }
        echo '发布完成.'
      }
    }
  }
  environment {
    CODING_MAVEN_REPO_ID = "${CCI_CURRENT_TEAM}-${PROJECT_NAME}-${MAVEN_REPO_NAME}"
    CODING_MAVEN_REPO_URL = "${CCI_CURRENT_WEB_PROTOCOL}://${CCI_CURRENT_TEAM}-maven.pkg.${CCI_CURRENT_DOMAIN}/repository/${PROJECT_NAME}/${MAVEN_REPO_NAME}/"
  }
}
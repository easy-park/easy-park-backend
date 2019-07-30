pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        sh 'chmod 777 ./gradlew'
        sh 'rm -f ./src/main/resources/application/*'
        sh 'cp /usr/local/bin/application.properties ./src/main/resources/application.properties'
        sh './gradlew test'
      }
    }
    stage('QA') {
      steps {
        sh './gradlew build'
        sh 'mv ./build/libs/easy-park-backend-0.0.1-SNAPSHOT.jar ./EasyPark.jar'
        sh 'cp -rf ./EasyPark.jar /usr/local/bin/jar'
        sh 'pid=$(jps | grep jar | cut -d \' \' -f 1);kill -9 $pid'

        sh '''JENKINS_NODE_COOKIE=dontKillMe
        nohup java -jar EasyPark.jar > out.log & sleep 20s'''
      }
    }
  }
}
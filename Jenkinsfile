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
        sh 'pid=$(jps | grep jar | cut -d \' \' -f 1)'
        sh '''if [ ! -n $pid ]; then
     kill -9 $pid
    fi'''
        sh '''JENKINS_NODE_COOKIE=dontKillMe
        nohup java -jar EasyPark.jar > out.log & sleep 20s'''
      }
    }
    stage('Deploy') {
          steps {
            sh 'chmod 777 ./EasyPark.jar'
            sh 'scp -i /root/ooclserver_rsa ./EasyPark.jar root@39.98.52.38:/usr/local/bin/EasyPark.jar'
            sh 'ssh -i /root/ooclserver_rsa root@39.98.52.38 "pid=\\$(jps | grep jar | cut -d \' \' -f 1)"'
            sh '''if [ ! -n $pid ]; then
     kill -9 $pid
    fi'''
            sh 'ssh -i /root/ooclserver_rsa root@39.98.52.38 "rm -f /usr/local/bin/application.log"'
            sh 'ssh -i /root/ooclserver_rsa root@39.98.52.38 "cd /usr/local/bin;nohup java -jar EasyPark.jar > application.log &"'
          }
        }
  }
}
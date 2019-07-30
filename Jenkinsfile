pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        sh 'chmod 777 ./gradlew'
        sh 'rm -f ./src/main/resources/application/*'
        sh 'cp /usr/local/bin/prod/application.properties ./src/main/resources/application.properties'
        sh './gradlew test'
      }
    }
    stage('deploy') {
      steps {
        sh './gradlew build'
        sh 'mv ./build/libs/easy-park-backend-0.0.1-SNAPSHOT.jar ./EasyPark.jar'
        sh 'chmod 777 ./EasyPark.jar'
        sh 'scp -i /root/ooclserver_rsa ./EasyPark.jar root@39.98.52.38:/usr/local/bin/'
        sh 'ssh -i /root/ooclserver_rsa root@39.98.52.38 "pid=\\$(jps | grep jar | cut -d \' \' -f 1);kill -9 \\$pid"'
        sh 'ssh -i /root/ooclserver_rsa root@39.98.52.38 "rm -f /usr/local/bin/application.log"'
        sh 'ssh -i /root/ooclserver_rsa root@39.98.52.38 "cd /usr/local/bin;nohup java -jar EasyPark.jar > application.log &"'
      }
    }
  }
}
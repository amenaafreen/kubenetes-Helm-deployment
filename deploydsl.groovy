String basePath = 'jobfolder1'
String gitRepository = 'amenaafreen/kubernetesdeployment'

folder(basePath) {
    description('Folder containing all jobs for folder')
}
   mavenJob("$basePath/maven_job") {
   description('Build the  Project: ' + gitRepository)
    scm {
        github(gitRepository, 'master')
    }
   triggers {
     githubPush()
    }

   goals('clean package')
   publishers {
       //archive the war file generated
       archiveArtifacts 'target/*.war'
}
     postBuildSteps{

     shell ("""kubectl apply -f webdeploy.yml &&\
               kubectl apply -f mysqldeployment.yml &&\
               kubectl get deploy &&\
               kubectl get pods &&\
               kubectl get svc &&\
               sleep 10s &&\
               echo "APP URL" &&\
               curl -Is http://localhost:30003/LoginWebApp/""")
       
     }
     
     postBuildSteps{
        configure{ project ->
    project/publishers << 'org.jfrog.hudson.ArtifactoryRedeployPublisher' {
    details {
      artifactoryUrl('http://localhost:8081/artifactory')
      artifactoryName('Artifactory Version 4.15.0')
      repositoryKey('Jenkins-Integration1')
      snapshotsRepositoryKey('Jenkins-Snapshot1')
    }
    deployBuildInfo(true)
    deployArtifacts(true)
    evenIfUnstable(false)
      }
   }
}
}

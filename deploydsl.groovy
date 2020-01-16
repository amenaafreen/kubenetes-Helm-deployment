String basePath = 'helm'
String gitRepository = 'amenaafreen/kubenetes-Helm-deployment'

folder(basePath) {
    description('Folder containing all jobs for folder')
}
   mavenJob("$basePath/deploy_job") {
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

     shell ("""helm install webapp &&\
               kubectl get deploy -l app=webapp &&\
               kubectl get pods -l app=webapp &&\
               kubectl get svc -l app=webapp &&\
               kubectl get configmaps &&\
               sleep 10s &&\
               echo "APP URL" &&\
               curl -Is http://localhost:30008/LoginWebApp/""")
       
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

def deploybleBranches = ["develop", "release", "master"]
def dockerData = [:]
def imageTag = ""

def isDeployableBranch = {
  deploybleBranches.contains(env.BRANCH_NAME)
}

def getDockerData = {
  def version = ""
  def namespace = ""
  def prefix = ""
  def pom = readMavenPom file: 'cidr-api/pom.xml'
  switch(env.BRANCH_NAME) {
    case "develop": // develop
      version = "${pom.version}"
      prefix = "alpha"
      namespace = "development"
      break
    case "release": // staging
      version = "betha-${pom.version}"
      prefix = "betha"
      namespace = "staging"
      break
    case "master": // production
      version = "${pom.version}"
      prefix = ""
      namespace = "production"
      break
    default:
      break
  }

  [version: version, namespace: namespace, prefix: prefix]
}

env.BRANCH_NAME = "develop" // harcoded to test the deployment

node {

  stage "Checkout"
  scm

  stage "Test"
  dir('cidr_convert_api/java') {
    sh 'docker run -v $(pwd)/cidr-api:/usr/local/cidr-api -w /usr/local/cidr-api maven:3-jdk-8-alpine mvn test'
  }

  stage "Build"
  dir('cidr_convert_api/java') {
    dockerData = getDockerData()
    imageTag = "${dockerData.prefix}-${dockerData.version}"
    sh "docker build -t cidr_convert:${imageTag} ."
  }

  if( isDeployableBranch() ) {
    stage "Push"
    sh "docker tag cidr_convert:${imageTag} wizelinedevops/cidr_convert:${imageTag}"
    sh "docker tag cidr_convert:${imageTag} wizelinedevops/cidr_convert:latest"
    sh "docker push wizelinedevops/cidr_convert:${imageTag}"
    sh "docker push wizelinedevops/cidr_convert:latest"

    stage "Deploy"
    withCredentials([file(credentialsId: 'k8s', variable: 'KUBECONFIG')]) {
      sh "kubectl --kubeconfig=${env.KUBECONFIG} --namespace=${dockerData.namespace} set image deployment/api api=wizelinedevops/cidr_convert:${dockerData.prefix}-latest"
    }
  }
}

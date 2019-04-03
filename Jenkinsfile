def deploybleBranches = ["develop", "release", "master"]
def dockerData = [:]

def isDeployableBranch = {
  deploybleBranches.find { env.BRANCH_NAME.contains(it) }
}

def getDockerData = {
  def version = ""
  def namespace = ""
  def pom = readMavenPom file: 'cidr-api/pom.xml'
  switch(env.BRANCH_NAME) {
    case "develop": // develop
      version = "alpha-${pom.version}"
      namespace = "development"
      break
    case ~/release(.*)/: // staging
      version = "betha-${pom.version}"
      namespace = "staging"
      break
    case "master": // production
      version = "${pom.version}"
      namespace = "production"
      break
    default:
      def branchName = env.BRANCH_NAME.replaceAll("/", "_")
      version = "${branchName}-${env.BUILD_NUMBER}"
      break
  }

  [version: version, namespace: namespace]
}

node {
<<<<<<< HEAD
=======
  withCredentials([string(credentialsId: 'webhookTokenWizeline', variable: 'webhookTokenWizeline')]) {
    properties([
      pipelineTriggers([
       [$class: 'GenericTrigger',
        genericVariables: [
         [key: 'ref', value: '$.ref'],
         [
          key: 'before',
          value: '$.before',
          expressionType: 'JSONPath', //Optional, defaults to JSONPath
          regexpFilter: '', //Optional, defaults to empty string
          defaultValue: '' //Optional, defaults to empty string
         ]
        ],
        causeString: 'Triggered on $ref',
        token: webhookTokenWizeline,
        printContributedVariables: true,
        printPostContent: true,
        silentResponse: false,
        regexpFilterText: '$ref',
        regexpFilterExpression: 'refs/heads/' + BRANCH_NAME
       ]
      ])
    ])
  }

>>>>>>> feature/jenkinsjob
  stage("Checkout") {
    checkout scm
  }

  stage("Test") {
    dir('cidr_convert_api/java') {
      sh 'docker run -v $(pwd)/cidr-api:/usr/local/cidr-api -w /usr/local/cidr-api maven:3-jdk-8-alpine mvn test'
    }
  }

  stage("Build") {
    dir('cidr_convert_api/java') {
      dockerData = getDockerData()
      sh "docker build -t cidr_convert:${dockerData.version} ."
    }
  }

  if( isDeployableBranch() ) {
    stage("Push") {
      sh "docker tag cidr_convert:${dockerData.version} wizelinedevops/cidr_convert:${dockerData.version}"
      sh "docker push wizelinedevops/cidr_convert:${dockerData.version}"
    }

    stage("Deploy") {
      withCredentials([file(credentialsId: 'k8s', variable: 'KUBECONFIG')]) {
        sh "kubectl --kubeconfig=${env.KUBECONFIG} --namespace=${dockerData.namespace} set image deployment/api api=wizelinedevops/cidr_convert:${dockerData.version}"
      }
    }
  }
}

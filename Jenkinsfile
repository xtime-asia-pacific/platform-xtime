#!groovy
node {

    // Start build stage.
    stage 'build'

    // Configure JAVA to use.
    // JAVA versions are configured under
    // manage_jenkins->global_tool_configuration->JDK
    // Possible values for JDK are
    // Java 6 -> 'jdk6'
    // Java 7 -> 'jdk7'
    // Java 8 -> 'jdk8'

    env.JAVA_HOME= "${tool 'jdk8'}"


    // Display the version of JDK used
    echo "Using JAVA_HOME: ${env.JAVA_HOME}"

    // Maven version is configured in Jenkins
    // manage_jenkins->global_tool_configuration->Maven
    env.PATH= "${tool 'Maven 3'}/bin:${env.PATH}"

    // Check source code.
    checkout scm


    // Verify the pom version and the svn branch name
    // match. For eg: if the svn branch name is
    // xt-7.29, pom version is  expected to be
    // 7.29-SNAPSHOT.
    def build = false
    def v = version()
    echo "Version : ${v}"
    def branches =  env.BRANCH_NAME.split('-')
    for (int i=0; i < branches.size(); i++){
        def branch=branches[i]+"-SNAPSHOT"
        if (branch == v)
            build = true
    }
    if (build){

        // Everything matches. Lets build now.
        // Note. We run the tests too.
        //
        //
        // One does not simply skip maven test
        // and expect QA to catch all bugs
        //   -- Boromir
        //
        //
        echo "Building version ${v}"
        sh 'mvn clean deploy'
    }
    else{
        echo "POM version ${v} is different than SVN branch version : ${env.BRANCH_NAME}"
        currentBuild.result = 'FAILURE'
    }
}

def version() {

    // Jenkinsfile does not support returning output from a shell command.
    // This is a feature request JENKINS-26133
    // If the feature is implemented, fix this code.
    // - May 16 2016
    // sh "xmllint --xpath string(/*[local-name()='project']/*[local-name()='version']) pom.xml > v.txt"


    // CentOS 6 does not seem to have proper version of xmllint
    // sh "cat pom.xml | xmllint --format - | sed '2 s/xmlns=\".*\"//g' | xmllint --xpath 'string(/project/version)' - > v.txt "

    sh "echo \"cat /*[local-name()='project']/*[local-name()='version']\" | xmllint --shell pom.xml > v.txt"



    // This does not work because of Script Security plugin that restricts
    // access to groovy.util.Node.
    // This is most likely a bug in Pipeline plugin.
    // Once this is fixed we can use the following way to parse pom.xml and retrieve
    // version.
    
    //  def project = new groovy.util.XmlParser(false, false,false).parseText(readFile('pom.xml'))
    //  project.version.text()


     def matcher = readFile('v.txt') =~ '<version>(.+)</version>'
     matcher ? matcher[0][1] : null
}

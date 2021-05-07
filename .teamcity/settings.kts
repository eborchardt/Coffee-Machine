import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.azureDevopsConnection
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.bitbucketCloudConnection
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"

project {

    vcsRoot(Bitbucketcloudhttps)
    vcsRoot(AzureCoffeeMachine)
    vcsRoot(HttpsGithubComEborchardtDockerComposeRefsHeadsMain)
    vcsRoot(GitBitbucketOrgEborchardtNomasterGit)
    vcsRoot(HttpsGithubComJetBrainsTeamcityDockerSamplesGitRefsHeadsMaster)
    vcsRoot(BitbucketCloudSsh)
    vcsRoot(HttpsGithubComEborchardtHelloWorld)

    buildType(ManualDeploy)
    buildType(Dockercompose)
    buildType(PullRequestTest)
    buildType(SetVersion)
    buildType(BuildStreamjockey)
    buildType(NoSources)
    buildType(Build1)
    buildType(RestParameterChanging)
    buildType(MvnDeploy)
    buildType(Build)

    template(LocalDockerSupport)

    features {
        azureDevopsConnection {
            id = "PROJECT_EXT_13"
            displayName = "Azure DevOps"
            serverUrl = "https://dev.azure.com/eborchardt/"
            accessToken = "credentialsJSON:9399bc85-a065-4f7a-9926-e5a206f08856"
        }
        dockerRegistry {
            id = "PROJECT_EXT_20"
            name = "Docker Registry"
            url = "https://docker.io"
            userName = "xxandorra"
            password = "credentialsJSON:fdbfb5c9-8963-4e21-9241-93e77e0ad70a"
        }
        feature {
            id = "PROJECT_EXT_21"
            type = "active_storage"
            param("active.storage.feature.id", "PROJECT_EXT_31")
        }
        dockerRegistry {
            id = "PROJECT_EXT_30"
            name = "Local Docker Registry"
            url = "http://192.168.0.63:5000"
            userName = "eborchardt"
            password = "credentialsJSON:a253a8db-e3ec-40dd-bc0b-3c6f41a3215e"
        }
        feature {
            id = "PROJECT_EXT_31"
            type = "storage_settings"
            param("aws.service.endpoint", "")
            param("secure:aws.secret.access.key", "credentialsJSON:146a250e-f0b1-4904-9299-43d35fd784db")
            param("aws.external.id", "TeamCity-server-9eec4a2a-59ee-45ac-93f3-917eac33b497")
            param("aws.environment", "")
            param("storage.name", "aws")
            param("storage.s3.bucket.name", "asimpletest")
            param("storage.type", "S3_storage")
            param("storage.s3.forceVirtualHostAddressing", "true")
            param("aws.access.key.id", "AKIAVAXHSCOWRECRFI6W")
            param("aws.credentials.type", "aws.access.keys")
            param("aws.region.name", "us-east-2")
            param("storage.s3.upload.presignedUrl.enabled", "true")
        }
        feature {
            id = "PROJECT_EXT_32"
            type = "project-graphs"
            param("series", """
                [
                  {
                    "type": "valueType",
                    "title": "Success Rate",
                    "sourceBuildTypeId": "CoffeeMachine_Build",
                    "key": "SuccessRate"
                  }
                ]
            """.trimIndent())
            param("format", "percentBy1")
            param("hideFilters", "")
            param("title", "New chart title")
            param("defaultFilters", "")
            param("seriesTitle", "Serie")
        }
        feature {
            id = "PROJECT_EXT_33"
            type = "buildtype-graphs"
            param("series", """
                [
                  {
                    "type": "valueType",
                    "title": "Starting Build on Agent",
                    "key": "buildStageDuration:firstStepPreparation"
                  }
                ]
            """.trimIndent())
            param("format", "duration")
            param("hideFilters", "")
            param("title", "Chart title")
            param("defaultFilters", "")
            param("seriesTitle", "Serie")
        }
        bitbucketCloudConnection {
            id = "PROJECT_EXT_37"
            displayName = "Bitbucket Cloud"
            key = "wU9Vj6tNkrf36KvzHu"
            clientSecret = "credentialsJSON:3706a51e-3626-4d5a-b5db-6b139b281536"
        }
    }

    subProject(Subproject2)
    subProject(Subproject)
}

object Build : BuildType({
    templates(LocalDockerSupport)
    name = "Build"

    steps {
        script {
            id = "RUNNER_1"
            enabled = false
            scriptContent = """echo "Hi""""
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            id = "RUNNER_2"
            enabled = false
            scriptContent = "echo ##teamcity[setParameter name='secretpassword' value='youcantseethis' type='password']"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        dockerCommand {
            id = "RUNNER_125"
            commandType = build {
                source = content {
                    content = """
                        FROM ubuntu
                        
                        RUN apt update && apt upgrade -y
                        RUN apt install figlet -y
                        RUN rm -rf /var/lib/apt/lists/*
                    """.trimIndent()
                }
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            id = "RUNNER_126"
            commandType = push {
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
            }
        }
        script {
            id = "RUNNER_136"
            scriptContent = "http://192.168.0.22/httpAuth/downloadBuildLog.html?buildId=%teamcity.build.id%"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    features {
        dockerSupport {
            id = "BUILD_EXT_1"
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_30,PROJECT_EXT_20"
            }
        }
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT", "RQ_1")
    }
    
    disableSettings("RQ_1")
})

object Build1 : BuildType({
    name = "Build (1)"

    vcs {
        root(HttpsGithubComJetBrainsTeamcityDockerSamplesGitRefsHeadsMaster, "+:compose-ubuntu => .")
    }

    steps {
        dockerCompose {
            file = "docker-compose.yml"
        }
    }

    triggers {
        vcs {
        }
    }
})

object BuildStreamjockey : BuildType({
    templates(LocalDockerSupport)
    name = "build streamjockey"

    params {
        param("PerforceStream", "")
    }

    steps {
        dockerCommand {
            id = "RUNNER_1"
            commandType = build {
                source = content {
                    content = """
                        FROM ubuntu
                        
                        RUN apt update && apt upgrade -y
                        RUN apt install -y figlet
                        RUN rm -rf /var/lib/apt/lists/*
                    """.trimIndent()
                }
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            id = "RUNNER_124"
            commandType = push {
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
            }
        }
    }

    features {
        dockerSupport {
            id = "DockerSupport"
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_20"
            }
        }
    }

    dependencies {
        snapshot(SetVersion) {
            synchronizeRevisions = false
        }
    }
})

object Dockercompose : BuildType({
    name = "dockercompose"

    artifactRules = "test.txt"

    vcs {
        root(HttpsGithubComEborchardtDockerComposeRefsHeadsMain)
    }

    steps {
        dockerCompose {
            file = "docker-compose.yml"
        }
    }
})

object ManualDeploy : BuildType({
    name = "manual deploy"

    params {
        param("JavaVersion", "java-1.8.0-amazon-corretto-devel-1-SNAPSHOT")
        text("reverse.dep.*.PerforceStream", "java-1.8.0-amazon-corretto-devel-1-SNAPSHOT", label = "Set Build Version PerforceStream", description = "Enter a Stream Name", display = ParameterDisplay.PROMPT, allowEmpty = false)
    }

    steps {
        script {
            name = "Convert Characters to Lowercase"
            scriptContent = """
                javaversion=${'$'}(echo %JavaVersion% | tr '[:upper:]' '[:lower:]')
                echo "##teamcity[setParameter name='JavaVersion' value='${'$'}javaversion']"
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            scriptContent = "echo %JavaVersion%"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    dependencies {
        snapshot(BuildStreamjockey) {
            synchronizeRevisions = false
        }
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT")
    }
})

object MvnDeploy : BuildType({
    name = "mvn deploy"
})

object NoSources : BuildType({
    name = "NoSources"

    artifactRules = "banana/test.txt"

    steps {
        script {
            scriptContent = """
                mkdir banana
                echo "blah blah blah" >> banana/test.txt
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            scriptMode = script {
                content = """
                    warning = write-warning "This is a warning. You have been warned."
                    "${'$'}([char]27)[${'$'}('1;35')m${'$'}warning${'$'}([char]27)[0m"
                    
                    
                    #"${'$'}([char]27)[${'$'}('1;35')m${'$'}{Text}${'$'}([char]27)[0m"
                """.trimIndent()
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    features {
        swabra {
            enabled = false
            verbose = true
        }
    }
})

object PullRequestTest : BuildType({
    name = "PullRequestTest"

    vcs {
        root(Bitbucketcloudhttps)
    }

    steps {
        script {
            scriptContent = "echo %build.counter%"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        pullRequests {
            vcsRootExtId = "${Bitbucketcloudhttps.id}"
            provider = bitbucketCloud {
                authType = vcsRoot()
                filterTargetBranch = "+:refs/heads/master"
            }
        }
    }
})

object RestParameterChanging : BuildType({
    name = "REST parameter changing"

    params {
        param("someparam", "12345")
    }

    vcs {
        root(HttpsGithubComEborchardtHelloWorld)
    }

    steps {
        script {
            scriptContent = """
                echo value: %someparam%
                echo ##teamcity[setParameter name='someparam' value='67890']
                echo value: %someparam%
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            name = "New build step"
            scriptContent = """
                echo value: %someparam%
                echo ##teamcity[setParameter name='someparam' value='12345']
                echo value: %someparam%
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            name = "New build step (1)"

            conditions {
                equals("teamcity.build.branch.is_default", "true")
            }
            scriptContent = """
                echo value: %someparam%
                echo ##teamcity[setParameter name='someparam' value='67890']
                echo value: %someparam%
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }
})

object SetVersion : BuildType({
    name = "set version"

    params {
        param("PerforceStream", "")
    }

    steps {
        script {
            scriptContent = "echo %PerforceStream%"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }
})

object LocalDockerSupport : Template({
    name = "LocalDockerSupport"

    artifactRules = "%artifactdir%"
    buildNumberPattern = "14.7.%build.counter%"

    params {
        param("teamcity.vcsTrigger.mergedBranchesThreshold", "3")
        checkbox("Confirmation", "false", label = "Confirmation", display = ParameterDisplay.PROMPT,
                  checked = "true", unchecked = "false")
        text("ConfirmationCheck", "%Confirmation%", display = ParameterDisplay.HIDDEN,
              regex = "^true${'$'}", validationMessage = "Confirmation Required")
        param("teamcity.vcsTrigger.analyzeFullHistoryForMergeCommits", "false")
        param("teamcity.vcsTrigger.analyzeAllPendingChangesOnMergeCommit", "false")
        param("artifactdir", "Home => test.zip")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    triggers {
        vcs {
            id = "TRIGGER_1"
            triggerRules = "+:/Home/"
            branchFilter = ""
            enableQueueOptimization = false
        }
    }

    features {
        dockerSupport {
            id = "BUILD_EXT_1"
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_30"
            }
        }
    }
})

object AzureCoffeeMachine : GitVcsRoot({
    name = "AzureCoffeeMachine"
    url = "https://eborchardt@dev.azure.com/eborchardt/Coffee-Machine/_git/Coffee-Machine"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "administrator"
        password = "credentialsJSON:9399bc85-a065-4f7a-9926-e5a206f08856"
    }
})

object BitbucketCloudSsh : GitVcsRoot({
    name = "bitbucket cloud ssh"
    url = "git@bitbucket.org:eborchardt/nomaster.git"
    branch = "notmaster"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "bitbucket"
    }
})

object Bitbucketcloudhttps : GitVcsRoot({
    name = "bitbucketcloudhttps"
    url = "https://eborchardt@bitbucket.org/eborchardt/nomaster.git"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "eborchardt"
        password = "credentialsJSON:6c2aafc1-51b7-49ba-8b40-4902322c8350"
    }
    param("oauthProviderId", "PROJECT_EXT_37")
})

object GitBitbucketOrgEborchardtNomasterGit : GitVcsRoot({
    name = "git@bitbucket.org:eborchardt/nomaster.git"
    url = "git@bitbucket.org:eborchardt/nomaster.git"
    branch = "not-master"
})

object HttpsGithubComEborchardtDockerComposeRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/eborchardt/docker-compose#refs/heads/main"
    url = "https://github.com/eborchardt/docker-compose"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "eborchardt"
        password = "credentialsJSON:900946fa-7463-4a61-ba9d-8f50926bfc30"
    }
})

object HttpsGithubComEborchardtHelloWorld : GitVcsRoot({
    name = "https://github.com/eborchardt/hello-world"
    url = "https://github.com/eborchardt/hello-world"
    branch = "refs/heads/master"
    branchSpec = """
        refs/heads/feature-*
        +:refs/heads/dev
    """.trimIndent()
    userNameStyle = GitVcsRoot.UserNameStyle.FULL
    authMethod = password {
        userName = "eborchardt"
        password = "credentialsJSON:a45b70d2-7e67-46ef-9713-4ccb0e20487e"
    }
})

object HttpsGithubComJetBrainsTeamcityDockerSamplesGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/JetBrains/teamcity-docker-samples.git#refs/heads/master"
    url = "https://github.com/JetBrains/teamcity-docker-samples.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
})


object Subproject : Project({
    name = "subproject"

    buildType(Subproject_Blah)
})

object Subproject_Blah : BuildType({
    name = "blah"

    steps {
        script {
            scriptContent = """echo "hello""""
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }
})


object Subproject2 : Project({
    name = "subproject2"
})

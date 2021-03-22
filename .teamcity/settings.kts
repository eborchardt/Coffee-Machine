import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.azureDevopsConnection
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

    vcsRoot(AzureCoffeeMachine)
    vcsRoot(GitBitbucketOrgEborchardtNomasterGit)
    vcsRoot(BitbucketCloudSsh)

    buildType(ManualDeploy)
    buildType(PullRequestTest)
    buildType(SetVersion)
    buildType(BuildStreamjockey)
    buildType(MvnDeploy)
    buildType(Build)

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
    }

    subProject(Subproject)
}

object Build : BuildType({
    name = "Build"

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

    steps {
        script {
            enabled = false
            scriptContent = """echo "Hi""""
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            enabled = false
            scriptContent = "echo ##teamcity[setParameter name='secretpassword' value='youcantseethis' type='password']"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    triggers {
        vcs {
            triggerRules = "+:/Home/"
            branchFilter = ""
            enableQueueOptimization = false
        }
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT", "RQ_1")
    }
    
    disableSettings("RQ_1")
})

object BuildStreamjockey : BuildType({
    name = "build streamjockey"

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

    dependencies {
        snapshot(SetVersion) {
            synchronizeRevisions = false
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

object PullRequestTest : BuildType({
    name = "PullRequestTest"

    vcs {
        root(BitbucketCloudSsh)
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
            triggerRules = "+:/Home/"
            branchFilter = "+:pull/*"
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
    authMethod = uploadedKey {
        uploadedKey = "bitbucket"
    }
})

object GitBitbucketOrgEborchardtNomasterGit : GitVcsRoot({
    name = "git@bitbucket.org:eborchardt/nomaster.git"
    url = "git@bitbucket.org:eborchardt/nomaster.git"
    branch = "not-master"
})


object Subproject : Project({
    name = "subproject"
})

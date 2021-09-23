package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

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

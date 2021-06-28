package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object NoSources : BuildType({
    name = "NoSources"

    artifactRules = """
        banana/test.txt
        index.xml
    """.trimIndent()

    params {
        param("env.version", "%env.major.minor.version%")
        param("env.major.minor.version", "%env.major.version%.0")
        text("env.major.version", "", display = ParameterDisplay.PROMPT, allowEmpty = false)
    }

    steps {
        script {
            scriptContent = """
                mkdir banana
                echo "blah blah blah" > index.txt
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            scriptMode = script {
                content = """Write-Host "This is a warning. You have been warned." -ForegroundColor DarkYellow"""
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

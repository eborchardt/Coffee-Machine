package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

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

    requirements {
        exists("system.cloud.profile_id")
    }
})

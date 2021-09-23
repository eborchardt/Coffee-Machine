package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.investigationsAutoAssigner
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object NotificationTests : BuildType({
    name = "Notification Tests"

    steps {
        script {
            scriptContent = "exit 1"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    features {
        investigationsAutoAssigner {
            defaultAssignee = "administrator"
        }
    }
})

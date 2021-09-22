package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

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

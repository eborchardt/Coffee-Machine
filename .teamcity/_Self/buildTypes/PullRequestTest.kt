package _Self.buildTypes

import _Self.vcsRoots.Bitbucketcloudhttps
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object PullRequestTest : BuildType({
    name = "PullRequestTest"

    vcs {
        root(_Self.vcsRoots.Bitbucketcloudhttps)
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

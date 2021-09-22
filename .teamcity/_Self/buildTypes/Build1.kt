package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Build1 : BuildType({
    name = "Build (1)"

    vcs {
        root(_Self.vcsRoots.HttpsGithubComJetBrainsTeamcityDockerSamplesGitRefsHeadsMaster, "+:compose-ubuntu => .", "+:banana")
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

package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCompose

object Dockercompose : BuildType({
    name = "dockercompose"

    artifactRules = "test.txt"

    vcs {
        root(_Self.vcsRoots.HttpsGithubComEborchardtDockerComposeRefsHeadsMain)
    }

    steps {
        dockerCompose {
            file = "docker-compose.yml"
        }
    }
})

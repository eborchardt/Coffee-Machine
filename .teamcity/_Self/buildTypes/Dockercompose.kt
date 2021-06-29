package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCompose

object Dockercompose : BuildType({
    name = "dockercompose"

    artifactRules = "test.txt"

    params {
        param("system.teamcity.agent.ensure.free.temp.space", "90gb")
    }

    vcs {
        root(_Self.vcsRoots.HttpsGithubComEborchardtDockerComposeRefsHeadsMain)
    }

    steps {
        dockerCompose {
            file = "docker-compose.yml"
        }
    }
})

package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.investigationsAutoAssigner
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

object BuildStreamjockey : BuildType({
    templates(LocalDockerSupport)
    name = "build streamjockey"

    params {
        param("PerforceStream", "")
        param("teamcity.internal.investigationsAutoAssigner.assignResolveManually", "true")
    }

    steps {
        dockerCommand {
            id = "RUNNER_1"
            enabled = false
            commandType = build {
                source = content {
                    content = """
                        FROM ubuntu
                        
                        RUN apt update && apt upgrade -y
                        RUN apt install -y figlet
                        RUN rm -rf /var/lib/apt/lists/*
                    """.trimIndent()
                }
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            id = "RUNNER_124"
            enabled = false
            commandType = push {
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
            }
        }
    }

    features {
        dockerSupport {
            id = "DockerSupport"
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_20"
            }
        }
        investigationsAutoAssigner {
            id = "InvestigationsAutoAssigner"
            defaultAssignee = "administrator"
        }
    }

    dependencies {
        snapshot(SetVersion) {
            synchronizeRevisions = false
        }
    }
})

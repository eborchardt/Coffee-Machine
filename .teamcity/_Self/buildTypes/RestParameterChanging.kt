package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object RestParameterChanging : BuildType({
    name = "REST parameter changing"

    params {
        param("someparam", "12345")
    }

    vcs {
        root(_Self.vcsRoots.HttpsGithubComEborchardtHelloWorld)
    }

    steps {
        script {
            scriptContent = """
                echo value: %someparam%
                echo ##teamcity[setParameter name='someparam' value='67890']
                echo value: %someparam%
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            name = "New build step"
            scriptContent = """
                echo value: %someparam%
                echo ##teamcity[setParameter name='someparam' value='12345']
                echo value: %someparam%
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            name = "New build step (1)"

            conditions {
                equals("teamcity.build.branch.is_default", "true")
            }
            scriptContent = """
                echo value: %someparam%
                echo ##teamcity[setParameter name='someparam' value='67890']
                echo value: %someparam%
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }
})

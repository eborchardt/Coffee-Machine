package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.PythonBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.python
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Teststest : BuildType({
    name = "teststest"

    artifactRules = "test.txt"

    steps {
        script {
            scriptContent = "echo > test.txt"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        python {
            command = script {
                content = """
                    import sys
                    
                    sys.stdout.write(
                    "##teamcity[testStarted name='test1']\n"
                    )
                    sys.stdout.flush()
                    sys.stdout.write(
                    "##teamcity[testFailed name='test1' message='Check log']\n"
                    )
                    sys.stdout.flush()
                    sys.stdout.write(
                    "##teamcity[testMetadata testName='test1' type='artifact' value='test.txt']\n"
                    )
                    sys.stdout.flush()
                    
                    sys.stdout.write("##teamcity[testFinished name='test1']\n")
                    sys.stdout.flush()
                """.trimIndent()
            }
            dockerImagePlatform = PythonBuildStep.ImagePlatform.Linux
            dockerImage = "python"
        }
    }

    failureConditions {
        testFailure = false
    }
})

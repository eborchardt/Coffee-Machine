package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.python
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule

object Build : BuildType({
    templates(LocalDockerSupport)
    name = "Build"

    params {
        password("test", "******")
    }

    steps {
        script {
            id = "RUNNER_1"
            enabled = false
            scriptContent = """echo "Hi""""
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        script {
            id = "RUNNER_2"
            enabled = false
            scriptContent = "echo ##teamcity[setParameter name='secretpassword' value='youcantseethis' type='password']"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        dockerCommand {
            id = "RUNNER_125"
            enabled = false
            commandType = build {
                source = content {
                    content = """
                        FROM ubuntu
                        
                        RUN apt update && apt upgrade -y
                        RUN apt install figlet -y
                        RUN rm -rf /var/lib/apt/lists/*
                    """.trimIndent()
                }
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            id = "RUNNER_126"
            enabled = false
            commandType = push {
                namesAndTags = "192.168.0.63:5000/figlet:ubuntu"
            }
        }
        script {
            id = "RUNNER_136"
            enabled = false
            scriptContent = "http://192.168.0.22/httpAuth/downloadBuildLog.html?buildId=%teamcity.build.id%"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        step {
            id = "RUNNER_156"
            type = "dotnet-tools-inspectcode"
            enabled = false
            param("dotnet-tools-inspectcode.solution", ".about-version.sln")
            param("jetbrains.resharper-clt.platform", "x64")
            param("jetbrains.resharper-clt.clt-path", "%teamcity.tool.jetbrains.resharper-clt.2021.2.0%")
        }
        step {
            id = "RUNNER_159"
            type = "python"
            param("python-script-code", """print("%test%")""")
            param("python-exe", "%AnyPython%")
        }
        python {
            id = "RUNNER_162"
            pythonVersion = customPython {
                executable = """C:\Program Files\Python38\python.exe"""
            }
            command = script {
                content = """print("%test%")"""
            }
        }
        step {
            id = "RUNNER_174"
            type = "MRPP_xunit_dotcover"
            param("xUnitNet.dotCover.reportType", "HTML")
        }
    }
    triggers {
        schedule {
            id = "TRIGGER_1"
            schedulingPolicy = cron {
                hours = "0"
                dayOfWeek = "1-6"
            }
            branchFilter = "+:master"
            triggerBuild = always()
            withPendingChangesOnly = false
            enforceCleanCheckout = true
            enforceCleanCheckoutForDependencies = true
            param("revisionRuleBuildBranch", "<default>")
            param("hour", "0")
        }
/*
        schedule {
            id = "TRIGGER_11"
            branchFilter = ""
            triggerBuild = always()
        
            enforceCleanCheckout = true
            enforceCleanCheckoutForDependencies = true
        }
*/
    }
    features {
        dockerSupport {
            id = "BUILD_EXT_1"
            enabled = false
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_30,PROJECT_EXT_20"
            }
        }
    }

    requirements {
        doesNotEqual("env.OS", "Windows_NT", "RQ_1")
    }

    disableSettings("RQ_1")
})

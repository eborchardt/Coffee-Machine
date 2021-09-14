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
        password("test", "credentialsJSON:0ace3dd3-b4ff-4aab-997a-d426639a473b")
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
            enabled = false
            executionMode = BuildStep.ExecutionMode.DEFAULT
            param("xUnitNet.dotCover.reportType", "HTML")
            param("xUnitNet.executable.legacymode", "false")
            param("xUnitNet.dotCover.reportOutputFile", "dotCoverReport.html")
            param("xUnitNet.assembliesExcludePath", "")
            param("xUnitNet.dotCover.AttributeFilters", """
                <!-- Attribute filters. It's possible to use asterisks as wildcard symbols. -->
                <AttributeFilters>
                  <AttributeFilterEntry>System.CodeDom.Compiler.GeneratedCodeAttribute</AttributeFilterEntry>
                  <AttributeFilterEntry>System.Diagnostics.CodeAnalysis.ExcludeFromCodeCoverageAttribute</AttributeFilterEntry>
                  <AttributeFilterEntry>System.ObsoleteAttribute</AttributeFilterEntry>
                </AttributeFilters>
            """.trimIndent())
            param("xUnitNet.dotCover.enable", "true")
            param("xUnitNet.nugetExe", "%teamcity.tool.NuGet.CommandLine.DEFAULT%")
            param("xUnitNet.assembliesPath", "*/bin/*tests.dll")
            param("xUnitNet.dotCover.skipProcesses", "")
            param("xUnitNet.dotCover.exportReport", "false")
            param("xUnitNet.notrait", "Category=Integration")
            param("xUnitNet.nugetSource", "http://www.nuget.org/api/v2/")
            param("xUnitNet.trait", "")
            param("xUnitNet.executable", "xunit.console.exe")
            param("xUnitNet.dotCover.Filters", """
                <!-- Coverage filters. It's possible to use asterisks as wildcard symbols. -->
                <Filters>
                  <IncludeFilters>
                    <FilterEntry>
                      <ModuleMask>*</ModuleMask>
                      <ClassMask>*</ClassMask>
                      <FunctionMask>*</FunctionMask>
                    </FilterEntry>
                  </IncludeFilters>
                  <ExcludeFilters>
                    <FilterEntry>
                      <ModuleMask>*Tests</ModuleMask>
                    </FilterEntry>
                  </ExcludeFilters>
                </Filters>
            """.trimIndent())
            param("xUnitNet.executable.args", "")
        }
    }

    triggers {
        schedule {
            id = "TRIGGER_11"
            schedulingPolicy = cron {
            }
            branchFilter = ""
            triggerBuild = always()

            enforceCleanCheckout = true
            enforceCleanCheckoutForDependencies = true
        }
        trigger {
            id = "DelayedFinishedBuildTrigger"
            type = "DelayedFinishedBuildTrigger"
            param("afterSuccessfulBuildOnly", "true")
            param("wait_time", "1")
            param("trigger_configuration", "bt457")
        }
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

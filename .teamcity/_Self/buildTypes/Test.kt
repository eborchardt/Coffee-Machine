package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dotnetNugetPush
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.nuGetPack
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.nuGetPublish

object Test : BuildType({
    name = "test"

    vcs {
        root(_Self.vcsRoots.HttpsGithubComEborchardtHelloWorld, "+:nugetstuff => .")

        cleanCheckout = true
    }

    steps {
        nuGetPack {
            toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
            paths = "test.nuspec"
            version = "1.2.3.%build.number%"
            outputDir = "."
            cleanOutputDir = false
        }
        dotnetNugetPush {
            packages = "*.nupkg"
            serverUrl = "http://localhost/httpAuth/app/nuget/feed/CoffeeMachine/NRZ/v3/index.json"
            apiKey = "credentialsJSON:1b13e6e1-7b5d-4a91-b523-be1bc3bcdc11"
            args = "--skip-duplicate"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
        }
        nuGetPublish {
            enabled = false
            toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
            packages = "*.nupkg"
            serverUrl = "http://localhost/httpAuth/app/nuget/feed/CoffeeMachine/NRZ/v3/"
            apiKey = "credentialsJSON:1b13e6e1-7b5d-4a91-b523-be1bc3bcdc11"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    features {
        dockerSupport {
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_20"
            }
        }
    }
})

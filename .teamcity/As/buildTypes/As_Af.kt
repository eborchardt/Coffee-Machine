package As.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dotnetBuild

object As_Af : BuildType({
    name = "af"

    vcs {
        root(As.vcsRoots.As_HttpsGithubComXunitSamplesXunitGit)
    }

    steps {
        dotnetBuild {
            projects = "AssemblyFixtureExample/AssemblyFixtureExample.csproj"
            framework = "net452"
            configuration = "Debug"
            outputDir = "bin"
            versionSuffix = "%build.number%"
            sdk = "4.5.2"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
        }
        step {
            type = "MRPP_xunit_dotcover"
            param("xUnitNet.dotCover.reportType", "HTML")
            param("xUnitNet.assembliesPath", """bin\*.dll""")
        }
    }
})

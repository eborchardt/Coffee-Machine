package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dotnetBuild
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a buildType with id = 'As_Af'
in the project with id = 'As', and delete the patch script.
*/
create(RelativeId("As"), BuildType({
    id("As_Af")
    name = "af"

    vcs {
        root(RelativeId("As_HttpsGithubComXunitSamplesXunitGit"))
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
            param("xUnitNet.assembliesPath", """
                xunit.runner.console.2.4.1\tools\net452\xunit.console.exe 
                xunit.runner.console.2.4.1\tools\net46\xunit.console.exe 
                xunit.runner.console.2.4.1\tools\net461\xunit.console.exe 
                xunit.runner.console.2.4.1\tools\net462\xunit.console.exe 
                xunit.runner.console.2.4.1\tools\net47\xunit.console.exe 
                xunit.runner.console.2.4.1\tools\net471\xunit.console.exe 
                xunit.runner.console.2.4.1\tools\net472\xunit.console.exe
            """.trimIndent())
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
}))


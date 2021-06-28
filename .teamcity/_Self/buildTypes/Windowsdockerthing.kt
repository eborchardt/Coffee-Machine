package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.powerShell

object Windowsdockerthing : BuildType({
    name = "windowsdockerthing"

    steps {
        powerShell {
            name = "Step 1 Create Temp Directory & Collect TC Install Log"
            scriptMode = script {
                content = """
                    mkdir c:\temp
                    ${'$'}tcagentlog = New-Item -Path C:\temp\tcagent-install.log -ItemType file
                """.trimIndent()
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            name = "Step 3 Downloading Installers From Host to Container"
            scriptMode = script {
                content = """
                    ${'$'}client = New-Object System.Net.WebClient
                    ${'$'}client.DownloadFile("https://nodejs.org/download/release/v12.15.0/node-v12.15.0-x64.msi","C:\temp\node-v12.15.0-x64.msi")
                """.trimIndent()
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            name = "Time for a snooze"
            enabled = false
            scriptMode = script {
                content = "Start-Sleep 120"
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            name = "Step 4 Installing NodeJS"
            scriptMode = script {
                content = """Start-Process MsiExec.exe -wait -ArgumentList '/a "C:\temp\node-v12.15.0-x64.msi" /qn'"""
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            name = "Step 6 Setting dot in ENV Variables Path"
            scriptMode = script {
                content = """
                    #Write-Host "Step 6 Setting dot in ENV Variables Path"
                    #"${'$'}env:path ="${'$'}(${'$'}env:path);."
                    ${'$'}env:Path = '${'$'}env:Path;C:\nodejs'
                    node --version
                """.trimIndent()
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            name = "Step 7 Check node version"
            scriptMode = script {
                content = """
                    #Write-Host "Step 7 Check node version"
                    #& 'C:\Program Files\nodejs\.\node.exe' --version
                    C:\nodejs\node.exe --version
                """.trimIndent()
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
        powerShell {
            name = "Final Step 8 Ping Loop"
            enabled = false
            scriptMode = script {
                content = "Ping -t 127.0.0.1 > ${'$'}null"
            }
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }
})

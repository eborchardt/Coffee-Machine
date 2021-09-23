package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.PerforceVcsRoot

object Asd : PerforceVcsRoot({
    name = "asd"
    port = "127.0.0.1:1492"
    mode = stream {
        streamName = "//streamdepot/A/test"
    }
    workspaceOptions = """
        Options:        noallwrite clobber nocompress unlocked nomodtime rmdir
        Host:           %teamcity.agent.hostname%
        SubmitOptions:  revertunchanged
        LineEnd:        local
    """.trimIndent()
})

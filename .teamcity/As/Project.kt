package As

import As.buildTypes.*
import As.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("As")
    name = "as"

    vcsRoot(As_HttpsGithubComXunitSamplesXunitGit)

    buildType(As_Af)
})

package As.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object As_HttpsGithubComXunitSamplesXunitGit : GitVcsRoot({
    name = "https://github.com/xunit/samples.xunit.git"
    url = "https://github.com/xunit/samples.xunit.git"
    branch = "refs/heads/main"
})

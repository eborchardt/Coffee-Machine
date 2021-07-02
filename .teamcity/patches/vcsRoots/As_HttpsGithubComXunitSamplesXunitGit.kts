package patches.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a vcsRoot with id = 'As_HttpsGithubComXunitSamplesXunitGit'
in the project with id = 'As', and delete the patch script.
*/
create(RelativeId("As"), GitVcsRoot({
    id("As_HttpsGithubComXunitSamplesXunitGit")
    name = "https://github.com/xunit/samples.xunit.git"
    url = "https://github.com/xunit/samples.xunit.git"
    branch = "refs/heads/main"
}))

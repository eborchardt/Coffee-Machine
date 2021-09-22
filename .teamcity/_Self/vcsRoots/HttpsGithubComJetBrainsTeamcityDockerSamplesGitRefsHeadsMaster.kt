package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object HttpsGithubComJetBrainsTeamcityDockerSamplesGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/JetBrains/teamcity-docker-samples.git#refs/heads/master"
    url = "https://github.com/JetBrains/teamcity-docker-samples.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    param("useAlternates", "true")
})

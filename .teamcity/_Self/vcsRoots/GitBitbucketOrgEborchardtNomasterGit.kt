package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object GitBitbucketOrgEborchardtNomasterGit : GitVcsRoot({
    name = "git@bitbucket.org:eborchardt/nomaster.git"
    url = "git@bitbucket.org:eborchardt/nomaster.git"
    branch = "not-master"
    param("useAlternates", "true")
})

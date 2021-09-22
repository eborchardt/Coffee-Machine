package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object BitbucketCloudSsh : GitVcsRoot({
    name = "bitbucket cloud ssh"
    url = "git@bitbucket.org:eborchardt/nomaster.git"
    branch = "notmaster"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "bitbucket"
    }
    param("useAlternates", "true")
})

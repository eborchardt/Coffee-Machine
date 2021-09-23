package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Bitbucketcloudhttps : GitVcsRoot({
    name = "bitbucketcloudhttps"
    url = "https://eborchardt@bitbucket.org/eborchardt/nomaster.git"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "eborchardt"
        password = "credentialsJSON:6c2aafc1-51b7-49ba-8b40-4902322c8350"
    }
    param("useAlternates", "true")
    param("oauthProviderId", "PROJECT_EXT_37")
})

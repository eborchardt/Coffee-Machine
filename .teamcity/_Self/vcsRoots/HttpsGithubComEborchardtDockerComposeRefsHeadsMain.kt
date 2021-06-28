package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object HttpsGithubComEborchardtDockerComposeRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/eborchardt/docker-compose#refs/heads/main"
    url = "https://github.com/eborchardt/docker-compose"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "eborchardt"
        password = "credentialsJSON:900946fa-7463-4a61-ba9d-8f50926bfc30"
    }
    param("useAlternates", "true")
})

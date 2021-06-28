package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object HttpsGithubComEborchardtHelloWorld : GitVcsRoot({
    name = "https://github.com/eborchardt/hello-world"
    url = "https://github.com/eborchardt/hello-world"
    branch = "refs/heads/master"
    branchSpec = """
        refs/heads/feature-*
        +:refs/heads/dev
    """.trimIndent()
    userNameStyle = GitVcsRoot.UserNameStyle.FULL
    authMethod = password {
        userName = "eborchardt"
        password = "credentialsJSON:a45b70d2-7e67-46ef-9713-4ccb0e20487e"
    }
    param("useAlternates", "true")
})

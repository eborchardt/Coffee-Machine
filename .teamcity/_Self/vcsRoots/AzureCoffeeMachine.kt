package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object AzureCoffeeMachine : GitVcsRoot({
    name = "AzureCoffeeMachine"
    url = "https://eborchardt@dev.azure.com/eborchardt/Coffee-Machine/_git/Coffee-Machine"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "administrator"
        password = "credentialsJSON:9399bc85-a065-4f7a-9926-e5a206f08856"
    }
    param("useAlternates", "true")
})

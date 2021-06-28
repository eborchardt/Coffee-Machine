package Subproject

import Subproject.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Subproject")
    name = "subproject"

    buildType(Subproject_Blah)
})

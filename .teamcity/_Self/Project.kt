package _Self

import _Self.buildTypes.*
import _Self.vcsRoots.*
import _Self.buildTypes.NoSources
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.ProjectReportTab
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.azureDevopsConnection
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.bitbucketCloudConnection
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.nuGetFeed
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.projectReportTab

object Project : Project({

    vcsRoot(Bitbucketcloudhttps)
    vcsRoot(HttpsGithubComEborchardtKtssettingsGit)
    vcsRoot(AzureCoffeeMachine)
    vcsRoot(HttpsGithubComEborchardtDockerComposeRefsHeadsMain)
    vcsRoot(Asd)
    vcsRoot(GitBitbucketOrgEborchardtNomasterGit)
    vcsRoot(HttpsGithubComJetBrainsTeamcityDockerSamplesGitRefsHeadsMaster)
    vcsRoot(BitbucketCloudSsh)
    vcsRoot(HttpsGithubComEborchardtHelloWorld)

    buildType(Runhelloworld)
    buildType(Windowsdockerthing)
    buildType(SetVersion)
    buildType(BuildStreamjockey)
    buildType(NotificationTests)
    buildType(NoSources)
    buildType(Build1)
    buildType(RestParameterChanging)
    buildType(MvnDeploy)
    buildType(ManualDeploy)
    buildType(Teststest)
    buildType(Dockercompose)
    buildType(PullRequestTest)
    buildType(Test)
    buildType(Build)

    template(LocalDockerSupport)

    params {
        param("teamcity.internal.kotlinDsl.newProjects.allowUsingNonPortableDSL", "true")
    }

    features {
        azureDevopsConnection {
            id = "PROJECT_EXT_13"
            displayName = "Azure DevOps"
            serverUrl = "https://dev.azure.com/eborchardt/"
            accessToken = "credentialsJSON:9399bc85-a065-4f7a-9926-e5a206f08856"
        }
        dockerRegistry {
            id = "PROJECT_EXT_20"
            name = "Docker Registry"
            url = "https://docker.io"
            userName = "xxandorra"
            password = "credentialsJSON:fdbfb5c9-8963-4e21-9241-93e77e0ad70a"
        }
        feature {
            id = "PROJECT_EXT_21"
            type = "active_storage"
            param("active.storage.feature.id", "PROJECT_EXT_31")
        }
        dockerRegistry {
            id = "PROJECT_EXT_30"
            name = "Local Docker Registry"
            url = "http://192.168.0.63:5000"
            userName = "eborchardt"
            password = "credentialsJSON:a253a8db-e3ec-40dd-bc0b-3c6f41a3215e"
        }
        feature {
            id = "PROJECT_EXT_31"
            type = "storage_settings"
            param("aws.service.endpoint", "")
            param("secure:aws.secret.access.key", "credentialsJSON:146a250e-f0b1-4904-9299-43d35fd784db")
            param("aws.external.id", "TeamCity-server-9eec4a2a-59ee-45ac-93f3-917eac33b497")
            param("aws.environment", "")
            param("storage.name", "aws")
            param("storage.s3.bucket.name", "asimpletest")
            param("storage.type", "S3_storage")
            param("storage.s3.forceVirtualHostAddressing", "true")
            param("aws.access.key.id", "AKIAVAXHSCOWRECRFI6W")
            param("aws.credentials.type", "aws.access.keys")
            param("aws.region.name", "us-east-2")
            param("storage.s3.upload.presignedUrl.enabled", "true")
        }
        feature {
            id = "PROJECT_EXT_32"
            type = "project-graphs"
            param("series", """
                [
                  {
                    "type": "valueType",
                    "title": "Success Rate",
                    "sourceBuildTypeId": "CoffeeMachine_Build",
                    "key": "SuccessRate"
                  }
                ]
            """.trimIndent())
            param("format", "percentBy1")
            param("hideFilters", "")
            param("title", "New chart title")
            param("defaultFilters", "")
            param("seriesTitle", "Serie")
        }
        feature {
            id = "PROJECT_EXT_33"
            type = "buildtype-graphs"
            param("series", """
                [
                  {
                    "type": "valueType",
                    "title": "Starting Build on Agent",
                    "key": "buildStageDuration:firstStepPreparation"
                  }
                ]
            """.trimIndent())
            param("format", "duration")
            param("hideFilters", "")
            param("title", "Chart title")
            param("defaultFilters", "")
            param("seriesTitle", "Serie")
        }
        bitbucketCloudConnection {
            id = "PROJECT_EXT_37"
            displayName = "Bitbucket Cloud"
            key = "wU9Vj6tNkrf36KvzHu"
            clientSecret = "credentialsJSON:3706a51e-3626-4d5a-b5db-6b139b281536"
        }
        feature {
            id = "PROJECT_EXT_38"
            type = "CloudImage"
            param("profileId", "VRDC-1")
            param("agent_pool_id", "-2")
            param("source-id", "blah")
        }
        feature {
            id = "PROJECT_EXT_39"
            type = "JetBrains.SharedResources"
            param("quota", "1")
            param("name", "Resource_with_Quota")
            param("type", "quoted")
        }
        buildReportTab {
            id = "PROJECT_EXT_49"
            title = "BuildReport"
            startPage = "index.txt"
        }
        buildReportTab {
            id = "PROJECT_EXT_49"
            title = "BuildReport"
            startPage = "index.html"
        }        
        feature {
            id = "PROJECT_EXT_7"
            type = "JetBrains.SharedResources"
            param("values", """
                abc123
                def456
                abc123
                def456
            """.trimIndent())
            param("name", "TestOne")
            param("type", "custom")
        }
        feature {
            id = "VRDC-1"
            type = "CloudProfile"
            param("run.var.teamcity.docker.cloud.daemon_info", "")
            param("run.var.teamcity.docker.cloud.server_url", "http://192.168.0.22")
            param("profileServerUrl", "")
            param("run.var.teamcity.docker.cloud.client_uuid", "aefd7693-3fd2-420c-9056-e087d743121a")
            param("system.cloud.profile_id", "VRDC-1")
            param("total-work-time", "")
            param("description", "")
            param("cloud-code", "VRDC")
            param("terminate-after-build", "true")
            param("enabled", "true")
            param("run.var.teamcity.docker.cloud.instance_uri", "tcp://192.168.0.63")
            param("profileId", "VRDC-1")
            param("name", "test1")
            param("next-hour", "")
            param("run.var.teamcity.docker.cloud.tested_image", "{")
            param("run.var.teamcity.docker.cloud.img_param", """[{"Administration":{"Version":4,"RmOnExit":true,"PullOnCreate":true,"MaxInstanceCount":2,"UseOfficialTCAgentImage":true,"Profile":"blah"},"Container":{"Image":"ubuntu18.04","HostConfig":{"OomKillDisable":false,"Privileged":false}},"Editor":{"MemoryUnit":"bytes","MemorySwapUnit":"bytes"}}]""")
            param("terminate-idle-time", "1")
        }
        nuGetFeed {
            id = "repository-nuget-NRZ"
            name = "NRZ"
            description = ""
            indexPackages = true
        }
    }

    subProject(Subproject2.Project)
    subProject(Subproject.Project)
})

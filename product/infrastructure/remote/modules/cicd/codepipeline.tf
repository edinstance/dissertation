resource "aws_codepipeline" "application-pipeline" {
  name     = "${var.environment}-application-pipeline"
  role_arn = var.codepipeline_iam_role_arn

  artifact_store {
    location = var.codepipeline_artifact_bucket
    type     = "S3"
  }

  # Source Stage
  stage {
    name = "Source"

    action {
      name             = "Source"
      category         = "Source"
      owner            = "AWS"
      provider         = "CodeStarSourceConnection"
      version          = "1"
      output_artifacts = ["source_output"]

      configuration = {
        ConnectionArn    = var.codeconnection_arn
        FullRepositoryId = "edinstance/dissertation"
        BranchName       = var.environment == "prod" ? "main" : (var.environment == "dev" ? "Development" : "test")
      }
    }
  }

  stage {
    name = "Infrastructure"

    action {
      name            = "BuildInfrastructure"
      category        = "Build"
      owner           = "AWS"
      provider        = "CodeBuild"
      version         = "1"
      input_artifacts = ["source_output"]

      configuration = {
        ProjectName = aws_codebuild_project.infrastructure_codebuild.name
      }
    }
  }

  # Database Update Stage
  stage {
    name = "Build"

    action {
      name             = "BuildFrontend"
      category         = "Build"
      owner            = "AWS"
      provider         = "CodeBuild"
      version          = "1"
      input_artifacts  = ["source_output"]
      output_artifacts = ["frontend_output"]

      configuration = {
        ProjectName = aws_codebuild_project.frontend_codebuild.name
      }
    }

    action {
      name             = "BuildApolloGateway"
      category         = "Build"
      owner            = "AWS"
      provider         = "CodeBuild"
      version          = "1"
      input_artifacts  = ["source_output"]
      output_artifacts = ["apollo_gateway_output"]

      configuration = {
        ProjectName = aws_codebuild_project.apollo_gateway_codebuild.name
      }
    }

    action {
      name             = "BuildBackend"
      category         = "Build"
      owner            = "AWS"
      provider         = "CodeBuild"
      version          = "1"
      input_artifacts  = ["source_output"]
      output_artifacts = ["backend_output"]

      configuration = {
        ProjectName = aws_codebuild_project.backend_codebuild.name
      }
    }
  }

  # Database Update Stage
  stage {
    name = "Deploy"

    action {
      name            = "UpdateDatabase"
      category        = "Build"
      owner           = "AWS"
      provider        = "CodeBuild"
      version         = "1"
      input_artifacts = ["source_output"]

      configuration = {
        ProjectName = aws_codebuild_project.database_codebuild.name
      }
    }

    action {
      name            = "DeployBackend"
      category        = "Deploy"
      owner           = "AWS"
      provider        = "CodeDeploy"
      version         = "1"
      run_order       = 1
      input_artifacts = ["backend_output"]

      configuration = {
        ApplicationName     = aws_codedeploy_app.backend_codedeploy_application.name
        DeploymentGroupName = aws_codedeploy_deployment_group.backend_codedeploy_deployment_group.deployment_group_name
      }
    }

    action {
      name            = "DeployApolloGateway"
      category        = "Deploy"
      owner           = "AWS"
      provider        = "CodeDeploy"
      version         = "1"
      run_order       = 2
      input_artifacts = ["apollo_gateway_output"]

      configuration = {
        ApplicationName     = aws_codedeploy_app.apollo_gateway_codedeploy_application.name
        DeploymentGroupName = aws_codedeploy_deployment_group.apollo_gateway_codedeploy_deployment_group.deployment_group_name
      }
    }

    action {
      name            = "DeployFrontend"
      category        = "Deploy"
      owner           = "AWS"
      provider        = "CodeDeploy"
      version         = "1"
      run_order       = 3
      input_artifacts = ["frontend_output"]

      configuration = {
        ApplicationName     = aws_codedeploy_app.frontend_codedeploy_application.name
        DeploymentGroupName = aws_codedeploy_deployment_group.frontend_codedeploy_deployment_group.deployment_group_name
      }
    }
  }
}

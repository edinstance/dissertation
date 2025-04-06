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
        BranchName       = "infrastructure-updates"
      }
    }
  }

  # Database Update Stage
  stage {
    name = "Build"

    action {
      name            = "BuildFrontend"
      category        = "Build"
      owner           = "AWS"
      provider        = "CodeBuild"
      version         = "1"
      input_artifacts = ["source_output"]

      configuration = {
        ProjectName = aws_codebuild_project.frontend_codebuild.name
      }
    }

    action {
      name            = "BuildBackend"
      category        = "Build"
      owner           = "AWS"
      provider        = "CodeBuild"
      version         = "1"
      input_artifacts = ["source_output"]

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
  }

}

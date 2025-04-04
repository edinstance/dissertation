resource "aws_codepipeline" "database_pipeline" {
  name     = "database-migration-pipeline"
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
    name = "DatabaseUpdate"

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

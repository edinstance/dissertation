resource "aws_codebuild_project" "frontend_codebuild" {
  name          = "${var.environment}-frontend-codebuild-project"
  description   = "CodeBuild project for the frontend application"
  service_role  = var.codebuild_iam_role_arn
  build_timeout = 30

  source {
    type      = "CODEPIPELINE"
    buildspec = "./product/CICD/aws/frontend.yml"
  }

  environment {
    compute_type                = "BUILD_GENERAL1_SMALL"
    image                       = "aws/codebuild/standard:5.0"
    type                        = "LINUX_CONTAINER"
    image_pull_credentials_type = "CODEBUILD"
    privileged_mode             = true

    # Environment variables
    environment_variable {
      name  = "AWS_ACCOUNT_ID"
      value = var.aws_account_id
    }

    environment_variable {
      name  = "IMAGE_REPO_NAME"
      value = "${var.environment}-frontend-ecr"
    }

    environment_variable {
      name  = "ENVIRONMENT"
      value = var.environment
    }

    environment_variable {
      name  = "SERVICE_TYPE"
      value = "frontend"
    }
  }

  cache {
    type  = "LOCAL"
    modes = ["LOCAL_DOCKER_LAYER_CACHE", "LOCAL_SOURCE_CACHE"]
  }

  artifacts {
    type = "CODEPIPELINE"
  }

  logs_config {
    cloudwatch_logs {
      group_name  = "${var.environment}-codebuild-logs"
      stream_name = "${var.environment}-frontend-codebuild-project"
    }
  }
}

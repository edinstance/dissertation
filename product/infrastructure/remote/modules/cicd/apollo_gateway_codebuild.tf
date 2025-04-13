resource "aws_codebuild_project" "apollo_gateway_codebuild" {
  name          = "${var.environment}-apollo-gateway-codebuild-project"
  description   = "CodeBuild project for the apollo gateway"
  service_role  = var.codebuild_iam_role_arn
  build_timeout = 30

  source {
    type      = "CODEPIPELINE"
    buildspec = "./product/CICD/aws/apollo-gateway.yml"
  }

  environment {
    compute_type                = "BUILD_GENERAL1_SMALL"
    image                       = "aws/codebuild/amazonlinux-aarch64-standard:3.0"
    type                        = "ARM_CONTAINER"
    image_pull_credentials_type = "CODEBUILD"
    privileged_mode             = true

    # Environment variables
    environment_variable {
      name  = "AWS_ACCOUNT_ID"
      value = var.aws_account_id
    }

    environment_variable {
      name  = "IMAGE_REPO_NAME"
      value = "${var.environment}-apollo-gateway-ecr"
    }

    environment_variable {
      name  = "ENVIRONMENT"
      value = var.environment
    }

    environment_variable {
      name  = "SERVICE_TYPE"
      value = "backend"
    }

    environment_variable {
      name  = "CONTAINER_PORT"
      value = "4000"
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
      stream_name = "${var.environment}-apollo-gatewau-codebuild-project"
    }
  }
}

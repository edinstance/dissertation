resource "aws_codebuild_project" "infrastructure_codebuild" {
  name          = "${var.environment}-infrastructure-codebuild-project"
  description   = "CodeBuild project for the infrastructure"
  service_role  = var.codebuild_iam_role_arn
  build_timeout = 30

  source {
    type      = "CODEPIPELINE"
    buildspec = "./product/CICD/aws/infrastructure.yml"
  }

  environment {
    compute_type                = "BUILD_GENERAL1_SMALL"
    image                       = "aws/codebuild/standard:5.0"
    type                        = "LINUX_CONTAINER"
    image_pull_credentials_type = "CODEBUILD"
    privileged_mode             = true

    # Environment variables
    environment_variable {
      name  = "GITLAB_USER"
      type  = "SECRETS_MANAGER"
      value = "${aws_secretsmanager_secret.gitlab_credentials.arn}:gitlab_user"
    }

    environment_variable {
      name  = "GITLAB_TOKEN"
      type  = "SECRETS_MANAGER"
      value = "${aws_secretsmanager_secret.gitlab_credentials.arn}:gitlab_token"
    }

    environment_variable {
      name  = "GITLAB_TERRAFORM_CONFIG"
      type  = "PARAMETER_STORE"
      value = var.gitlab_terraform_config_arn
    }

    environment_variable {
      name  = "ENVIRONMENT"
      value = var.environment
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
      stream_name = "${var.environment}-infrastructure-codebuild-project"
    }
  }
}

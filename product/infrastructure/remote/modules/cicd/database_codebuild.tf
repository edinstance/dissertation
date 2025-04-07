resource "aws_codebuild_project" "database_codebuild" {
  name         = "${var.environment}-database-codebuild-project"
  description  = "CodeBuild project for database migrations"
  service_role = var.codebuild_iam_role_arn
  build_timeout = 30

  source {
    type = "CODEPIPELINE"
    buildspec       = "./product/CICD/aws/database.yml"
  }

  vpc_config {
    vpc_id  = var.vpc_id
    subnets = var.database_subnet_ids
    security_group_ids = [
      var.database_codebuild_sg_id
    ]
  }

  environment {
    compute_type = "BUILD_GENERAL1_SMALL"
    image        = "aws/codebuild/standard:5.0"
    type         = "LINUX_CONTAINER"

    environment_variable {
      name  = "DB_USERNAME"
      value = "${var.rds_secrets_arn}:username"
      type  = "SECRETS_MANAGER"
    }

    environment_variable {
      name  = "DB_PASSWORD"
      value = "${var.rds_secrets_arn}:password"
      type  = "SECRETS_MANAGER"
    }

    environment_variable {
      name  = "environment"
      value = "${var.environment}"
      type  = "PLAINTEXT"
    }
  }
  artifacts {
    type = "CODEPIPELINE"
  }

  cache {
    type = "NO_CACHE"
  }

  logs_config {
    cloudwatch_logs {
      group_name  = "${var.environment}-codebuild-logs"
      stream_name = "${var.environment}-database-codebuild-project"
    }
  }
}

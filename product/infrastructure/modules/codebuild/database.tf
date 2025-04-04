resource "aws_codebuild_project" "database_codebuild" {
  name         = "database-codebuild-project"
  description  = "CodeBuild project for database migrations"
  service_role = var.codebuild_iam_role_arn
  source {
    type            = var.codebuild_src
    location        = var.codebuild_src_url
    git_clone_depth = 1
    buildspec       = "./product/CICD/database.yml"

    git_submodules_config {
      fetch_submodules = true

    }
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
      value = "prod"
      type  = "PLAINTEXT"
    }
  }
  artifacts {
    type = "NO_ARTIFACTS"
  }

  cache {
    type = "NO_CACHE"
  }

  logs_config {
    cloudwatch_logs {
      group_name  = "codebuild-logs"
      stream_name = "database-codebuild-project"
    }
  }

  build_timeout = 60


}


resource "aws_secretsmanager_secret" "gitlab_credentials" {
  name        = "GITLAB_CREDENTIALS"
  description = "GitLab credentials for CI/CD pipeline"
  tags = {
    Environment = var.environment
  }
}

resource "aws_secretsmanager_secret_version" "gitlab_credentials_version" {
  secret_id = aws_secretsmanager_secret.gitlab_credentials.id
  secret_string = jsonencode({
    gitlab_user  = var.gitlab_user
    gitlab_token = var.gitlab_token
  })
}

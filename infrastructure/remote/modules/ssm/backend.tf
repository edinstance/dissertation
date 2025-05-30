resource "aws_ssm_parameter" "spring_active_profile" {
  name  = "/${var.environment}/backend/SPRING_ACTIVE_PROFILE"
  type  = "String"
  value = var.spring_active_profile
}

resource "aws_ssm_parameter" "cognito_jwt_url" {
  name  = "/${var.environment}/backend/COGNITO_JWT_URL"
  type  = "String"
  value = var.cognito_jwt_url
}

resource "aws_ssm_parameter" "database_url" {
  name  = "/${var.environment}/backend/DATABASE_URL"
  type  = "String"
  value = var.database_url
}

resource "aws_ssm_parameter" "postgres_user" {
  name  = "/${var.environment}/backend/POSTGRES_USER"
  type  = "String"
  value = var.postgres_user
}

resource "aws_ssm_parameter" "postgres_password" {
  name  = "/${var.environment}/backend/POSTGRES_PASSWORD"
  type  = "String"
  value = var.postgres_password
}

resource "aws_ssm_parameter" "redis_host" {
  name  = "/${var.environment}/backend/REDIS_HOST"
  type  = "String"
  value = var.redis_host
}

resource "aws_ssm_parameter" "redis_port" {
  name  = "/${var.environment}/backend/REDIS_PORT"
  type  = "String"
  value = var.redis_port
}

resource "aws_ssm_parameter" "jira_access_token" {
  name  = "/${var.environment}/backend/JIRA_ACCESS_TOKEN"
  type  = "String"
  value = var.jira_access_token
}

resource "aws_ssm_parameter" "jira_email" {
  name  = "/${var.environment}/backend/JIRA_EMAIL"
  type  = "String"
  value = var.jira_email
}

resource "aws_ssm_parameter" "jira_url" {
  name  = "/${var.environment}/backend/JIRA_URL"
  type  = "String"
  value = var.jira_url
}

resource "aws_ssm_parameter" "jira_project_key" {
  name  = "/${var.environment}/backend/JIRA_PROJECT_KEY"
  type  = "String"
  value = var.jira_project_key
}

resource "aws_ssm_parameter" "is_chat_enabled" {
  name  = "/${var.environment}/backend/IS_CHAT_ENABLED"
  type  = "String"
  value = var.is_chat_enabled
}

resource "aws_ssm_parameter" "open_ai_key" {
  name  = "/${var.environment}/backend/OPEN_AI_KEY"
  type  = "String"
  value = var.open_ai_key
}

resource "aws_ssm_parameter" "open_ai_project_id" {
  name  = "/${var.environment}/backend/OPEN_AI_PROJECT_ID"
  type  = "String"
  value = var.open_ai_project_id
}

resource "aws_ssm_parameter" "open_ai_organization_id" {
  name  = "/${var.environment}/backend/OPEN_AI_ORGANIZATION_ID"
  type  = "String"
  value = var.open_ai_organization_id
}
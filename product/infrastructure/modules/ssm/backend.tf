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
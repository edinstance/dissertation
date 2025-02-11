# Frontend
output "nextauth_secret_arn" {
  value = aws_ssm_parameter.nextauth_secret.arn
}

output "nextauth_url_arn" {
  value = aws_ssm_parameter.nextauth_url.arn
}

output "backend_graphql_endpoint_arn" {
  value = aws_ssm_parameter.backend_graphql_endpoint.arn
}

output "frontend_cognito_client_id_arn" {
  value = aws_ssm_parameter.frontend_cognito_client_id.arn
}

output "cognito_user_pool_id_arn" {
  value = aws_ssm_parameter.cognito_user_pool_id.arn
}

output "stripe_publishable_key_arn" {
  value = aws_ssm_parameter.stripe_publishable_key.arn
}

output "stripe_secret_key_arn" {
  value = aws_ssm_parameter.stripe_secret_key.arn
}

output "stripe_price_id_arn" {
  value = aws_ssm_parameter.stripe_price_id.arn
}

output "launched_arn" {
  value = aws_ssm_parameter.launched.arn
}

output "recaptcha_site_key_arn" {
  value = aws_ssm_parameter.recaptcha_site_key.arn
}

output "recaptcha_secret_key_arn" {
  value = aws_ssm_parameter.recaptcha_secret_key.arn
}

output "ses_sender_email_arn" {
  value = aws_ssm_parameter.ses_sender_email.arn
}

output "ses_recipient_email_arn" {
  value = aws_ssm_parameter.ses_recipient_email.arn
}

output "ses_production_arn" {
  value = aws_ssm_parameter.ses_production.arn
}

# Backend
output "spring_active_profile_arn" {
  value = aws_ssm_parameter.spring_active_profile.arn
}

output "cognito_jwt_url_arn" {
  value = aws_ssm_parameter.cognito_jwt_url.arn
}

output "database_url_arn" {
  value = aws_ssm_parameter.database_url.arn
}

output "postgres_user_arn" {
  value = aws_ssm_parameter.postgres_user.arn
}

output "postgres_password_arn" {
  value = aws_ssm_parameter.postgres_password.arn
}

output "redis_host_arn" {
  value = aws_ssm_parameter.redis_host.arn
}

output "redis_port_arn" {
  value = aws_ssm_parameter.redis_port.arn
}

# Shared
output "api_key_arn" {
  value = aws_ssm_parameter.api_key.arn
}
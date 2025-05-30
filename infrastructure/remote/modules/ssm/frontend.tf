resource "aws_ssm_parameter" "nextauth_secret" {
  name  = "/${var.environment}/frontend/NEXTAUTH_SECRET"
  type  = "String"
  value = var.nextauth_secret
}

resource "aws_ssm_parameter" "frontend_cognito_client_id" {
  name  = "/${var.environment}/frontend/COGNITO_CLIENT_ID"
  type  = "String"
  value = var.frontend_cognito_client_id
}

resource "aws_ssm_parameter" "cognito_user_pool_id" {
  name  = "/${var.environment}/frontend/COGNITO_USER_POOL_ID"
  type  = "String"
  value = var.cognito_user_pool_id
}

resource "aws_ssm_parameter" "stripe_publishable_key" {
  name  = "/${var.environment}/frontend/STRIPE_PUBLISHABLE_KEY"
  type  = "String"
  value = var.stripe_publishable_key
}

resource "aws_ssm_parameter" "stripe_secret_key" {
  name  = "/${var.environment}/frontend/STRIPE_SECRET_KEY"
  type  = "String"
  value = var.stripe_secret_key
}

resource "aws_ssm_parameter" "stripe_price_id" {
  name  = "/${var.environment}/frontend/STRIPE_PRICE_ID"
  type  = "String"
  value = var.stripe_price_id
}

resource "aws_ssm_parameter" "launched" {
  name  = "/${var.environment}/frontend/LAUNCHED"
  type  = "String"
  value = var.launched
}

resource "aws_ssm_parameter" "recaptcha_site_key" {
  name  = "/${var.environment}/frontend/RECAPTCHA_SITE_KEY"
  type  = "String"
  value = var.recaptcha_site_key
}

resource "aws_ssm_parameter" "recaptcha_secret_key" {
  name  = "/${var.environment}/frontend/RECAPTCHA_SECRET_KEY"
  type  = "String"
  value = var.recaptcha_secret_key
}

resource "aws_ssm_parameter" "ses_sender_email" {
  name  = "/${var.environment}/frontend/SES_SENDER_EMAIL"
  type  = "String"
  value = var.ses_sender_email
}

resource "aws_ssm_parameter" "ses_recipient_email" {
  name  = "/${var.environment}/frontend/SES_RECIPIENT_EMAIL"
  type  = "String"
  value = var.ses_recipient_email
}

resource "aws_ssm_parameter" "ses_production" {
  name  = "/${var.environment}/frontend/SES_PRODUCTION"
  type  = "String"
  value = var.ses_production
}
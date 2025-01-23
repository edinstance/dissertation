output "nextauth_secret_arn" {
    value = aws_ssm_parameter.nextauth_secret.arn
}

output "nextauth_url_arn" {
    value = aws_ssm_parameter.nextauth_url.arn
}

output "backend_graphql_endpoint_arn" {
    value = aws_ssm_parameter.backend_graphql_endpoint.arn
}

output "cognito_client_id_arn" {
    value = aws_ssm_parameter.cognito_client_id.arn
}

output "cognito_user_pool_id_arn" {
    value = aws_ssm_parameter.cognito_user_pool_id.arn
}

output "api_key_arn" {
    value = aws_ssm_parameter.api_key.arn
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
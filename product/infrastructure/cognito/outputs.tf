
output "cognito_client_id" {
  description = "The client ID for the Cognito client"
  value       = aws_cognito_user_pool_client.cognito_client.id
}

output "cognito_user_pool_id" {
    description = "The user pool id"
    value       = aws_cognito_user_pool.user_pool.id
}

output "frontend_client_id" {
  description = "The client ID for the frontend Cognito client"
  value       = aws_cognito_user_pool_client.frontend_client.id
}

output "backend_client_id" {
  description = "The client ID for the backend Cognito client"
  value       = aws_cognito_user_pool_client.backend_client.id
}

output "cognito_user_pool_id" {
    description = "The user pool id"
    value       = aws_cognito_user_pool.user_pool.id
}
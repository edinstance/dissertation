
output "database_url" {
  value = aws_rds_cluster.database.endpoint
}

output "rds_secrets_arn" {
   value = aws_rds_cluster.database.master_user_secret[0].secret_arn
}
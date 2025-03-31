
output "database_url" {
  value = aws_rds_cluster.database.endpoint
}
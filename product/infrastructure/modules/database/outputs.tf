
output "database_url" {
  value = aws_rds_cluster.database.endpoint
}

output "redis_host" {
  value = aws_elasticache_cluster.endpoint.address
}

output "redis_port" {
  value = aws_elasticache_cluster.endpoint.port
}
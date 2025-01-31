
output "database_url" {
  value = aws_rds_cluster.database.endpoint
}

output "redis_host" {
  value = aws_elasticache_serverless_cache.redis_cache.endpoint[0].address
}
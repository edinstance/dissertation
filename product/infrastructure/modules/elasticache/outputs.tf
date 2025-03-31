output "redis_host" {
  value = aws_elasticache_serverless_cache.redis_cache.endpoint[0].address
}
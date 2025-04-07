# Redis Serverless Cache
resource "aws_elasticache_serverless_cache" "redis_cache" {
  engine = "redis"
  name   = "${var.environment}-redis"

  major_engine_version = "7"
  security_group_ids   = [var.redis_sg_id]
  subnet_ids           = var.private_subnet_ids
}
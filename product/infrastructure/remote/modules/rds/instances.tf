# Postgres RDS instance
resource "aws_rds_cluster_instance" "cluster_instances" {
  count                        = 2
  identifier                   = "${var.environment}-aurora-instance-${count.index}"
  cluster_identifier           = aws_rds_cluster.database.id
  instance_class               = "db.serverless"
  engine                       = aws_rds_cluster.database.engine
  engine_version               = aws_rds_cluster.database.engine_version
  performance_insights_enabled = true
}
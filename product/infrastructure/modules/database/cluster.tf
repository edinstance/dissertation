
resource "aws_rds_cluster" "database" {
  cluster_identifier = "${var.environment}-database"
  engine             = "aurora-postgresql"
  engine_mode        = "provisioned"
  engine_version     = "16.1"
  database_name      = "${var.environment}Database"
  master_username    = "${var.environment}Master"
  manage_master_user_password = true
  storage_encrypted  = true

  skip_final_snapshot = true

  serverlessv2_scaling_configuration {
    max_capacity             = 1.0
    min_capacity             = 0.5
  }

  availability_zones = var.availability_zones
  db_subnet_group_name = var.db_subnet_group_name
  vpc_security_group_ids = [ var.db_sg_id ]
  network_type = "IPV4"
  
  enabled_cloudwatch_logs_exports = [ "audit", "error", "general", "postgresql" ]
}
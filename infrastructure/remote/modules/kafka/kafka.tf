resource "aws_msk_cluster" "kafka_cluster" {
  cluster_name           = "${var.environment}-kafka-cluster"
  kafka_version          = "3.6.0"
  number_of_broker_nodes = 3

  broker_node_group_info {
    instance_type = var.environment == "prod" ? "kafka.m5.large" : "kafka.t3.small"
    client_subnets = [
      for subnet in var.subnet_ids : subnet
    ]
    storage_info {
      ebs_storage_info {
        volume_size = var.environment == "prod" ? 1000 : 100
      }
    }
    security_groups = [var.security_group_id]
  }

  logging_info {
    broker_logs {
      cloudwatch_logs {
        enabled   = true
        log_group = "${var.environment}-kafka-logs"
      }
    }
  }

  tags = {
    environment = var.environment
  }
}

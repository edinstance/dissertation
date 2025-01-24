
resource "aws_ecs_service" "frontend_service" {
  name            = "${var.environment}-frontend"
  cluster         = aws_ecs_cluster.cluster.arn
  task_definition = aws_ecs_task_definition.frontend_task.arn
  desired_count   = 1

  launch_type = "FARGATE"

  network_configuration {
    subnets = var.public_subnet_ids
  }

 lifecycle {
    # Allow external changes without Terraform plan difference such as autoscaling
    ignore_changes = [desired_count]
  }
}
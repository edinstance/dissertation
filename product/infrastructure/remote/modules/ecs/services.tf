# Frontend Service
resource "aws_ecs_service" "frontend_service" {
  name            = "${var.environment}-frontend"
  cluster         = aws_ecs_cluster.cluster.arn
  task_definition = aws_ecs_task_definition.frontend_task.arn
  desired_count   = 3

  launch_type = "FARGATE"

  network_configuration {
    subnets         = var.private_subnet_ids
    security_groups = [var.frontend_sg_id]
  }

  depends_on = [aws_lb.frontend_alb]

  lifecycle {
    # Allow external changes without Terraform plan difference such as autoscaling
    ignore_changes = [desired_count, task_definition]
  }
}

# Backend Service
resource "aws_ecs_service" "backend_service" {
  name            = "${var.environment}-backend"
  cluster         = aws_ecs_cluster.cluster.arn
  task_definition = aws_ecs_task_definition.backend_task.arn
  desired_count   = 3

  launch_type = "FARGATE"

  network_configuration {
    subnets         = var.private_subnet_ids
    security_groups = [var.backend_sg_id]
  }

  depends_on = [aws_lb.backend_alb]

  lifecycle {
    # Allow external changes without Terraform plan difference such as autoscaling
    ignore_changes = [desired_count, task_definition]
  }
}

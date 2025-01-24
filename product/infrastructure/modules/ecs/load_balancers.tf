
resource "aws_lb" "frontend_alb" {
  name               = "${var.environment}-frontend-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [var.alb_sg_id]
  subnets            = [for subnet_id in var.public_subnet_ids : subnet_id]

}

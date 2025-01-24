
resource "aws_lb" "frontend_alb" {
  name               = "${var.environment}-frontend-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [var.alb_sg_id]
  subnets            = [for subnet_id in var.public_subnet_ids : subnet_id]

}

resource "aws_lb_listener" "http" {
    load_balancer_arn = aws_lb.frontend_alb.arn
    port              = "80"
    protocol          = "HTTP"
    default_action {
        type             = "forward"
        target_group_arn = aws_lb_target_group.alb_frontend_tg.arn
    }
}

resource "aws_lb_target_group" "alb_frontend_tg" {
    name        = "${var.environment}-frontend-tg"
    port        = 3000
    protocol    = "HTTP"
    target_type = "ip"
    vpc_id      = var.vpc_id
}
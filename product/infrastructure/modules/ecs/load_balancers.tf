
resource "aws_lb" "frontend_alb" {
  name               = "${var.environment}-frontend-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [var.frontend_alb_sg_id]
  subnets            = [for subnet_id in var.public_subnet_ids : subnet_id]

}

resource "aws_lb_listener" "frontend_http" {
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

    health_check {
        path                = "/api/health"
        interval            = 30
        timeout             = 5
        healthy_threshold   = 2
        unhealthy_threshold = 2
    }
}


resource "aws_lb" "backend_alb" {
  name               = "${var.environment}-backend-alb"
  internal           = true
  load_balancer_type = "application"
  security_groups    = [var.backend_alb_sg_id]
  subnets            = [for subnet_id in var.private_subnet_ids : subnet_id]

}

resource "aws_lb_listener" "backend_http" {
    load_balancer_arn = aws_lb.backend_alb.arn
    port              = "80"
    protocol          = "HTTP"
    default_action {
        type             = "forward"
        target_group_arn = aws_lb_target_group.alb_backend_tg.arn
    }
}

resource "aws_lb_target_group" "alb_backend_tg" {
    name        = "${var.environment}-backend-tg"
    port        = 8080
    protocol    = "HTTP"
    target_type = "ip"
    vpc_id      = var.vpc_id

    health_check {
        path                = "/details/health"
        interval            = 30
        timeout             = 5
        healthy_threshold   = 2
        unhealthy_threshold = 2
    }
}

resource "aws_lb" "frontend_alb" {
  name                       = "${var.environment}-frontend-alb"
  internal                   = false
  load_balancer_type         = "application"
  drop_invalid_header_fields = true
  security_groups            = [var.frontend_alb_sg_id]
  subnets                    = [for subnet_id in var.public_subnet_ids : subnet_id]

}

resource "aws_lb_listener" "frontend_http" {
  load_balancer_arn = aws_lb.frontend_alb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type = "redirect"

    redirect {
      port        = "443"
      protocol    = "HTTPS"
      status_code = "HTTP_301"
    }
  }
}

resource "aws_lb_listener" "frontend_https" {
  load_balancer_arn = aws_lb.frontend_alb.arn
  port              = 443
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-TLS13-1-2-2021-06"
  certificate_arn   = var.acm_certificate_arn

  default_action {
    type = "forward"
    forward {
      target_group {
        arn = aws_lb_target_group.alb_frontend_tg_blue.arn
      }
      stickiness {
        enabled  = true
        duration = 600
      }
    }
    target_group_arn = aws_lb_target_group.alb_frontend_tg_blue.arn
  }
}


resource "aws_lb_target_group" "alb_frontend_tg_blue" {
  name        = "${var.environment}-frontend-tg-blue"
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

  stickiness {
    type            = "lb_cookie"
    cookie_duration = 600
  }
}

resource "aws_lb_target_group" "alb_frontend_tg_green" {
  name        = "${var.environment}-frontend-tg-green"
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

  stickiness {
    type            = "lb_cookie"
    cookie_duration = 600
  }
}

resource "aws_lb" "backend_alb" {
  name                       = "${var.environment}-backend-alb"
  internal                   = true
  load_balancer_type         = "application"
  drop_invalid_header_fields = true
  security_groups            = [var.backend_alb_sg_id]
  subnets                    = [for subnet_id in var.private_subnet_ids : subnet_id]

}

resource "aws_lb_listener" "backend_http" {
  load_balancer_arn = aws_lb.backend_alb.arn
  port              = "80"
  protocol          = "HTTP"
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.alb_apollo_gatway_tg_blue.arn
  }
}

resource "aws_lb_listener_rule" "backend_rule" {
  listener_arn = aws_lb_listener.backend_http.arn
  priority     = 20

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.alb_backend_tg_blue.arn
  }

  condition {
    path_pattern {
      values = ["/backend*"]
    }
  }
}

resource "aws_lb_target_group" "alb_apollo_gatway_tg_blue" {
  name        = "${var.environment}-apollo-gateway-tg-blue"
  port        = 4000
  protocol    = "HTTP"
  target_type = "ip"
  vpc_id      = var.vpc_id
}

resource "aws_lb_target_group" "alb_apollo_gatway_tg_green" {
  name        = "${var.environment}-apollo-gateway-tg-green"
  port        = 4000
  protocol    = "HTTP"
  target_type = "ip"
  vpc_id      = var.vpc_id
}

resource "aws_lb_target_group" "alb_backend_tg_blue" {
  name        = "${var.environment}-backend-tg-blue"
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

resource "aws_lb_target_group" "alb_backend_tg_green" {
  name        = "${var.environment}-backend-tg-green"
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

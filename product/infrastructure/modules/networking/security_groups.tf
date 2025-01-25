
# Security group for the alb to the ecs service
resource "aws_security_group" "alb_ecs_sg" {
    name = "${var.environment}-alb-ecs-sg"
    
    vpc_id = aws_vpc.vpc.id

    ## Allow inbound on port 80 from internet (all traffic)
    ingress {
        protocol         = "tcp"
        from_port        = 80
        to_port          = 80
        cidr_blocks      = ["0.0.0.0/0"]
    }
    
    egress {
        protocol     = "-1" # Allow all protocols
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}

# Security group for the ecs service from the alb
resource "aws_security_group" "ecs_frontend_sg" {
    name = "${var.environment}-ecs-frontend-sg"

    vpc_id = aws_vpc.vpc.id
    
    ingress {
        protocol         = "tcp"
        from_port        = 3000
        to_port          = 3000
        security_groups  = [aws_security_group.alb_ecs_sg.id] # Allow traffic from ALB
    }

    egress {
        protocol         = "-1"
        from_port        = 0
        to_port          = 0
        cidr_blocks      = ["0.0.0.0/0"]
    }
}
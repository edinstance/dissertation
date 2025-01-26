
# Security group for the alb to the ecs service
resource "aws_security_group" "frontend_alb_sg" {
    name = "${var.environment}-frontend-alb-sg"
    
    vpc_id = aws_vpc.vpc.id

    ## Allow inbound on port 80 from internet (all traffic)
    ingress {
        protocol         = "tcp"
        from_port        = 80
        to_port          = 80
        cidr_blocks      = ["0.0.0.0/0"]
    }
    
    ingress {
        protocol         = "tcp"
        from_port        = 443
        to_port          = 443
        cidr_blocks      = ["0.0.0.0/0"]
    }

    egress {
        protocol     = "tcp"
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}

# Security group for the ecs service from the alb
resource "aws_security_group" "frontend_sg" {
    name = "${var.environment}-frontend-sg"

    vpc_id = aws_vpc.vpc.id
    
    ingress {
        protocol         = "tcp"
        from_port        = 3000
        to_port          = 3000
        security_groups  = [aws_security_group.frontend_alb_sg.id] # Allow traffic from ALB
    }

    egress {
        protocol         = "tcp"
        from_port        = 0
        to_port          = 0
        cidr_blocks      = ["0.0.0.0/0"]
    }
}

# Security group for the alb to the ecs service
resource "aws_security_group" "backend_alb_sg" {
    name = "${var.environment}-backend-alb-sg"
    
    vpc_id = aws_vpc.vpc.id

    ingress {
        protocol         = "-1"
        from_port        = 0
        to_port          = 0
        security_groups = [aws_security_group.frontend_sg.id]
    }
    
   egress {
        protocol     = "tcp"
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}


# Security group for the alb to the ecs service
resource "aws_security_group" "backend_sg" {
    name = "${var.environment}-backend-sg"
    
    vpc_id = aws_vpc.vpc.id

    ingress {
        protocol         = "tcp"
        from_port        = 8080
        to_port          = 8080
        security_groups = [aws_security_group.backend_alb_sg.id]
    }
    
    egress {
        protocol     = "-1" # Allow all protocols
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}
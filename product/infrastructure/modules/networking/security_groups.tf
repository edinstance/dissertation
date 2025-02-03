
# Security group for the alb to the ecs service
resource "aws_security_group" "frontend_alb_sg" {
    name = "${var.environment}-frontend-alb-sg"
    description = "Security group for the frontend ALB"
    
    vpc_id = aws_vpc.vpc.id

    ## Allow inbound on port 80 from internet (all traffic)
    ingress {
        description = "Allow http traffic from the internet"
        protocol         = "tcp"
        from_port        = 80
        to_port          = 80
        cidr_blocks      = ["0.0.0.0/0"]
    }
    
    ingress {
        description = "Allow https traffic from the internet"
        protocol         = "tcp"
        from_port        = 443
        to_port          = 443
        cidr_blocks      = ["0.0.0.0/0"]
    }

    egress {
        description = "Allow all traffic out"
        protocol     = "-1"
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}

# Security group for the ecs service from the alb
resource "aws_security_group" "frontend_sg" {
    name = "${var.environment}-frontend-sg"
    description = "Security group for the frontend ECS service"

    vpc_id = aws_vpc.vpc.id
    
    ingress {
        description = "Allow traffic from the frontend ALB"
        protocol         = "tcp"
        from_port        = 3000
        to_port          = 3000
        security_groups  = [aws_security_group.frontend_alb_sg.id] # Allow traffic from ALB
    }

    egress {
        description = "Allow all traffic out"
        protocol         = "-1"
        from_port        = 0
        to_port          = 0
        cidr_blocks      = ["0.0.0.0/0"]
    }
}

# Security group for the alb to the ecs service
resource "aws_security_group" "backend_alb_sg" {
    name = "${var.environment}-backend-alb-sg"
    description = "Security group for the backend ALB"
    
    vpc_id = aws_vpc.vpc.id

    ingress {
        description = "Allow traffic from frontend"
        protocol         = "-1"
        from_port        = 0
        to_port          = 0
        security_groups = [aws_security_group.frontend_sg.id]
    }
    
   egress {
        description = "Allow all traffic out"
        protocol     = "tcp"
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}


# Security group for the alb to the ecs service
resource "aws_security_group" "backend_sg" {
    name = "${var.environment}-backend-sg"
    description = "Security group for the backend ECS service"
    
    vpc_id = aws_vpc.vpc.id

    ingress {
        description = "Allow traffic from backend ALB"
        protocol         = "tcp"
        from_port        = 8080
        to_port          = 8080
        security_groups = [aws_security_group.backend_alb_sg.id]
    }
    
    egress {
        description = "Allow all traffic out"
        protocol     = "-1"
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}

resource "aws_security_group" "database_sg" {
    name = "${var.environment}-database-sg"
    description = "Security group for the database"

    vpc_id = aws_vpc.vpc.id
  
    ingress {
        description = "Allow traffic from backend"
        protocol         = "tcp"
        from_port        = 5432
        to_port          = 5432
        security_groups = [aws_security_group.backend_sg.id]
    }
    
    egress {
        description = "Allow all traffic out"
        protocol     = "-1"
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}

resource "aws_security_group" "redis_sg" {
    name = "${var.environment}-redis-sg"
    description = "Security group for the Redis cluster"

    vpc_id = aws_vpc.vpc.id
  
    ingress {
        description = "Allow traffic from backend"
        protocol         = "tcp"
        from_port        = 6379
        to_port          = 6379
        security_groups = [aws_security_group.backend_sg.id]
    }
    
    egress {
        description = "Allow all traffic out"
        protocol     = "-1"
        from_port    = 0
        to_port      = 0
        cidr_blocks  = ["0.0.0.0/0"]
    }
}
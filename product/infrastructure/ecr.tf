
resource "aws_ecr_repository" "frontend-repo" {
    name                 = "${var.environment}-frontend"
    image_tag_mutability = "IMMUTABLE"
    
    image_scanning_configuration {
        scan_on_push = true
    }
}

resource "aws_ecr_repository" "backend-repo" {
    name                 = "${var.environment}-backend"
    image_tag_mutability = "IMMUTABLE"
    
    image_scanning_configuration {
        scan_on_push = true
    }
}
terraform {
  required_version = ">= 1.9"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.60.0"
    }
  }
}

provider "aws" {
  region = "eu-west-2"

  access_key                  = "test"
  secret_key                  = "test"
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  s3_use_path_style = true


  dynamic "endpoints" {
    for_each = toset(["dynamodb", "s3"])
    content {
      dynamodb = "http://localhost:8000"
      s3       = "http://localhost:4566"
    }
  }

  default_tags {
    tags = {
      Environment = "local"
      Project     = "SubShop"
    }
  }
}


module "dynamodb" {
  source = "../shared/dynamodb"

  environment = "dev"
}

module "s3" {
  source = "../shared/s3"

  environment     = "local"
  allowed_origins = ["http://localhost:3000"]
}

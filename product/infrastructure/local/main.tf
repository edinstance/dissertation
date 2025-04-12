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

  access_key                  = "dummykey"
  secret_key                  = "dummysecret"
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

    
  dynamic "endpoints" {
    for_each = toset(["dynamodb"])
    content {
      dynamodb = "http://localhost:8000"
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

terraform {
  required_version = ">= 1.9"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.60.0"
    }
  }
}

resource "aws_cognito_user_pool" "user_pool" {
  name = "final-year-project-user-pool"

  # Allow users to sign in with their email address
  username_attributes = ["email"]

  #   Alow users to sign up with their email address
  admin_create_user_config {
    allow_admin_create_user_only = false
  }

  # Allow users to recover their account using their email address
  account_recovery_setting {
    recovery_mechanism {
      name     = "verified_email"
      priority = 1
    }
  }

  # Configure the password policy for the user pool
  password_policy {
    minimum_length    = 8
    require_lowercase = false
    require_numbers   = true
    require_symbols   = false
    require_uppercase = true
  }

  tags = {
    Name : "final-year-project-user-pool"
  }
}


resource "aws_cognito_user_pool_client" "cognito_client" {

  name = "final-year-project-client"

  # Set the client's user pool to be the one created above
  user_pool_id = aws_cognito_user_pool.user_pool.id

  # Enable and configure OAuth authentication with JWT tokens
  supported_identity_providers = ["COGNITO"]

  # Enable the following flows for client authentication
  explicit_auth_flows = [
    "ALLOW_REFRESH_TOKEN_AUTH",
    "ALLOW_USER_PASSWORD_AUTH",
    "ALLOW_USER_SRP_AUTH"
  ]

  # Keep the refresh token valid for 30 days
  refresh_token_validity = 30

  # Don't generate a client secret
  generate_secret = false
}

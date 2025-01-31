
resource "aws_cognito_user_pool" "user_pool" {
  name = "${var.environment}-final-year-project-user-pool"

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

  schema {
    attribute_data_type = "String"
    name                = "name"
    required            = true
    mutable             = true
  }
  
  tags = {
    Name = "${var.environment}-final-year-project-user-pool"
    Environment = var.environment
  }
}
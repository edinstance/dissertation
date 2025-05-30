resource "aws_cognito_user_group" "admin_group" {
  name         = "SubShopAdmin"
  user_pool_id = aws_cognito_user_pool.user_pool.id

  description = "This is a user group for admins"
}
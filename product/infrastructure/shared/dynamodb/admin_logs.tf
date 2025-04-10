
resource "aws_dynamodb_table" "admin_access_logs" {
  name         = "${var.environment}-admin-access-logs"
  billing_mode = "PROVISIONED"

  read_capacity  = 1
  write_capacity = 1

  hash_key  = "adminId"
  range_key = "timestamp"


  attribute {
    name = "adminId"
    type = "S"
  }

  attribute {
    name = "timestamp"
    type = "S"
  }

  ttl {
    attribute_name = "ttlTimestamp"
    enabled        = true
  }

}
resource "aws_dynamodb_table" "bids" {
  name         = "${var.environment}-bids"
  billing_mode = "PROVISIONED"

  read_capacity  = 1
  write_capacity = 1

  hash_key  = "bidId"
  range_key = "createdAt"

  attribute {
    name = "bidId"
    type = "S"
  }

  attribute {
    name = "itemId"
    type = "S"
  }

  attribute {
    name = "createdAt"
    type = "S"
  }

  global_secondary_index {
    name      = "itemId-index"
    hash_key  = "itemId"
    range_key = "createdAt"

    read_capacity  = 1
    write_capacity = 1

    projection_type = "ALL"
  }

  ttl {
    attribute_name = "ttlTimestamp"
    enabled        = true
  }
}

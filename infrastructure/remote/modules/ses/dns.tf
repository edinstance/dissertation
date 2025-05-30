resource "aws_route53_record" "ses_verification" {
  zone_id = var.aws_route53_zone_id
  name    = "_amazonses.${aws_ses_domain_identity.ses_domain.domain}"
  type    = "TXT"
  ttl     = "600"
  records = [aws_ses_domain_identity.ses_domain.verification_token]
}

resource "aws_route53_record" "ses_dkim" {
  count   = 3
  zone_id = var.aws_route53_zone_id
  name = format(
    "%s._domainkey.%s",
    element(aws_ses_domain_dkim.ses_dkim.dkim_tokens, count.index),
    aws_ses_domain_identity.ses_domain.domain
  )
  type    = "CNAME"
  ttl     = "600"
  records = ["${element(aws_ses_domain_dkim.ses_dkim.dkim_tokens, count.index)}.dkim.amazonses.com"]
}

resource "aws_route53_record" "ses_mail_from_mx" {
  count   = var.domain != null ? 1 : 0
  zone_id = var.aws_route53_zone_id
  name    = aws_ses_domain_mail_from.ses_mail_from[0].mail_from_domain
  type    = "MX"
  ttl     = "600"
  records = ["10 feedback-smtp.${data.aws_region.current.name}.amazonses.com"]
}

resource "aws_route53_record" "ses_mail_from_txt" {
  count   = var.domain != null ? 1 : 0
  zone_id = var.aws_route53_zone_id
  name    = aws_ses_domain_mail_from.ses_mail_from[0].mail_from_domain
  type    = "TXT"
  ttl     = "600"
  records = ["v=spf1 include:amazonses.com -all"]
}

resource "aws_route53_record" "ses_dmarc" {
  zone_id = var.aws_route53_zone_id
  name    = "_dmarc.${aws_ses_domain_identity.ses_domain.domain}"
  type    = "TXT"
  ttl     = "600"
  records = ["v=DMARC1; p=none;"]
}
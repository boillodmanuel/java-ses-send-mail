# Build

```bash
# Set version
./mvnw versions:set -DnewVersion=1.0.0

# Package
./mvnw clean package
```

# Usage

```bash
# You must define at least the recipient (which should be a valid SES recipient)
java -cp target/java-ses-send-mail-1.0.0.jar -Dmail.sender="java-ses-send-mail@ses.domain.acme.cloud" -Dmail.recipient="user.name@acme.fr" fr.acme.ses.SendEmailMain

# By default, authentication relies on environment variables: AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY and AWS_SESSION_TOKEN (if temporary credential) 
# To use EC2 instance profile instead, use:
java -cp target/java-ses-send-mail-1.0.0.jar -Daws.credentials.ec2_instance_profile=true -Dmail.sender="java-ses-send-mail@ses.domain.acme.cloud" -Dmail.recipient="user.name@acme.fr" fr.acme.ses.SendEmailMain
```

You can also change default mail config with following system properties (ex: `-Dmail.subject="test"`):
- `mail.sender`
- `mail.recipient`
- `mail.subject`
- `mail.body`


# Documentation

- [Authentication](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials.html)
- [Send email](https://docs.aws.amazon.com/fr_fr/ses/latest/dg/send-an-email-using-sdk-programmatically.html)
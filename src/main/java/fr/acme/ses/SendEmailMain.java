package fr.acme.ses;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

public class SendEmailMain {

    public static void main(String[] args) {
        // CREDENTIALS
        System.out.print("Use AWS Credentials provider: ");
        AwsCredentialsProvider provider;
        if (System.getProperty("aws.credentials.ec2_instance_profile", "false").equals("true")) {
            System.out.println("InstanceProfileCredentialsProvider");
            provider = InstanceProfileCredentialsProvider.create();
        } else {
            System.out.println("EnvironmentVariableCredentialsProvider");
            provider = EnvironmentVariableCredentialsProvider.create();
        }
        provider.resolveCredentials();
        System.out.println();

        // EMAIL ATTRIBUTES
        String sender = System.getProperty("mail.sender");
        String recipient = System.getProperty("mail.recipient");
        String subject = System.getProperty("mail.subject", "Test email");
        String body = System.getProperty("mail.body", "<html><body><h1>Hello!</h1></body></html>");

        System.out.println("Email attributes:");
        System.out.println("- Sender: " + sender);
        System.out.println("- Recipient: " + recipient);
        System.out.println("- Subject: " + subject);
        System.out.println("- Body: " + body);
        System.out.println();

        if (sender == null || sender.isEmpty()) {
            System.err.println("Error: sender not set. You can set it with -Dmail.sender=");
            System.exit(1);
        }

        if (recipient == null || recipient.isEmpty()) {
            System.err.println("Error: recipient not set. You can set it with -Dmail.recipient=");
            System.exit(1);
        }

        try (SesClient client = SesClient.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(provider)
                .build()) {

            System.out.println("Sending email...");
            SendMessageEmailRequest.send(client, sender, recipient, subject, body);
        }

        System.out.println("Done");
    }
}

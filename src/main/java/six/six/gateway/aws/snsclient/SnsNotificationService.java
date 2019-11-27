package six.six.gateway.aws.snsclient;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.jboss.logging.Logger;
import six.six.gateway.SMSService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nickpack on 09/08/2017.
 */
public class SnsNotificationService implements SMSService {

    private static Logger logger = Logger.getLogger(SnsNotificationService.class);

    public boolean send(String phoneNumber, String message, String clientToken, String clientSecret, String awsRegion) {

        logger.debug("Sending to: " + phoneNumber + "|" + clientToken + clientSecret + " " + awsRegion);
        try {
            Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
            smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                    .withStringValue("HomeOffice")
                    .withDataType("String"));

            BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(clientToken, clientSecret);
            AmazonSNS snsClient = AmazonSNSClient
                    .builder()
                    .withRegion(awsRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials))
                    .build();

            String id = snsClient.publish(new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber(phoneNumber)
                    .withMessageAttributes(smsAttributes)).getMessageId();
            logger.info("SMS Send Successfully to " + phoneNumber + " " + id);
            return (id != null);
        } catch (Exception ex) {
            logger.error("Unable to send SMS for " + phoneNumber + " " + ex.getMessage());
            return false;
        }

    }
}

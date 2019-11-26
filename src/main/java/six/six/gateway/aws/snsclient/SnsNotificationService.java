package six.six.gateway.aws.snsclient;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.jboss.logging.Logger;
import six.six.gateway.SMSService;
import six.six.gateway.govuk.notify.NotifySMSService;
import six.six.keycloak.KeycloakSmsConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nickpack on 09/08/2017.
 */
public class SnsNotificationService implements SMSService {

    //TODO Implement proxy
    private static Logger logger = Logger.getLogger(SnsNotificationService.class);
    public boolean send(String phoneNumber, String message, String clientToken, String clientSecret) {
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("HomeOffice")
                .withDataType("String"));

        logger.warn("Sending to: " + phoneNumber + "|" + clientToken + clientSecret + " " + KeycloakSmsConstants.AWS_REGION);

        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(clientToken,clientSecret);
        AmazonSNS snsClient = AmazonSNSClient
                .builder()
                .withRegion(KeycloakSmsConstants.AWS_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials))
                .build();



        String id= snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes)).getMessageId();

        return (id!=null);
    }
}

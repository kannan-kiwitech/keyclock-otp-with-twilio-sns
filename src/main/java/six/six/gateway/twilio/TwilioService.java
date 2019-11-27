package six.six.gateway.twilio;

import com.twilio.Twilio;
import org.jboss.logging.Logger;
import org.keycloak.models.AuthenticatorConfigModel;
import six.six.gateway.SMSService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import six.six.keycloak.KeycloakSmsConstants;

/**
 * Created by Kannan.
 */
public class TwilioService implements SMSService {

    private static Logger logger = Logger.getLogger(TwilioService.class);

    public boolean send(String phoneNumber, String message, String sId, String authToken, String defaultSenderPhone) {
        logger.debug("Sending to: " + phoneNumber + "|" + sId + " " + authToken + " " + defaultSenderPhone);
        try {
            Twilio.init(sId, authToken);
            Message sms = Message.creator(new PhoneNumber(phoneNumber),
                    new PhoneNumber(defaultSenderPhone),
                    message).create();
            logger.info("SMS Send Successfully to " + phoneNumber + " " + sms.getSid());
            return true;
        } catch (Exception ex) {
            logger.error("Unable to send SMS for " + phoneNumber + " " + ex.getMessage());
            return false;
        }
    }
}

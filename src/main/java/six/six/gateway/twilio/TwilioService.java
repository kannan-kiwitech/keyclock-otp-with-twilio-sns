package six.six.gateway.twilio;

import com.twilio.Twilio;
import org.jboss.logging.Logger;
import six.six.gateway.SMSService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import six.six.keycloak.KeycloakSmsConstants;

/**
 * Created by Kannan.
 */
public class TwilioService implements SMSService {

    private static Logger logger = Logger.getLogger(TwilioService.class);

    public boolean send(String phoneNumber, String message, String sId, String authToken) {
        try {
            logger.debug("token " + sId + " secret " + authToken);
            Twilio.init(sId, authToken);
            Message sms = Message.creator(new PhoneNumber(phoneNumber),
                    new PhoneNumber(KeycloakSmsConstants.MSG_MOBILE_SENDER_DEFAULT),
                    message).create();
            logger.info("SMS Send Successfully " + sms.getSid());
            return true;
        } catch (Exception ex){
            logger.error("Unable to send SMS " + ex.getMessage());
            return false;
        }
    }
}

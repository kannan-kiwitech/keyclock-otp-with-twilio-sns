package six.six.keycloak;

import org.keycloak.models.UserModel;

public class MobileNumberHelper {
    public static String getMobileNumber(UserModel user) {
        String mobileNumberCreds = user.getFirstAttribute(KeycloakSmsConstants.ATTR_MOBILE);
        String countryCodeCreds = user.getFirstAttribute(KeycloakSmsConstants.ATTR_COUNTRY_CODE);

        String mobileNumber = null;


        if (mobileNumberCreds != null && !mobileNumberCreds.isEmpty()) {
            mobileNumber = ((countryCodeCreds != null) ? countryCodeCreds : "") + mobileNumberCreds;
        }

        return mobileNumber;
    }
}

package six.six.gateway.aws.snsclient;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import six.six.keycloak.KeycloakSmsConstants;

/**
 * Created by nickpack on 09/08/2017.
 */
public class SnsClientFactory {
    private static AmazonSNS snsClient = null;
    public static AmazonSNS getSnsClient(String clientToken, String clientSecret) {

        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(clientToken,clientSecret);
        snsClient = AmazonSNSClient
                .builder()
                .withRegion(KeycloakSmsConstants.AWS_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials))
                .build();
        return snsClient;
    }
}

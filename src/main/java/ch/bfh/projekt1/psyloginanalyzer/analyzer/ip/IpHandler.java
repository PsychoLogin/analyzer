package ch.bfh.projekt1.psyloginanalyzer.analyzer.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Christoph Kiser on 11.12.2016.
 */
public class IpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpHandler.class);

    public IpAddress convertToIp(String ipString) throws InvalidIpException {
        IpAddress returnAddress = new IpAddress();
        String[] parts = ipString.trim().split("\\.");
        if (parts.length != 4) {
            LOGGER.error(ipString + " is invalid");
            throw new InvalidIpException("No Valid IPv4 Address given");
        }
        long ipAsNumber = 0;
        for (int i = 0; i < parts.length; i++) {
            ipAsNumber += Integer.parseInt(parts[i]) * Math.pow(10, (3 - i) * 3);

        }
        returnAddress.setIpAddressAsNumber(ipAsNumber);
        returnAddress.setIpAddressAsString(ipString);
        return returnAddress;
    }
}

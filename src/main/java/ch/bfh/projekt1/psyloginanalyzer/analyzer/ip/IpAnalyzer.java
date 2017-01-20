package ch.bfh.projekt1.psyloginanalyzer.analyzer.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Christoph Kiser on 03.01.2017.
 */

@Stateless
public class IpAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpAnalyzer.class);

    /**
     * @param ipString IP to be checked
     * @return Provider of the IP to be checked
     */
    public String checkRange(String ipString) {
        try {
            IpHandler handler = new IpHandler();
            IpAddress ip = handler.convertToIp(ipString);

            ArrayList<IpRange> ipRanges = new ArrayList<>();


            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("ip/ipProvider.csv")))) {

                br.lines().forEach(s -> {
                    String[] part = s.split(",");
                    if (part.length > 4) {
                        IpAddress ipFrom = null;
                        IpAddress ipTo = null;
                        try {
                            ipFrom = handler.convertToIp(part[0]);
                            ipTo = handler.convertToIp(part[1]);
                        } catch (InvalidIpException e) {
                            e.printStackTrace();
                        }

                        String providerName = part[4];
                        ipRanges.add(new IpRange(ipFrom, ipTo, providerName));
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            for (IpRange currentRange : ipRanges) {
                if (currentRange.containsIp(ip)) {
                    return currentRange.getProviderName();
                }
            }

        } catch (InvalidIpException e) {
            e.printStackTrace();
        }
        return null;
    }
}
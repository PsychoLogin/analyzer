package ch.bfh.projekt1.psyloginanalyzer.analyzer.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Christoph Kiser on 03.01.2017.
 */

@Stateless
public class IpAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpAnalyzer.class);

    public String checkRange(String ipString) {
        try {
            IpHandler handler = new IpHandler();
            IpAddress ip = handler.convertToIp(ipString);

            ArrayList<IpRange> ipRanges = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(new File(this.getClass().getResource("/ip/ipProvider.csv").getFile())))) {
                String curLine;
                while ((curLine = reader.readLine()) != null) {
                    String[] part = curLine.trim().split(",");
                    if (part.length > 4) {
                        IpAddress ipFrom = handler.convertToIp(part[0]);
                        IpAddress ipTo = handler.convertToIp(part[1]);
                        String providerName = part[4];
                        ipRanges.add(new IpRange(ipFrom, ipTo, providerName));

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (IpRange currentRange : ipRanges) {
                if (currentRange.containsIp(ip)) {
                    return currentRange.getProviderName();
                }
            }
        } catch (InvalidIpException e) {
            LOGGER.error("Invalid IP", e);
        }
        return null;
    }
}
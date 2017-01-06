package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import javax.ejb.Stateless;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Christoph Kiser on 03.01.2017.
 */

@Stateless
public class IpAnalyzer {

    public String checkRange(String ipString) {

        IpHandler handler = new IpHandler();
        IpAddress ip = handler.convertToIp(ipString);

        ArrayList<IpRange> ipRanges = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(this.getClass().getResource("/ip/ipProvider.csv").toURI())))) {
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        for (IpRange currentRange : ipRanges) {
            if (currentRange.containsIp(ip)) {
                return currentRange.getProviderName();
            }
        }
        return null;
    }
}
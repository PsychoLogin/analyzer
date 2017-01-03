package ch.bfh.projekt1.psyloginanalyzer.analyzer;

/**
 * Created by Christoph Kiser on 11.12.2016.
 */
public class IpHandler {


    public IpAddress convertToIp(String ipString) {
       IpAddress returnAddress = new IpAddress();
        String[] part = ipString.trim().split("\\.");
            long ipAsNumber = 0;
            for(int i = 0; i< part.length; i++){
                ipAsNumber += Integer.parseInt(part[i])*Math.pow(10, (3-i)*3);

        }
        returnAddress.setIpAddressAsNumber(ipAsNumber);
        returnAddress.setIpAddressAsString(ipString);
        return returnAddress;
    }
}

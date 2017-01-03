package ch.bfh.projekt1.psyloginanalyzer.analyzer;

/**
 * Created by Christoph Kiser on 11.12.2016.
 */
public class IpAddress {

    private long ipAddressAsNumber;
    private String ipAddressAsString;

    public long getIpAddressAsNumber() {
        return ipAddressAsNumber;
    }

    public void setIpAddressAsNumber(long ipAddressAsNumber) {
        this.ipAddressAsNumber = ipAddressAsNumber;
    }

    public String getIpAddressAsString() {
        return ipAddressAsString;
    }

    public void setIpAddressAsString(String ipAddressAsString) {
        this.ipAddressAsString = ipAddressAsString;
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "ipAddressAsNumber=" + ipAddressAsNumber +
                ", ipAddressAsString='" + ipAddressAsString + '\'' +
                '}';
    }
}

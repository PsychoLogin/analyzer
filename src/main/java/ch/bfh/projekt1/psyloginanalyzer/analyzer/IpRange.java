package ch.bfh.projekt1.psyloginanalyzer.analyzer;

/**
 * Created by Christoph Kiser on 11.12.2016.
 */
public class IpRange {

    private IpAddress from;
    private IpAddress to;
    private String providerName;

    IpRange(IpAddress from, IpAddress to,String providerName){
        this.from = from;
        this.to = to;
        this.providerName = providerName;
    }

    public boolean containsIp(IpAddress ip) {
        return from.getIpAddressAsNumber() <= ip.getIpAddressAsNumber() && to.getIpAddressAsNumber() >= ip.getIpAddressAsNumber();
    }

    public String getProviderName() {
        return providerName;
    }

    @Override
    public String toString(){
        return "von: " +from+ "\n" +
                "bis: "+to+ "\n" +
                "gehoert: "+providerName+"\n\n";
    }
}

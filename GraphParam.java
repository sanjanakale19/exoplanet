import java.io.*;
import java.net.*;
import java.util.*;

public class GraphParam {
    private double BJD;
    private double flux;

    GraphParam(double BJD, double flux) {
        this.BJD = BJD;
        this.flux = flux;
    }

    double getBJD() {
        return BJD;
    }

    double getFlux() {
        return flux;
    }

    void setBJD(double BJD) { this.BJD = BJD; }

    void setFlux(double flux) { this.flux = flux; }

    static ArrayList<GraphParam> getTableFromLink(String link) throws Exception {
        return getTable(new DataObj("", link));
    }

    static ArrayList<GraphParam> getTable(DataObj obj) throws Exception {
        ArrayList<GraphParam> params = new ArrayList<>();

        try {
            boolean redirect = false;
            String link = obj.getLink();
            URL myUrl;
            do {
                myUrl = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
                int status = conn.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    if (status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_MOVED_TEMP
                                                                    || status == HttpURLConnection.HTTP_SEE_OTHER) {
                        redirect = true;
                        link = conn.getHeaderField("Location"); //i wish i knew how this works
                    }
                } else {
                    redirect = false;
                }

            } while (redirect);

            BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()));

            reader.readLine();  // skip the first line of text - not necessary at the moment

            String inputLn;
            double bjd;
            double flux;
            Scanner scan;
            while ((inputLn = reader.readLine()) != null) {

                scan = new Scanner(inputLn).useDelimiter("\\s+|,+");
                bjd = scan.nextDouble() + 2454833;
                scan.skip(",");
                flux = scan.nextDouble();

                params.add(new GraphParam(bjd, flux));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("u suck");
        }
        return params;
    }

    @Override
    public String toString() {
        return "(" + BJD + ", " + flux + ")";
    }

}

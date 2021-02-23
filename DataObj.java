import java.util.ArrayList;
import java.net.URL;
import java.io.*;
import java.util.Scanner;

public class DataObj {
    private String name;
    private String link;

    DataObj(String myName, String myLink) {
        this.name = myName;
        this.link = myLink;
    }

    String getName() {
        return name;
    }

    String getLink() {
        return link;
    }

    ArrayList<DataObj> readURL() {
        ArrayList<DataObj> txtList = new ArrayList<>();

        try {
            URL myUrl = new URL(link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()));

            String inputLn;
            String url;
            Scanner scan;
            while ((inputLn = reader.readLine()) != null) {
                scan = new Scanner(inputLn);
                scan.skip("wget -q");
                url = scan.nextLine();
                txtList.add(new DataObj(url.substring(91, 100), url));
            }

        } catch (Exception e) {
            System.out.println("u suck");
            e.printStackTrace();
        }

        return txtList;
    }

    @Override
    public String toString() {
        return name + ": " + link;
    }
}

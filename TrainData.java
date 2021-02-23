import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

class TrainData {
    private boolean isExo;
    private List<GraphParam> dataSet;

    TrainData(boolean isExo, List<GraphParam> dataSet){
        this.isExo = isExo;
        this.dataSet = dataSet;
    }

    boolean getIfExo() {
        return isExo;
    }

    List<GraphParam> getDataSet() {
        return dataSet;
    }

    void setExo(boolean exo) {
        isExo = exo;
    }

    void setExo(List<GraphParam> dataSet) {
        this.dataSet = dataSet;
    }


    static List<TrainData> getDataList(String fileName) throws Exception {
        List<TrainData> list = new ArrayList<>();

        boolean isExo;
        List<GraphParam> dataSet;

        try {
            File data = new File(fileName);
            Scanner fileScan = new Scanner(data);
            String temp = fileScan.nextLine();
            Scanner scan;

            while (fileScan.hasNextLine()) {
                scan = new Scanner(fileScan.nextLine());
                scan.useDelimiter("\\s+|,+");
                isExo = scan.nextInt() == 2;

                List<GraphParam> vals = new ArrayList<>();
                int i = 0;
                while (scan.hasNext()) {
                    scan.skip(",");
                    vals.add(new GraphParam(i, scan.nextDouble()));
                    i += 30;
                }

                list.add(new TrainData(isExo, vals));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("u suck");
        }
        return list;
    }

}

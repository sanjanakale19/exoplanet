import java.util.ArrayList;
import java.util.List;

public class DataParser {
    final static ArrayList<DataObj> cList = new ArrayList<>();

    private static void printDataTable(ArrayList<DataObj> obj, int studyNum) throws Exception {
        System.out.println(obj.get(studyNum).getName());


        ArrayList<GraphParam> campaignParams = GraphParam.getTable(obj.get(studyNum));

        int point = 0;
        for (GraphParam gp : campaignParams) {
            System.out.println(point + " - " + gp);
            point++;
        }
    }


    public static void main(String[] args) throws Exception {

        cList.add(new DataObj("c0", "https://www.cfa.harvard.edu/~avanderb/C0asciiwget.sh"));
        cList.add(new DataObj("c1", "https://www.cfa.harvard.edu/~avanderb/C1asciiwget.sh"));
        cList.add(new DataObj("c2", "https://www.cfa.harvard.edu/~avanderb/C2asciiwget.sh"));
        cList.add(new DataObj("c3", "https://www.cfa.harvard.edu/~avanderb/C3asciiwget.sh"));
        cList.add(new DataObj("c4", "https://www.cfa.harvard.edu/~avanderb/C4asciiwget.sh"));
        cList.add(new DataObj("c5", "https://www.cfa.harvard.edu/~avanderb/C5asciiwget.sh"));
        cList.add(new DataObj("c6", "https://www.cfa.harvard.edu/~avanderb/C6asciiwget.sh"));
        cList.add(new DataObj("c7", "https://www.cfa.harvard.edu/~avanderb/C7asciiwget.sh"));
        cList.add(new DataObj("c8", "https://www.cfa.harvard.edu/~avanderb/C8asciiwget.sh"));
        cList.add(new DataObj("c91", "https://www.cfa.harvard.edu/~avanderb/C91asciiwget.sh"));
        cList.add(new DataObj("c92", "https://www.cfa.harvard.edu/~avanderb/C92asciiwget.sh"));
        cList.add(new DataObj("c10", "https://www.cfa.harvard.edu/~avanderb/C10asciiwget.sh"));
        cList.add(new DataObj("c111", "https://www.cfa.harvard.edu/~avanderb/C111asciiwget.sh"));
        cList.add(new DataObj("c112", "https://www.cfa.harvard.edu/~avanderb/C112asciiwget.sh"));
        cList.add(new DataObj("c12", "https://www.cfa.harvard.edu/~avanderb/C12asciiwget.sh"));
        cList.add(new DataObj("c13", "https://www.cfa.harvard.edu/~avanderb/C13asciiwget.sh"));
        cList.add(new DataObj("c14", "https://www.cfa.harvard.edu/~avanderb/C14asciiwget.sh"));
        cList.add(new DataObj("c15", "https://www.cfa.harvard.edu/~avanderb/C15asciiwget.sh"));
        cList.add(new DataObj("c16", "https://www.cfa.harvard.edu/~avanderb/C16asciiwget.sh"));
        cList.add(new DataObj("c17", "https://www.cfa.harvard.edu/~avanderb/C17asciiwget.sh"));
        cList.add(new DataObj("c18", "https://www.cfa.harvard.edu/~avanderb/C18asciiwget.sh"));
        cList.add(new DataObj("c19", "https://www.cfa.harvard.edu/~avanderb/C19asciiwget.sh"));


        DataObj campaign = cList.get(9);

        ArrayList<DataObj> obj = campaign.readURL();

        int studyNum = 0;

        String graphName = obj.get(studyNum).getName();

        System.out.println("Campaign name = " + campaign.getName() + ", Graph Name = " + graphName);
/*
        int linkNum = 0;
        for (DataObj link : obj) {
            System.out.println(linkNum + " - " + link);
            linkNum++;
        }
*/

        //printDataTable(obj, studyNum);
/*
        GraphCurve curve = new GraphCurve("myCurve", GraphParam.getTable(obj.get(studyNum)), false);

        GraphCurve curve = new GraphCurve("myGraph1", GraphParam.getTableFromLink("http://archive.stsci.edu/missions/hlsp/k2sff/c05/211300000/11380/hlsp_k2sff_k2_lightcurve_211311380-c05_kepler_v1_llc-default-aper.txt"));
        curve.pack();
        RefineryUtilities.centerFrameOnScreen(curve);
        curve.setVisible(true);

        GraphCurve freqCurve = GraphCurve.timeToFreqGraph(curve, "myFreqCurve");

        freqCurve.pack();
        RefineryUtilities.centerFrameOnScreen(freqCurve);
        freqCurve.setVisible(true);
        */

        int N = 1000;

        List<Double> domain = new ArrayList<>();

        // Square wave
//        List<GraphParam> squareWave = new ArrayList<>();
//        for (double x = 0; x < N; x++) {
//            domain.add(x/N);
//            double y = (x >= N/3f && x < 2f*N/3f) ? 1.0 : 0;
//
////            double y;
////            if (x > N/3f && x <= N/2f) {
////                y = (x - N/3f) * (1.0 / (N/2f - N/3f));
////            } else if (x > N/2f && x < 2f*N/3f) {
////                y = (2f*N/3f - x) * (1.0 / (N/2f - N/3f));
////            } else {
////                y = 0;
////            }
//
//            squareWave.add(new GraphParam(x, y));
//        }
//
//        GraphLineCurve curve2 = new GraphLineCurve("mySquareWave", squareWave);
//        curve2.graph();
//
//        GraphComplexCurve freqCurve1 = GraphLineCurve.timeToFreqGraph(curve2, domain, "myFreqCurve");
//        freqCurve1.graph();
//
//        GraphLineCurve origCurve1 = GraphComplexCurve.freqToLineGraph(freqCurve1, curve2,"myOriginalCurve");
//        origCurve1.graph();

//        // Plain Sine curve
//        List<GraphParam> sinusoid = new ArrayList<>();
//        for (double x = 0; x < 1000; x++) {
//            domain.add(x/1000);
//            sinusoid.add(new GraphParam(x,  Math.sin(2 * Math.PI * x / 100) /*+ (x < 500 ? x / 500 : 1.0 - (x - 500) / 500)*/));
//        }
//
//        GraphLineCurve curve2 = new GraphLineCurve("mySinusoid", sinusoid);
//        curve2.graph();
//
//        GraphComplexCurve freqCurve1 = GraphLineCurve.timeToFreqGraph(curve2, domain, "myFreqCurve");
//        freqCurve1.graph();
//
//        GraphLineCurve origCurve1 = GraphComplexCurve.freqToLineGraph(freqCurve1, curve2,"myOriginalCurve");
//        origCurve1.graph();

        // Flux signal curve
//        List<GraphParam> myList = GraphParam.getTable(obj.get(studyNum));
//

//
//        GraphTimeCurve curve = new GraphTimeCurve("mySinusoid", myList);
//        curve.graph();
//
//        List<GraphParam> bjdList = new ArrayList<>(myList);
//
//        for (GraphParam gp : bjdList) {
//            gp.setBJD(gp.getBJD() - 2454833);
//        }
//
//        GraphLineCurve curve2 = new GraphLineCurve("myBJDGraph", bjdList);
//        curve2.graph();
//
//        GraphComplexCurve freqCurve = GraphTimeCurve.timeToFreqGraph(curve, domain, "myFreqCurve");
//        freqCurve = freqCurve.lowPassFilter("MyFreqCurve", 0.001);
//        freqCurve.graph();
//
//        GraphTimeCurve origCurve = GraphComplexCurve.freqToTimeGraph(freqCurve, curve,"myOriginalCurve");
//        origCurve.graph();

        List<TrainData> data = TrainData.getDataList("exoTrain.csv");

        for (double x = 0; x < 3197; x += 1) {
            domain.add(x/(3197 * 30));
        }

        int num = (int)(Math.random() * (data.size() - 37) + 37);
        System.out.println("The Non-ExoPlanet picked is the " + num + " one in the data set");

        GraphLineCurve nonExo = new GraphLineCurve("Confirmed Non-exoplanet", data.get(num).getDataSet());
        nonExo.graph();
        GraphComplexCurve freqCurve1 = GraphLineCurve.timeToFreqGraph(nonExo, domain, "myFreqCurve");
        freqCurve1 = freqCurve1.lowPassFilter("MyFreqCurve", 0.0005);
        GraphLineCurve fixedExo1 = GraphComplexCurve.freqToLineGraph(freqCurve1, nonExo,"CORRECTED - Confirmed Non-exoplanet");
        fixedExo1.graph();

        num = (int)(Math.random() * 37);
        System.out.println("The ExoPlanet picked is the " + num + " one in the data set");

        GraphLineCurve exo = new GraphLineCurve("Confirmed Exoplanet", data.get(num).getDataSet());
        exo.graph();
        GraphComplexCurve freqCurve = GraphLineCurve.timeToFreqGraph(exo, domain, "myFreqCurve");
        freqCurve = freqCurve.lowPassFilter("MyFreqCurve", 0.0005);
        GraphLineCurve fixedExo = GraphComplexCurve.freqToLineGraph(freqCurve, exo,"CORRECTED - Confirmed Exoplanet");
        fixedExo.graph();

        GraphLineCurve curve = new GraphLineCurve("myGraph1", GraphParam.getTableFromLink("http://archive.stsci.edu/missions/hlsp/k2sff/c05/211300000/11380/hlsp_k2sff_k2_lightcurve_211311380-c05_kepler_v1_llc-default-aper.txt"));
        curve.graph();
    }


}


import java.util.ArrayList;
import java.util.List;

class FourierTransform {

     static List<ComplexNumber> toFrequency(List<ComplexNumber> list) {
        List<ComplexNumber> newParams = new ArrayList<>();
        int n = list.size();

        for (int k = 0; k < n; k++) {  // For each output element
            double sumReal = 0;
            double sumImag = 0;

            for (int t = 0; t < n; t++) {  // For each input element
                double angle = 2 * Math.PI * t * k / n;
                sumReal +=   1.0 / n * list.get(t).getReal() * Math.cos(angle) + list.get(t).getImag() * Math.sin(angle);
                sumImag += - 1.0 / n * list.get(t).getReal() * Math.sin(angle) + list.get(t).getImag() * Math.cos(angle);
            }

            newParams.add(new ComplexNumber(sumReal, sumImag));
        }

        return newParams;
    }

    static List<ComplexNumber> toTime(List<ComplexNumber> list) {
        List<ComplexNumber> newParams = new ArrayList<>();
        int n = list.size();

        for (int k = 0; k < n; k++) {  // For each output element
            double sumReal = 0;
            double sumImag = 0;

            for (int t = 0; t < n; t++) {  // For each input element
                double angle = 2 * Math.PI * t * k / n;
                sumReal += list.get(t).getReal() * Math.cos(angle) - list.get(t).getImag() * Math.sin(angle);
                sumImag += list.get(t).getReal() * Math.sin(angle) + list.get(t).getImag() * Math.cos(angle);
            }

            newParams.add(new ComplexNumber(sumReal, sumImag));
        }
        return newParams;
    }

}

public class ComplexNumber {
    private double real;
    private double imag;

    ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;

    }

    double getReal() {
        return real;
    }

    double getImag() {
        return imag;
    }

    double getMagnitude() { return Math.sqrt(real * real + imag * imag); }

    public void setReal(double myReal) {
        real = myReal;
    }

    public void setImag(double myImag) {
        imag = myImag;
    }

    @Override
    public String toString() {
        return "(" + real + ", " + imag + ")";
    }
}

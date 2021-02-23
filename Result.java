
public class Result<T> {

    private T result;
    private Exception fault;

    public Result(T result, Exception fault) {
        this.result = result;
        this.fault = fault;
    }

    boolean wasFaulty() {
        return fault != null;
    }

    T getResult() {
        return result;
    }

    Exception getFault() {
        assert wasFaulty();
        return fault;
    }

    static Result<Double> computeDivision(int num, int denom) {
        if (denom == 0) {
            return new Result<>(null, new Exception("Division by zero"));
        } else {
            return new Result<>((double) num / denom, null);
        }
    }

    public static void main(String[] args) {
        Result<Double> result = computeDivision(5, 0);
        if (result.wasFaulty()) {
            System.out.println(result.getFault().getMessage());
        } else {
            System.out.println(result.getResult());
        }
    }
}

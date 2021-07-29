package cinema;

public class APIException {
    private final String error;

    public APIException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

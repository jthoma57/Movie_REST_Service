package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    @JsonProperty("token")
    private final String token;

    @JsonProperty("ticket")
    private final Seat seat;

    public Ticket(@JsonProperty("token") String token, @JsonProperty("ticket") Seat seat) {
        this.token = token;
        this.seat = seat;
    }

    public String getToken() {
        return token;
    }

    public Seat getSeat() {
        return seat;
    }
}

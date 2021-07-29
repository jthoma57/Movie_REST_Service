package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {
    private final Cinema cinema = new Cinema(9,9);

    @GetMapping("/seats")
    public Map<String, Object> getSeats() {
        return Map.of(
                "total_rows", cinema.getRows(),
                "total_columns", cinema.getColumns(),
                "available_seats", cinema.getAvailableSeats()
        );
    }

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody Seat seat) {
        if (seat.getRow() <= 0 || seat.getRow() > cinema.getRows() || seat.getColumn() <= 0 || seat.getColumn() > cinema.getColumns()) {
            throw new APIRequestException("The number of a row or a column is out of bounds!");
        }

        for (Ticket t : cinema.getPurchasedTickets()) {
            Seat s = t.getSeat();
            if (s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn()) {
                throw new APIRequestException("The ticket has been already purchased!");
            }
        }

        Ticket ticket = new Ticket(UUID.randomUUID().toString(), seat);

        cinema.addPurchasedTicket(ticket);

        return ticket;
    }

    @PostMapping("/return")
    public Map<String, Object> returnTicket(@RequestBody Ticket ticket) {
        boolean ticketExists = false;
        for (Ticket t : cinema.getPurchasedTickets()) {
            if (t.getToken().equals(ticket.getToken())) {
                ticketExists = true;
                break;
            }
        }

        if (!ticketExists) {
            throw new APIRequestException("Wrong token!");
        }

        return Map.of("returned_ticket", cinema.returnTicket(ticket));
    }

    @PostMapping("/stats")
    public ResponseEntity<?>  getStats(@RequestParam(required = false) String password) {
        System.out.println(password);
        if (password == null || !password.equals("super_secret")) {
            return ResponseEntity.status(401).body(Map.of("error","The password is wrong!"));
        }

        return new ResponseEntity<>(Map.of(
                "current_income", cinema.getCurrentIncome(),
                "number_of_available_seats", cinema.getAvailableSeats().size() ,
                "number_of_purchased_tickets", cinema.getPurchasedTickets().size()
        ), HttpStatus.OK);
    }

}

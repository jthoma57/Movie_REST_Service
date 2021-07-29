package cinema;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private final int rows;
    private final int columns;
    private final List<Seat> availableSeats;
    private final List<Ticket> purchasedTickets;
    private int currentIncome = 0;

    public Cinema(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.availableSeats = fillCinema();
        this.purchasedTickets = new ArrayList<>();
    }

    private List<Seat> fillCinema() {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= columns; col++) {
                seats.add(new Seat(row, col));
            }
        }
        return seats;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void addPurchasedTicket(Ticket ticket) {
        this.purchasedTickets.add(ticket);
        Seat seat = ticket.getSeat();
        this.availableSeats.removeIf(s -> s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn());
        currentIncome += seat.getPrice();
    }

    public Seat returnTicket(Ticket ticket) {
        for (Ticket t : getPurchasedTickets()) {
            if (t.getToken().equals(ticket.getToken())) {
                Seat seat = t.getSeat();
                this.availableSeats.add(seat);
                this.purchasedTickets.remove(t);
                currentIncome -= seat.getPrice();
                return seat;
            }
        }
        return null;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }
}

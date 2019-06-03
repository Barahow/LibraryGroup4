package model;

public class ReservedBook extends Book {
    private String reservationDate;
    private String avilabledate;


    public ReservedBook(int isbn, String title, String author, int bookType, boolean availability, boolean reserved, String reservationDate, String avilabledate) {
        super(isbn, title, author, bookType, availability, reserved);
        this.reservationDate = reservationDate;
        this.avilabledate = avilabledate;
    }


    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getAvilabledate() {
        return avilabledate;
    }

    public void setAvilabledate(String avilabledate) {
        this.avilabledate = avilabledate;
    }
}

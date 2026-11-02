import java.sql.SQLOutput;
import java.sql.Time;
import java.util.Date;

public class Transactions {
    private Date date;
    private Time time;
    private String description;
    private String vendor;
    private double amount;


    public Transactions(Date date, Time time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }


    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }


    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void display(String currentTra){
        System.out.println("Date: "+ge);

    }

}

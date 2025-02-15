/*
    Lớp Bảo hiểm
 */
package core;

import java.util.Date;

/**
 *
 * @author no-solace
 */
public class Inserance {

    private String Id;
    private String licensePlate;
    private Date startDate;
    private int period;
    private float fee;
    private String owner;

    public Inserance(String Id, String licensePlate, Date startDate, int period, float fee, String owner) {
        this.Id = Id;
        this.licensePlate = licensePlate;
        this.startDate = startDate;
        this.period = period;
        this.fee = fee;
        this.owner = owner;
    }

    public void setPeriod(int period) {
        if (0 < period && period % 12 == 0 && period / 12 <= 3) {
            this.period = period;
        }
    }

}

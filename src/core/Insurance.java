/*
    Lớp Bảo hiểm
 */
package core;

import java.io.Serializable;

/**
 *
 * @author no-solace
 */
public class Insurance implements Comparable<Insurance>, Serializable {

    public static final String ID_FORMAT = "^\\d{4}$";
    public static final String NAME_FORMAT = "^[A-Za-z ]{2,25}";

    private String id;
    private String licensePlate;
    private String startDate;
    private int period;
    private long fee;
    private String beneficiary; // Người thụ hưởng

    public Insurance(String id, String licensePlate, String startDate, int period, long fee, String beneficiary) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.startDate = startDate;
        this.period = period;
        this.fee = fee;
        this.beneficiary = beneficiary;
    }

    public Insurance(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public boolean equals(Object obj) {
        Insurance i = (Insurance) obj;
        return this.licensePlate.equals(i.licensePlate);
    }

    public String getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getPeriod() {
        return period;
    }

    public long getFee() {
        return fee;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setPeriod(int period) {
        if (period == 12 || period == 24 || period == 36) {
            this.period = period;
        }
    }

    public void setFee(long fee) {
        if (fee > 0) {
            this.fee = fee;
        }
    }

    @Override
    public int compareTo(Insurance o) {
        return Long.compare(this.fee, o.fee);
    }
}

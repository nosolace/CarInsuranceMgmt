/*
    Lớp mô tả đối tượng Car
 */
package core;

import java.io.Serializable;
import java.util.Date;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class Car implements Serializable {

    public static final String LICENSE_PLATE_FORMAT
            = "^5[0-9](X[1-4B]|T[12A]|F[12A]|C[13A]|H[12A]|K[12A]|"
            + "C[24B]|L[12A]|U[12A]|M[12A]|G[12A]|D[123A]|E[12A]|"
            + "N[13A]|P[12A]|S[123A]|V[123A]|Y[123B]|Z[12A]|N[23B])[0-9]{5}$";
    public static final String OWNER_NAME_FORMAT = "^[A-Za-z ]{2,25}";
    public static final String PHONE_FORMAT = "0[1-9][0-9]{8}";
    public static final String BRAND_CAR_FORMAT = "[A-Za-z]{5,12}";

    private String licensePlate;
    private String owner;
    private String phone;
    private String brand;
    private long valueOfVehicle;
    private Date regDate;
    private String regPlace;
    private int numOfSeats;

    public Car(String licensePlate, String owner, String phone, String brand, long valueOfVehicle, Date regDate, int numOfSeats) {
        this.licensePlate = licensePlate;
        this.owner = owner;
        this.phone = phone;
        this.brand = brand.toUpperCase();
        this.valueOfVehicle = valueOfVehicle;
        this.regDate = regDate;
        this.regPlace = ConsoleInputter.getDistrict(licensePlate.charAt(2));
        this.numOfSeats = numOfSeats;
    }

    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public boolean equals(Object obj) {
        Car c = (Car) obj;
        return this.licensePlate.equals(c.licensePlate);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    public String getPhone() {
        return phone;
    }

    public String getBrand() {
        return brand;
    }

    public long getValueOfVehicle() {
        return valueOfVehicle;
    }

    public Date getRegDate() {
        return regDate;
    }

    public String getRegPlace() {
        return regPlace;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setOwner(String owner) {
        if (owner.matches(OWNER_NAME_FORMAT)) {
            this.owner = owner;
        }
    }

    public void setPhone(String phone) {
        if (phone.matches(PHONE_FORMAT)) {
            this.phone = phone;
        }
    }

    public void setBrand(String brand) {
        if (brand.matches(BRAND_CAR_FORMAT)) {
            this.brand = brand.toUpperCase();
        }
    }

    public void setNumOfSeats(int numOfSeats) {
        if (4 <= numOfSeats && numOfSeats <= 36) {
            this.numOfSeats = numOfSeats;
        }
    }
}

/*
    Lớp mô tả đối tượng Car
 */
package core;

import java.io.Serializable;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class Car implements Serializable, Comparable<Car> {

    public static final String LICENSE_PLATE_FORMAT
            = "^5[0-9](x[1-4b]|t[12a]|f[12A]|c[13A]|h[12A]|k[12A]|"
            + "c[24B]|l[12A]|u[12A]|m[12A]|g[12A]|d[123A]|e[12A]|"
            + "n[13A]|p[12A]|s[123A]|v[123A]|y[123B]|z[12A]|n[23B])[0-9]{5}$";
    public static final String OWNER_NAME_FORMAT = "^[A-Za-z ]{2,25}";
    public static final String PHONE_FORMAT = "0[1-9][0-9]{8}";
    public static final String BRAND_CAR_FORMAT = "[A-Za-z]{5,12}";

    private String licensePlate;
    private String owner;
    private String phone;
    private String brand;
    private long valueOfVehicle;
    private String regDate;
    private String regPlace;
    private int numOfSeats;

    public Car(String licensePlate, String owner, String phone, String brand, long valueOfVehicle, String regDate, int numOfSeats) {
        this.licensePlate = licensePlate.toUpperCase();
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

    public String getRegDate() {
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

    @Override
    public int compareTo(Car o) {
        return Long.compare(this.valueOfVehicle, o.valueOfVehicle);
    }
}

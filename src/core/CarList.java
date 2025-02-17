/*
    Car List
 */
package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class CarList extends ArrayList<Car> {

    private InsuranceList insurances;

    public CarList(InsuranceList insurances) {
        this.insurances = insurances;
    }

    public CarList() {
    }

    public void setInsurances(InsuranceList insurances) {
        this.insurances = insurances;
    }

    private static String licensePlate, owner, phone, brand;
    private static long valueOfVehicle;
    private static String regDate;
    private static int numOfSeats;

    protected String inputPlate() {
        String errorMsg = "The first two characters are numbers in the range from 50 to 59.\n"
                + "The next two characters include a character describing the district code.\n"
                + "The last of five characters is numeric.";
        return ConsoleInputter.getStr("Input license plate", Car.LICENSE_PLATE_FORMAT, errorMsg);
    }

    private String inputOwner() {
        return ConsoleInputter.getStr("Input owner", Car.OWNER_NAME_FORMAT, "Length must be between 2 and 25 characters.");
    }

    private String inputPhone() {
        return ConsoleInputter.getStr("Input phone", Car.PHONE_FORMAT, "Must contain exactly 10 digits and belong to a valid Vietnamese network operator");
    }

    private String inputDate() {
        Date d;
        do {
            d = ConsoleInputter.getDate("Input Date", "dd/MM/yyyy");
        } while (d.after(new java.util.Date()));
        return ConsoleInputter.dateStr(d, "dd/MM/yyyy");
    }

    private String inputBrand() {
        return ConsoleInputter.getStr("Input brand", Car.BRAND_CAR_FORMAT, "Length must be between 5 and 12 characters");
    }

    private int inputSeats() {
        return ConsoleInputter.getInt("Input number of seats", 4, 36);
    }

    private long inputValue() {
        return ConsoleInputter.getLongInt("Input Value of Car", 1000, 100000000000L);
    }

    protected boolean isCarInList(String licensePlate) {
        return this.indexOf(new Car(licensePlate)) >= 0;
    }

    protected Car getCar(String licensePlate) {
        return this.get(this.indexOf(new Car(licensePlate)));
    }

    private String getCarDetail(String licensePlate) {
        Car c = getCar(licensePlate);
        return String.format("%-17s: %s\n", "License plate", c.getLicensePlate())
                + String.format("%-17s: %s\n", "Owner", c.getOwner())
                + String.format("%-17s: %s\n", "Phone", c.getPhone())
                + String.format("%-17s: %s\n", "Car brand", c.getBrand())
                + String.format("%-17s: %,d\n", "Value of vehicle", c.getValueOfVehicle())
                + String.format("%-17s: %d\n", "Number of seats", c.getNumOfSeats())
                + String.format("%-17s: %s", "Registration date", c.getRegDate());
    }

    public void addCar() {
        do {
            licensePlate = inputPlate();
            if (isCarInList(licensePlate)) {
                System.out.println("This license plate has existed");
            }
        } while (isCarInList(licensePlate));
        owner = inputOwner();
        phone = inputPhone();
        brand = inputBrand();
        valueOfVehicle = inputValue();
        regDate = inputDate();
        numOfSeats = inputSeats();
        this.add(new Car(licensePlate, owner, phone, brand, valueOfVehicle, regDate, numOfSeats));
    }

    public void findByPlate() {
        licensePlate = inputPlate();
        if (isCarInList(licensePlate)) {
            String seperator = "-----------------------------------------------------";
            System.out.println("Vehicle Details:");
            System.out.println(seperator);
            System.out.println(getCarDetail(licensePlate));
            System.out.println(seperator);
        } else {
            System.out.println("No one matches the search criteria!");
        }
    }

    public void updateCarInfo() {
        licensePlate = inputPlate();
        if (isCarInList(licensePlate)) {
            owner = ConsoleInputter.updateStr("Input owner", Car.OWNER_NAME_FORMAT, "Length must be between 2 and 25 characters");
            getCar(licensePlate).setOwner(owner);
            phone = ConsoleInputter.updateStr("Input phone", Car.PHONE_FORMAT, "Must contain exactly 10 digits and belong to a valid Vietnamese network operator");
            getCar(licensePlate).setPhone(phone);
            brand = ConsoleInputter.updateStr("Input brand", Car.BRAND_CAR_FORMAT, "Length must be between 5 and 12 characters");
            getCar(licensePlate).setBrand(brand);
            numOfSeats = ConsoleInputter.updateInt("Input number of seats", getCar(licensePlate).getNumOfSeats(), 4, 36);
            getCar(licensePlate).setNumOfSeats(numOfSeats);
            System.out.println("This vehicle has been updated successfully.");
        } else {
            System.out.println("This vehicle does not exist.");
        }
    }

    public void delete() {
        licensePlate = inputPlate();
        if (isCarInList(licensePlate)) {
            if (!insurances.isInList(licensePlate)) {
                System.out.println("Vehicle Details:");
                System.out.println(getCarDetail(licensePlate));
                if (ConsoleInputter.getBoolean("Are you sure you want to delete this registration")) {
                    this.remove(getCar(licensePlate));
                    System.out.println("The vehicle information has been succesfully deleted");
                }
            } else {
                System.out.println("This vehicle has been registed for insurance");
            }
        } else {
            System.out.println("This vehicle has not registered yet");
        }
    }

    public void printUninsuredCar() {
        if (insurances != null) {
            CarList uninsuredCars = new CarList();
            for (Car c : this) {
                if (!insurances.isInList(c.getLicensePlate())) {
                    uninsuredCars.add(c);
                }
            }
            if (!uninsuredCars.isEmpty()) {
                Collections.sort(uninsuredCars);
                String seperator = "----------------------------------------"
                        + "------------------------------------------------";
                System.out.println(seperator);
                System.out.println("Report: UNINSURED CARS");
                System.out.println("Sorted by: Vehicle Type");
                System.out.println("Sort type: DESC");
                System.out.println(seperator);
                System.out.printf("%-4s| %-10s| %-11s|%-18s| %-10s| %-11s| %7s%n",
                        "No.", "Li. plate", "Reg. Date", "Ve. Owner", "Brand", "Num. Seats", "Value");
                System.out.println(seperator);
                for (Car c : uninsuredCars) {
                    System.out.printf("%-4s| %-10s| %-11s|%-18s| %-10s| %-11s| %,7d%n",
                            uninsuredCars.indexOf(c) + 1, c.getLicensePlate(), c.getRegDate(), c.getOwner(),
                            c.getBrand(), c.getNumOfSeats(), c.getValueOfVehicle());
                }
                System.out.println(seperator);
            } else {
                System.out.println("No information available");
            }
        } else {
            Collections.sort(this);
            String seperator = "----------------------------------------"
                    + "------------------------------------------------";
            System.out.println(seperator);
            System.out.println("Report: UNINSURED CARS");
            System.out.println("Sorted by: Vehicle Type");
            System.out.println("Sort type: DESC");
            System.out.println(seperator);
            System.out.printf("%-4s| %-10s| %-11s|%-18s| %-10s| %-11s| %7s%n",
                    "No.", "Li. plate", "Reg. Date", "Ve. Owner", "Brand", "Num. Seats", "Value");
            System.out.println(seperator);
            for (Car c : this) {
                System.out.printf("%-4s| %-10s| %-11s|%-18s| %-10s| %-11s| %,7d%n",
                        this.indexOf(c) + 1, c.getLicensePlate(), c.getRegDate(), c.getOwner(),
                        c.getBrand(), c.getNumOfSeats(), c.getValueOfVehicle());
            }
            System.out.println(seperator);
        }
    }

    public void saveFile(String fname) {
        try (FileOutputStream fos = new FileOutputStream(fname);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Ghi vào số lượng khách hàng
            oos.writeInt(this.size());
            // Vòng lặp duyệt từng list
            for (Car car : this) {
                oos.writeObject(car); // Ghi vào file
            }
        } catch (IOException e) { // Báo lỗi nếu có
            System.out.println(e);
        }
    }

    public void loadFromFile(String fname) {
        //Lấy file theo tên file
        File f = new File(fname);
        //Kiểm tra file có tồn tại
        if (!f.exists()) {
            System.out.println(fname + " does not exist."); // Thông báo không có file
        } else {
            try (FileInputStream fis = new FileInputStream(fname);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                // Biến lưu số lượng Obj của list trong saveFile sẽ được đọc đầu tiên
                int size = ois.readInt();
                // Load danh sách vào list này
                for (int i = 0; i < size; i++) {
                    Car car = (Car) ois.readObject();
                    this.add(car);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

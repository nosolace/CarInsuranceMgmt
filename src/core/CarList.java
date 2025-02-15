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
import java.util.Date;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class CarList extends ArrayList<Car> {

    private static String licensePlate, owner, phone, brand;
    private static long valueOfVehicle;
    private static Date regDate;
    private static int numOfSeats;

    private String inputPlate() {
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

    private Date inputDate() {
        return ConsoleInputter.getDate("Input Date", "dd/MM/yyyy");
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

    private boolean isCarInList(String licensePlate) {
        return this.indexOf(new Car(licensePlate)) >= 0;
    }

    private Car getCar(String licensePlate) {
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
            System.out.println("Vehicle Details:");
            System.out.println(getCarDetail(licensePlate));
            boolean isDeleted = ConsoleInputter.getBoolean("Are you sure you want to delete this registration");
            if (isDeleted) {
                this.remove(getCar(licensePlate));
            }
        } else {
            System.out.println("This vehicle does not exist.");
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
/*
    Tôi không thể làm gì hơn với đoạn code này, vì nó đã quá gọn 
 */

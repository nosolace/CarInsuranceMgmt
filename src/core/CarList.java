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

    public boolean carInList(String licensePlate) {
        return this.indexOf(new Car(licensePlate)) >= 0;
    }

    public Car getCar(String licensePlate) {
        return this.get(this.indexOf(new Car(licensePlate)));
    }

    public void addCar() {
        // Các biến lưu giá trị của car
        String licensePlate, owner, phone, brand;
        long valueOfVehicle;
        Date regDate;
        int numOfSeats;
        do {
            // Nhập plate
            licensePlate = ConsoleInputter.getStr("Input license plate", Car.LICENSE_PLATE_FORMAT, "");
        } while (carInList(licensePlate)); // Nếu index > 0
        // Nhập tên
        owner = ConsoleInputter.getStr("Input owner", Car.OWNER_NAME_FORMAT, "");
        // Nhập sđt
        phone = ConsoleInputter.getStr("Input phone", Car.PHONE_FORMAT, "");
        // Nhập brand
        brand = ConsoleInputter.getStr("Input brand", Car.BRAND_CAR_FORMAT, "");
        // Nhập giá trị, max 100 tỷ =))
        valueOfVehicle = ConsoleInputter.getLongInt("Input Value of Car", 1000, 100000000000L);
        // Nhập ngày
        regDate = ConsoleInputter.getDate("Input Date", "dd/MM/yyyy");
        // Nhập ghế
        numOfSeats = ConsoleInputter.getInt("Input number of seats", 4, 36);
        // Thêm car vào danh sách
        this.add(new Car(licensePlate, owner, phone, brand, valueOfVehicle, regDate, numOfSeats));
    }

    //FindByPalate
    public void findByPlate() {
        // Nhập plate
        String licensePlate = ConsoleInputter.getStr("Input license plate", Car.LICENSE_PLATE_FORMAT, "");
        // Kiểm tra
        if (carInList(licensePlate)) {
            String seperator = "-----------------------------------------------------";
            System.out.println("Vehicle Details:");
            System.out.println(seperator);
            System.out.println(getCar(licensePlate).getCarDetail());
            System.out.println(seperator);
        } else {
            System.out.println("No one matches the search criteria!");
        }
    }

    //Update
    public void updateCarInfo() {
        //Nhập plate
        String licensePlate = ConsoleInputter.getStr("Input license plate", Car.LICENSE_PLATE_FORMAT, "");
        // Kiểm tra
        if (carInList(licensePlate)) {
            String owner, phone, brand;
            int numOfSeats;
            owner = ConsoleInputter.updateStr("Input owner", Car.OWNER_NAME_FORMAT, "");
            getCar(licensePlate).setOwner(owner);
            phone = ConsoleInputter.updateStr("Input phone", Car.PHONE_FORMAT, "");
            getCar(licensePlate).setPhone(phone);
            brand = ConsoleInputter.updateStr("Input brand", Car.BRAND_CAR_FORMAT, "");
            getCar(licensePlate).setBrand(brand);
            numOfSeats = ConsoleInputter.updateInt("Input number of seats", getCar(licensePlate).getNumOfSeats(), 4, 36);
            getCar(licensePlate).setNumOfSeats(numOfSeats);
            System.out.println("This vehicle has been updated successfully.");
        } else {
            System.out.println("This vehicle does not exist.");
        }
    }

    //Delete
    public void delete() {
        //Nhập plate
        String licensePlate = ConsoleInputter.getStr("Input license plate", Car.LICENSE_PLATE_FORMAT, "");
        // Kiểm tra
        if (carInList(licensePlate)) {

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

/*
    Danh sách bảo hiểm
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
public class InsuranceList extends ArrayList<Insurance> {
    
    private CarList cars;
    
    public InsuranceList() {
    }
    
    public InsuranceList(CarList cars) {
        this.cars = cars;
    }
    
    public void setCars(CarList cars) {
        this.cars = cars;
    }
    
    private static String id, licensePlate, startDate, beneficiary;
    private static int period;
    private static long fee;
    
    private Insurance getInserance(String id) {
        int i = this.indexOf(new Insurance(id));
        return (i >= 0) ? this.get(i) : null;
    }
    
    protected boolean isInList(String plate) {
        return this.indexOf(new Insurance(plate)) >= 0;
    }
    
    private String inputId() {
        return ConsoleInputter.getStr("Input Id", Insurance.ID_FORMAT, "Input must be exactly 4 numeric digits.");
    }
    
    private String inputPlate() {
        return cars.inputPlate();
    }
    
    private String inputStartDate() {
        Date d;
        d = ConsoleInputter.getDate("Input Date", "dd/MM/yyyy");
        return ConsoleInputter.dateStr(d, "dd/MM/yyyy");
    }
    
    private int inputPeriod() {
        Object obj = ConsoleInputter.objMenu("12", "24", "36");
        return Integer.parseInt((String) obj);
    }
    
    private long calculateFee(int period) {
        long value = cars.getCar(licensePlate).getValueOfVehicle();
        if (period == 12) {
            return (long) (0.25 * value);
        } else if (period == 24) {
            return (long) (0.4 * value);
        } else {
            return (long) (0.45 * value);
        }
    }
    
    private String inputName() {
        return ConsoleInputter.getStr("Input owner", Insurance.NAME_FORMAT, "Length must be between 2 and 25 characters.");
    }
    
    public void addInsurance() {
        id = inputId();
        licensePlate = inputPlate();
        if (cars.isCarInList(licensePlate)) {
            if (!isInList(licensePlate)) {
                startDate = inputStartDate();
                period = inputPeriod();
                fee = calculateFee(period);
                beneficiary = inputName();
                this.add(new Insurance(id, licensePlate, startDate, period, fee, beneficiary));
            } else {
                System.out.println("This car has already registed insurance");
            }
        } else {
            System.out.println("No information about this car");
        }
    }
    
    public void printByYear() {
        String y = ConsoleInputter.getStr("Input year", "^2\\d{3}$", "Range [2000 - 2999]");
        InsuranceList insurances = new InsuranceList();
        for (Insurance i : this) {
            if (y.equals(i.getStartDate().substring(i.getStartDate().lastIndexOf("/") + 1))) {
                insurances.add(i);
            }
        }
        if (insurances.isEmpty()) {
            System.out.println("There are no statements in this year");
        } else {
            String seperator = "------------------------------------"
                    + "--------------------------------------------";
            System.out.println(seperator);
            System.out.println("Report: INSURANCE STATEMENTS");
            System.out.println("From: 01/01/" + y + " To: 31/12/" + y);
            System.out.println("Sorted by: Establish Date");
            System.out.println("Sort type: ASC");
            System.out.println(seperator);
            System.out.printf("%-4s| %-5s| %-11s| %-10s| %-15s| %-7s| %-15s%n",
                    "No.", "Id", "Esta. Date", "Li. plate", "Customer", "Period", "Insurance fees");
            System.out.println(seperator);
            Collections.sort(insurances);
            for (Insurance i : insurances) {
                System.out.printf("%-4s| %-5s| %-11s| %-10s| %-15s| %7s| %,15d%n",
                        insurances.indexOf(i) + 1, i.getId(), i.getStartDate(),
                        i.getLicensePlate(), i.getBeneficiary(), i.getPeriod(), i.getFee());
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
            for (Insurance i : this) {
                oos.writeObject(i); // Ghi vào file
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
                    Insurance insur = (Insurance) ois.readObject();
                    this.add(insur);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

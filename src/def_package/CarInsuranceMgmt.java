/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package def_package;

import core.CarList;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class CarInsuranceMgmt {

    public static void main(String[] args) {
        String carFile = "data/carInfo.dat";
        CarList carList = new CarList();
        carList.loadFromFile(carFile);
        int choice; //biến menu
        boolean isChanged = false; //biến "isChange" kiểm tra thông tin đăng ký có bị thay đổi hay không
        do {
            choice = ConsoleInputter.intMenu("Add new car", "Find a car by license plate", "Update car information",
                    "Delete car information", "Add an insurance statement", "List of insurance statements",
                    "Report uninsured cars", "Save data", "Quit");

            switch (choice) {
                case 1:
                    do {
                        carList.addCar();
                    } while (ConsoleInputter.getBoolean("Add new car"));
                    isChanged = true;
                    break;
                case 2:
                    do {
                        carList.findByPlate();
                    } while (ConsoleInputter.getBoolean("Search another car"));
                    break;
                case 3:
                    do {
                        carList.updateCarInfo();
                    } while (ConsoleInputter.getBoolean("Update another car"));
                    isChanged = true;
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    carList.saveFile(carFile);
                    break;
                case 9:
                    if (isChanged == true) {
                        if (ConsoleInputter.getBoolean("Data changed. Save data to file.") == true) {
                            carList.saveFile(carFile);
                            //customerList.saveFile(customerData);
                        }
                        System.out.println("Good bye!");
                    }
                    break;
            }
        } while (choice < 9);
    }
}

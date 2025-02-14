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
        String carInfo = "data/carInfo.dat";
        CarList cars = new CarList();
        cars.loadFromFile(carInfo);
        int choice; //biến menu
        boolean isChanged = false; //biến "isChange" kiểm tra thông tin đăng ký có bị thay đổi hay không
        do {
            choice = ConsoleInputter.intMenu("Add new car", "Find a car by license plate", "Update car information",
                    "Delete car information", "Add an insurance statement", "List of insurance statements",
                    "Report uninsured cars", "Save data", "Quit");

            switch (choice) {
                case 1:
                    do {
                        cars.addCar();
                    } while (ConsoleInputter.getBoolean("Add new car"));
                    isChanged = true;
                    break;
                case 2:
                    do {
                        cars.findByPlate();
                    } while (ConsoleInputter.getBoolean("Search another car"));
                    break;
                case 3:
                    do {
                        cars.updateCarInfo();
                    } while (ConsoleInputter.getBoolean("Update another car"));
                    isChanged = true;
                    break;
                case 4:
                    cars.delete();
                    isChanged = true;
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    cars.saveFile(carInfo);
                    isChanged = false;
                    break;
                case 9:
                    if (isChanged == true) {
                        if (ConsoleInputter.getBoolean("Do you want to save the changes before exiting") == true) {
                            cars.saveFile(carInfo);
                        }
                        System.out.println("Good bye!");
                    }
                    break;
            }
        } while (choice < 9);
    }
}

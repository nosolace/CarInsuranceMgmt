/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package def_package;

import core.CarList;
import core.InsuranceList;
import tools.ConsoleInputter;

/**
 *
 * @author no-solace
 */
public class CarInsuranceMgmt {

    public static void main(String[] args) {
        String carInfo = "data/carInfo.dat";
        String insurData = "data/insurances.dat";
        CarList cars = new CarList();
        cars.loadFromFile(carInfo);
        InsuranceList iList = new InsuranceList(cars);
        iList.loadFromFile(insurData);
        cars.setInsurances(iList);

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
                        //iList.setCars(cars);
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
                        //iList.setCars(cars);
                    } while (ConsoleInputter.getBoolean("Update another car"));
                    isChanged = true;
                    break;
                case 4:
                    cars.delete();
                    //iList.setCars(cars);
                    isChanged = true;
                    break;
                case 5:
                    do {
                        iList.addInsurance();
                    } while (ConsoleInputter.getBoolean("Add another insurance"));
                    break;
                case 6:
                    do {
                        iList.printByYear();
                    } while (ConsoleInputter.getBoolean("Continue with another report"));
                    break;
                case 7:
                    cars.printUninsuredCar();
                    break;
                case 8:
                    cars.saveFile(carInfo);
                    iList.saveFile(insurData);
                    isChanged = false;
                    if (ConsoleInputter.getBoolean("Return to menu")) {
                        break;
                    } else {
                        choice = 10;
                    }
                case 9:
                    if (isChanged == true) {
                        if (ConsoleInputter.getBoolean("Do you want to save the changes before exiting") == true) {
                            cars.saveFile(carInfo);
                            iList.saveFile(insurData);
                        }
                        System.out.println("Good bye!");
                    }
                    break;
            }
        } while (choice < 9);
    }

}

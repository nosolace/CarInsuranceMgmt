/*
Tạo công cụ nhập dữ liệu
 */
package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class ConsoleInputter {

    public static Scanner sc = new Scanner(System.in);

    public static boolean getBoolean(String prompt) {
        System.out.print(prompt + " (Y/N, T/F, 1/0)?: ");
        String data = sc.nextLine().trim().toUpperCase();
        char c = data.charAt(0);
        return c == 'T' || c == 'Y' || c == '1';
    }

    public static int getInt(String prompt, int min, int max) {
        int result = 0;
        if (min > max) {
            max = min + max;
            min = max - min;
            max = max - min;
        }
        do {
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Integer.parseInt(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: " + "[" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;
    }

    public static long getLongInt(String prompt, long min, long max) {
        long result = 0;
        if (min > max) {
            max = min + max;
            min = max - min;
            max = max - min;
        }
        do {
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Long.parseLong(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: " + "[" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;
    }

    public static double getDouble(String prompt, double min, double max) {
        double result = 0;
        if (min > max) {
            max = min + max;
            min = max - min;
            max = max - min;
        }
        do {
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Double.parseDouble(sc.nextLine().trim());
            if (result < min || result > max) {
                System.out.println("Value range: [" + min + ", " + max + "]");
            }
        } while (result < min || result > max);
        return result;
    }

    public static String getStr(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine().trim();
    }

    public static String getStr(String prompt, String pattern, String errorMsg) {
        String data;
        boolean valid;
        do {
            System.out.print(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern);
            if (!valid) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return data;
    }

    public static int intMenu(Object... options) {
        int n = options.length;
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        return getInt("Choice ", 1, n);
    }

    public static Object objMenu(Object... options) {
        int choice = intMenu(options);
        return options[choice - 1];
    }

    public static Date getDate(String prompt, String dateFormat) {
        String dateStr;
        Date d;

        DateFormat formatter = new SimpleDateFormat(dateFormat);
        do {
            System.out.print(prompt + ": ");
            dateStr = sc.nextLine().trim();
            try {
                d = formatter.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Date format should be " + dateFormat + ".");
                d = null;
            }
        } while (d == null);
        return d;
    }

    public static String dateStr(Date date, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        return (date == null) ? null : formatter.format(date);
    }

    public static String getDistrict(char c) {
        Map<Character, String> district = new HashMap<>();
        String[] districts = {"Thu Duc", "District 1", "District 3", "District 4", "District 5",
            "District 6", "District 7", "District 8", "District 10", "District 11", "District 12", "Tan Phu",
            "Phu Nhuan", "Binh Tan", "Tan Binh", "Binh Thanh",
            "Go Vap", "Hoc Mon, Cu Chi", "Nha Be", "Binh Chanh"};
        char[] firstCharacters = {
            'X', 'T', 'F', 'C', 'H', 'K', 'C', 'L', 'U', 'M',
            'G', 'D', 'E', 'N', 'P', 'S', 'V', 'Y', 'Z', 'N'};
        for (int i = 0; i < firstCharacters.length; i++) {
            district.put(firstCharacters[i], districts[i]);
        }
        return district.get(Character.toUpperCase(c));
    }

    public static String updateStr(String prompt, String pattern, String errorMsg) {
        String data;
        boolean valid;
        do {
            System.out.print(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern) || data.isEmpty();
            if (!valid) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return data;
    }

    public static int updateInt(String prompt, int lastInt, int min, int max) {
        int result = 0;
        if (min > max) {
            max = min + max;
            min = max - min;
            max = max - min;
        }
        do {
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            String data = sc.nextLine().trim();
            if (data.isEmpty()) {
                return lastInt;
            } else {
                result = Integer.parseInt(data);
                if (result < min || result > max) {
                    System.out.println("Value range: " + "[" + min + ", " + max + "]");
                }
            }
        } while (result < min || result > max);
        return result;
    }
}

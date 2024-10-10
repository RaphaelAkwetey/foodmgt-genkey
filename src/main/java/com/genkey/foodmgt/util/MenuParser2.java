/*ackage com.genkey.foodmgt.util;

import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.api.MenuDAO;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.services.api.TransactionService;
import com.genkey.foodmgt.services.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;

public class MenuParser {

    @Autowired
    MenuDAO menuRepository;

    @Autowired
    static
    TransactionService transactionService;

    @Autowired
    MenuService menuService;
    private Map<String, List<String>> menuMap;
    ;
    private String[] menuList;

    public MenuParser(String[] menuList) {
        menuMap = new HashMap<>();
        this.menuList = menuList;
    }

    public MenuParser() {
        menuMap = new HashMap<>();
    }

//    public String[][] getOrderedMenuArray(){
//        String[][] orderedArray = new String[5][2];
//        int i = 0;
//        for (Map.Entry entry : getMenuMap()){
//            orderedArray[i][0] = ;
//        }
//        return orderedArray;
//    }

    public static Days findByName(String menuDay) {
        Days result = null;
        for (Days day : Days.values()) {
            if (day.name().equalsIgnoreCase(menuDay)) {
                result = day;
                break;
            }
        }
        return result;
    }

    public void parseListToMap() {
        int n = menuList.length;
        for (int i = 0; i < n; i++) {
            String line = menuList[i];
            if (line.equalsIgnoreCase(Days.Monday.toString()) || line.equalsIgnoreCase(Days.Tuesday.toString())
                    || line.equalsIgnoreCase(Days.Wednesday.toString()) || line.equalsIgnoreCase(Days.Thursday.toString())
                    || line.equalsIgnoreCase(Days.Friday.toString())) {
                for (int j = i + 1; j < n; j++) {
                    String nextLine = menuList[j];
                    if (!nextLine.equalsIgnoreCase(Days.Monday.toString()) && !nextLine.equalsIgnoreCase(Days.Tuesday.toString())
                            && !nextLine.equalsIgnoreCase(Days.Wednesday.toString()) && !nextLine.equalsIgnoreCase(Days.Thursday.toString())
                            && !nextLine.equalsIgnoreCase(Days.Friday.toString())) {
//                        List<String> valueList = new ArrayList<>();
                        String menuItem = nextLine;
//                        String lineOption = null;
//                        if (lineArray[1].contains("/")) {
//                            String[] arr = lineArray[1].split("/");
//                            String lineOption1 = arr[0];
//                            String lineOption2 = arr[1];
//                            valueList.add(lineArray[0]);
//                            valueList.add(lineOption1);
//                            valueList.add(lineOption2);
//                        } else {
//                            valueList.add(lineArray[0]);
//                            valueList.add(lineArray[1]);
//                        }
                        String menuDay = findByName(line).toString();
                        List<String> arrayList = menuMap.getOrDefault(menuDay, new ArrayList<>());
                        arrayList.add(menuItem);
                        menuMap.put(menuDay, arrayList);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static int findIdxByName(String menuDay) {
        int result = -1;
        for (Days day : Days.values()) {
            if (day.name().equalsIgnoreCase(menuDay)) {
                result++;
                break;
            } else {
                result++;
            }
        }
        return result;
    }

    public Map<String, List<String>> getMenuMap() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        int n = menuMap.entrySet().size();
        for (int i = 0; i < n; i++) {
            String day = Days.values()[i].toString();
            map.put(day, menuMap.get(day));
        }
        menuMap = map;
        return map;
    }

    public static void main(String args[]) {
//        String test = "Monday\n" +
//                "Vermicelli rice and beef stew\n" +
//                "Beans stew and rice /fried plantain\n" +
//                "Fried yam and spicy chicken\n" +
//                "Tuesday\n" +
//                "Kenkey and fish stew\n" +
//                "Waakye and fish /beef stew\n" +
//                "Rice and chicken stew\n" +
//                "Wednesday\n" +
//                "Banku and tilapia\n" +
//                "Vegie spaghetti and chicken\n" +
//                "Fried rice and chicken\n" +
//                "Thursday\n" +
//                "Fufu and beef light soup\n" +
//                "Chicken jollof and salad\n" +
//                "Fried sweet potatoes and spicy chicken\n" +
//                "Friday\n" +
//                "Omotuo and palm nut soup\n" +
//                "Banku and okro\n" +
//                "Plain rice and chicken /fish stew or Palmnut soup";
//        String[] lines = test.split("\n");
//
//        for (int i = 0; i < 4; i++) {
//            String s2 = lines[i];
//            System.out.println(s2);
//        }
//
//        MenuParser menuParser = new MenuParser(lines);
////        menuParser.parseListToMap();
//        menuParser.parseListToMap();
//        Map<String, List<String>> parseMenu = menuParser.getMenuMap();
//        parseMenu.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        });
//        menuParser.persistMenuMap();
       *//* MenuParser menuParser = new MenuParser();
        Users dave = new Users();
        Date orderDate = new Date();
        LocalDate uploadDate = LocalDate.now();
        //TransactionServiceImpl transactionService = new TransactionServiceImpl();
        Food_Order order = transactionService.parseFoodOrder(0, 2, uploadDate, dave, orderDate);
        System.out.println(order);*//*
    }

}*/
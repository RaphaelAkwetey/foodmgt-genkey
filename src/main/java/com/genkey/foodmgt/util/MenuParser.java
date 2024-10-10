package com.genkey.foodmgt.util;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.genkey.foodmgt.repository.dao.api.MenuDAO;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.services.api.TransactionService;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.services.impl.TransactionServiceImpl;

public class MenuParser {

    @Autowired
    private MenuDAO menuRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MenuService menuService;

    private LinkedHashMap<String, List<String>> menuMap;

    private String[] menuList;

    public MenuParser(String[] menuList) {
        menuMap = new LinkedHashMap<>();
        this.menuList = menuList;
    }

    public MenuParser() {
        menuMap = new LinkedHashMap<>();
    }

    public void setMenuRepository(MenuDAO menuRepository) {
        this.menuRepository = menuRepository;
    }

    public LinkedHashMap<String, List<String>> parseListToMap() {
        menuMap = new LinkedHashMap<>();

        List<String> foodOne = new ArrayList<>();
        List<String> foodTwo = new ArrayList<>();
        List<String> foodThree = new ArrayList<>();
        List<String> foodFour = new ArrayList<>();
        List<String> foodFive = new ArrayList<>();

        List<List<String>> foodLists = new ArrayList<>();
        foodLists.add(foodOne);
        foodLists.add(foodTwo);
        foodLists.add(foodThree);
        foodLists.add(foodFour);
        foodLists.add(foodFive);

        String[] daysOfWeek = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        for (int i = 0; i < daysOfWeek.length; i++) {
            String day = daysOfWeek[i];
            List<String> foodList = foodLists.get(i);

            for (int j = 0; j < menuList.length; j++) {
                String menuLine = menuList[j];

                if (menuLine.equalsIgnoreCase(day)) {
                    for (int k = j + 1; k < menuList.length; k++) {
                        String food = menuList[k];
                        if (Arrays.stream(daysOfWeek).noneMatch(food::equalsIgnoreCase)) {
                            foodList.add(food);
                        } else {
                            break;
                        }
                    }
                    menuMap.put(day, foodList);
                    break;
                }
            }
        }

        return menuMap;
    }

    static String[] Days = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday"};

    public static int findIdxByName(String menuDay) {
        int result = -1;
        for (String day : Days) {
            if (day.equalsIgnoreCase(menuDay)) {
                result++;
                break;
            } else {
                result++;
            }
        }
        return result;
    }

    public static String findByName(String menuDay) {
        String result = null;
        for (String day : Days) {
            if (day.equalsIgnoreCase(menuDay)) {
                result = day;
                break;
            }
        }
        return result;
    }

    public static void main(String args[]) {
    }

}

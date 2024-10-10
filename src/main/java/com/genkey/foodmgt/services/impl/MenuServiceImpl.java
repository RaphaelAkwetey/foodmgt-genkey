/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.dto.MealEdit;
import com.genkey.foodmgt.dto.OrderResponse;
import com.genkey.foodmgt.model.impl.*;
import com.genkey.foodmgt.repository.dao.api.*;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.util.MenuParser;
import org.hibernate.jpa.QueryHints;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 *
 * @author david
 */
//@Named("menuService")

@Transactional
@Service
public class MenuServiceImpl implements MenuService {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MetaDAO metaDAO;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MenuDAO menuRepository;

    @Autowired
    VendorDAO vendorRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    MenuDAO menuDAO;

    @Autowired
    DaysDAO daysDAO;

    @Override
    public Food_Order processOrds(String id) {
        Food_Order order = transactionRepository.findById(id).orElse(null);
        order.setStatus(Status.RECEIVED);
        return  transactionRepository.save(order);

    }

    @Override
    public Food_Order processOrdss(String id) {
        Food_Order order = transactionRepository.findById(id).orElse(null);
        order.setStatus(Status.PENDING);
        return  transactionRepository.save(order);
    }


    @Override
    public Food_Order updateOrder(String id,String userID, String food) {
        Food_Order order = transactionRepository.findById(id).orElse(null);
        String d = order.getMenuitem().getWeekDays().getId();
        LocalDate date = order.getMenuitem().getUploadDate();

        WeekDays days = daysDAO.findWeekDaysById(d);
        System.out.println(days);
        Menu ids = menuDAO.findMenuByFoodAndWeekDaysAndUploadDate(food,days,date);
        System.out.println(ids);
        String ID = ids.getId();
        System.out.println(ID);

        String orderId = order.getMenuitem().getId();
        transactionRepository.changeOrder(ID,orderId,userID);
        return order;
    }

    @Override
    public Optional<Food_Order> getOrderById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Menu> getAllMenu() {
        return (List<Menu>) menuRepository.findAll();
    }

    // test 2...
    //A test run for forking
    @Override
    public List<OrderResponse> getAllOrders() {
        return null;
    }

    @Override
    public Optional<Menu> getMenuById(String id) {
        return menuRepository.findById(id);
    }

    @Override
    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(Menu menu, String id) {
        Menu existingMenu = menuRepository.findById(id).orElse(null);
        assert existingMenu != null;
        existingMenu.setCost(menu.getCost());
        existingMenu.setFood(menu.getFood());
        menuRepository.save(existingMenu);
        return existingMenu;
    }

    @Override
    public void deleteOrder(String id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public void processOrders(List<OrderResponse> menu) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaUpdate<Food_Order> update = cb.createCriteriaUpdate(Food_Order.class);
        Root e = update.from(Food_Order.class);
        update.set("status", Status.RECEIVED);
        update.where(cb.equal(e.get("status"), Status.PENDING));
        this.em.createQuery(update).executeUpdate();
    }

    @Override
    public List<Menu> getOrdersByRange(Date startDate, Date endDate) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Menu> criteria = builder.createQuery(Menu.class);
        Root<Menu> order = criteria.from(Menu.class);
        criteria.select(order);
        criteria.where(builder.between(order.<Date>get("submitDate"), startDate, endDate));
        criteria.orderBy(builder.desc(order.get("submitDate")));
        TypedQuery<Menu> query = em.createQuery(criteria);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        query.setHint(QueryHints.HINT_CACHE_REGION, "query.Menu");
        return query.getResultList();
    }

    @Override
    public List<Menu> findBySearch(Date start, Date end) {
        return null;
    }

    public boolean persistMenuMap(LinkedHashMap<String, List<String>> map) {
        boolean result = false;
        LocalDate uploadDate = LocalDate.now();
        //menuDAO.setMenuTrue();
        Meta meta = metaDAO.findById("345").orElse(null);
        for (Map.Entry entry : map.entrySet()) {
            String dayKey = entry.getKey().toString();
            String day = MenuParser.findByName(dayKey).toString();
            LocalDate dateRange = null;
            if (day.equalsIgnoreCase("monday")){
                dateRange = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
            } else if (day.equalsIgnoreCase("tuesday")) {
                dateRange = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
            } else if (day.equalsIgnoreCase("wednesday")) {
                dateRange = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
            } else if (day.equalsIgnoreCase("thursday")) {
                dateRange = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
            } else if (day.equalsIgnoreCase("friday")) {
                dateRange = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
            }
            int dayId = MenuParser.findIdxByName(day);
            int menuItemcount = 0;
            if (entry.getValue() == null) {
                System.out.println("NPE");
            } else {
                for (Object menuItem : (ArrayList) entry.getValue()) {
                    String food = menuItem.toString();
                    try {
                        double price = meta.getCurrentFoodPrice();
                        Vendor vendor = vendorRepository.getActiveVendor();
                        Menu menu = new Menu(food, menuItemcount, price, dayId, uploadDate, vendor,"Available",dateRange);
                        System.out.println("Persisting menuItem: " + menu.toString());
                        menuRepository.save(menu);
                    } catch (Exception e) {
                        double price = 0;
                        Vendor vendor = vendorRepository.getActiveVendor();
                        Menu menu = new Menu(food, menuItemcount, price, dayId, uploadDate, vendor,"Available",dateRange);
                        System.out.println("Persisting menuItem: " + menu.toString());
                        menuRepository.save(menu);

                    }

//                menuService.addMenu(menu);
                    menuItemcount++;
                }
            }
        }
        return result;
    }

    public Map<String, List<String>> O_C_R(){
        List<MealEdit> meal = menuDAO.retrieveMealsForDisplay();
        Map<String, List<String>> foodMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String day1, String day2) {
                // Define the custom order of days
                List<String> daysOrder = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
                int index1 = daysOrder.indexOf(day1);
                int index2 = daysOrder.indexOf(day2);
                return Integer.compare(index1, index2);
            }
        });

        for (MealEdit item : meal) {
            String day = item.getDays();
            String food = item.getFood();

            if (!foodMap.containsKey(day)) {
                foodMap.put(day, new ArrayList<>());
            }

            foodMap.get(day).add(food);
        }
        return foodMap;
    }
}

//    @Inject
 //   @Named("menuDAO")
  //  private MenuDAOImpl menuDao;

   // public List retrieveMenu(Vendor vendor) {
   //     return menuDao.retrieveMenu(vendor);
  //  }

//    protected MenuDAOImpl getMenuDao() {
//        return menuDao;
//    }


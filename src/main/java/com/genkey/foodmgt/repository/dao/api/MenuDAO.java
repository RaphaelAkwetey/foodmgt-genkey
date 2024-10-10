/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.dto.MealEdit;
import com.genkey.foodmgt.dto.OrderResponse;
import com.genkey.foodmgt.dto.SpecificUserOrders;
import com.genkey.foodmgt.dto.foodByDays;
import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.model.impl.WeekDays;
import com.genkey.foodmgt.model.impl.ocrDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author david
 */
@Transactional
@Repository
public interface MenuDAO extends JpaRepository<Menu, String> {


 @Modifying
 @Query(value = "delete from menu m where m.upload_date = cast(:date as date)",nativeQuery = true)
 void deleteByUploadDate(LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.ExpiryDate < :expiryDate AND m.status = :status")
    List<Menu> findByExpiryDateLessThanAndStatus(LocalDate expiryDate, String status);

 @Modifying
 @Query(value = "update Menu m set m.status='Unavailable' where m.status='Available'")
 void setMenuTrue();

    @Query(value = "select * from menu m where m.status = 'Available' " + " and m.days_id = cast(:dayPosition as varchar) and " +
            " m.food = :orderPosition ", nativeQuery = true)
    Optional<Menu> getMenuItem(int dayPosition, String orderPosition);

    @Query("SELECT m FROM Menu m WHERE m.status = 'Available' AND m.weekDays.id = :dayPosition AND m.food = :orderPosition")
    Optional<Menu> getMenuItemFallback(@Param("dayPosition") int dayPosition,
                                       @Param("orderPosition") String orderPosition);


    Menu findMenuByFoodAndWeekDaysAndUploadDate (String food, WeekDays weekDays,LocalDate date);

   @Query(value = "select * from menu where days_id = :id and upload_date = cast(cast(:sd as text) as timestamp)",nativeQuery = true)
   List<Menu> MondayFood(String id,LocalDate sd);

   //List<Menu> findMenuByWeekDays(WeekDays weekDays);

    @Transactional
    @Query(value = "SELECT new com.genkey.foodmgt.dto.foodByDays(m.food)" + "from Menu m where m.weekDays = :days and m.status = 'Available' " +
            " group by m.food")
    List<foodByDays> moon(WeekDays days);

    @Transactional
    @Query(value = "SELECT new com.genkey.foodmgt.dto.foodByDays(m.food)" + "from Menu m where m.status = 'Available' ")
    List<foodByDays> sun();

    @Query(value = "SELECT new com.genkey.foodmgt.dto.MealEdit(m.id, d.days, m.food,m.status) FROM Menu m join WeekDays d on m.weekDays = d.id where m.weekDays.days = :day and m.status = 'Available' group by m.id," +
            "d.days,m.food, m.status")
    List<MealEdit> retrieveSpecifiedMeals(String day);

    @Query(value = "SELECT new com.genkey.foodmgt.dto.MealEdit(m.id, d.days, m.food,m.status) FROM Menu m join WeekDays d on m.weekDays = d.id where m.status = 'Available' group by m.id," +
            "d.days,m.food, m.status")
    List<MealEdit> retrieveMeals();


 @Query(value = "SELECT new com.genkey.foodmgt.dto.MealEdit(d.days, m.food) FROM Menu m join WeekDays d on m.weekDays = d.id where m.status= 'Available' group by " +
         "d.days,m.food" +
         " ORDER BY d.days ASC")
 List<MealEdit> retrieveMealsForDisplay();


    @Modifying
    @Query(value = "UPDATE Menu m set m.status = :status where m.id = :id")
    void editMenuIndividually(String status, String id);

    @Modifying
    @Query(value = "UPDATE Menu m set m.status = 'Available' where m.uploadDate = cast(cast(:date as text) as timestamp)")
    void setMenuToAvailable(LocalDate date);


  /* @Query(value = "select * from menu where menu_item_position = :position and days_id = :day",nativeQuery = true)
   Menu bringThem(int position, int day);
*/
   /* @Query(value = "SELECT u.firstname,m.food,m.cost FROM menu m JOIN users u ON m.username = u.username", nativeQuery = true)
    List<OrderResponse> ShowOrders();*/

    //Dashboard query
    /*@Query("SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname,m.food,m.cost)" + "FROM Users u, Menu m")
    List<OrderResponse> OrdersAndUsers();*/

    // pending individual orders
   /* @Query(value="SELECT new com.genkey.foodmgt.dto.SpecificUserOrders(m.food,m.cost,m.created)" + "FROM Menu m ")
    List<SpecificUserOrders> OnlyPendingSpecificUserOrders(Users users);

    @Query(value="SELECT new com.genkey.foodmgt.dto.SpecificUserOrders(m.food,m.cost,m.created)" + "FROM Menu m")
    List<SpecificUserOrders> OnlyAcceptedSpecificUserOrders(Users users);*/

    //this query gets the list of orders for the week
 @Query(
         value = "select " +
                 "  * " +
                 "from menu t " +
                 "where " +
                 "  t.created >= cast(cast(:start as text) as timestamp) is null or t.created <= cast(cast(:end as text) as timestamp)",
         nativeQuery = true
 )
 List<Menu> GetListOfOrdersForThisWeek(Optional<LocalDate> start, Optional<LocalDate> end);

 @Query(
         value = "select " +
                 "  SUM(cost) " +
                 "from menu t " +
                 "where " +
                 "  t.created >= cast(cast(:start as text) as timestamp) is null or t.created <= cast(cast(:end as text) as timestamp)"
         + "and t.username = :username",
         nativeQuery = true
 )
 double RetrieveUserTransactionsCostSumForSpecificPeriod (Optional<LocalDate> start, Optional<LocalDate> end, String username);


 @Query(
         value = "select " +
                 "   " +
                 "from menu t " +
                 "where " +
                 "  t.created >= cast(cast(:start as text) as timestamp) is null or t.created <= cast(cast(:end as text) as timestamp)",
         nativeQuery = true
 )
 double GetThisMonthsTotalCostOfFood(Optional<LocalDate> start, Optional<LocalDate> end);

 @Query(
         value = "select " +
                 "  SUM(cost) " +
                 "from menu t " +
                 "where " +
                 "  t.created >= cast(cast(:start as text) as timestamp) is null or t.created <= cast(cast(:end as text) as timestamp)",
         nativeQuery = true
 )
 double RetrieveAllTransactionCostSumForSpecificPeriod(Optional<LocalDate> start, Optional<LocalDate> end);

 @Query(
         value = "select " +
                 "  * " +
                 "from menu t " +
                 "where " +
                 "  t.created >= cast(cast(:start as text) as timestamp) is null or t.created <= cast(cast(:end as text) as timestamp)"
         + "and t.username = :username",
         nativeQuery = true
 )
 List<Menu> RetrieveUserTransactionForSpecificPeriod(Optional<LocalDate> start, Optional<LocalDate> end, String username);

List<Menu> findAllByStatus(String status);
List<Menu> findAllByStatusAndAndWeekDays(String status, WeekDays weekDays);

}

package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.dto.*;
import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.Status;
import com.genkey.foodmgt.model.impl.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Food_Order, String> {
    @Query(value = "select m.food,m.menuItemPosition,m.uploadDate from Food_Order f join Menu m on f.menuitem = m.id where f.id = :id")
    Food_Order getOrder(String id);
    //this is to get all orders for a specific Day.

    @Query(value = "SELECT new com.genkey.foodmgt.dto.FoodOrderDto(u.firstname,m.food,m.cost)" + " from Food_Order f " +
            "join Users u on f.user = u.id" + " join Menu m on f.menuitem = m.id" + " where  f.deliveryDate = cast(cast(:sd as text) as timestamp)"
    + " and f.status = 'PENDING'")
    List<FoodOrderDto> getAllOrdersForToday(LocalDate sd);

    //this is to get all Pending orders for Admin to process.
    @Query(value = "SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname,m.food,m.cost,f.status,f.transactionShorterDate,f.id)" + " from Food_Order f " +
            "join Users u on f.user = u.id" + " join Menu m on f.menuitem = m.id" + " where " +
            " f.deliveryDate > cast(cast(:start as text) as timestamp) and f.deliveryDate < cast(cast(:end as text) as timestamp) group by u.firstname,m.food,m.cost,f.status,f.transactionShorterDate,f.id ")
    List<OrderResponse> getAllPendingOrdersAndUser(LocalDate start, LocalDate end);

    @Transactional
    @Query(value = "SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname,m.food,f.transactionShorterDate,m.cost,f.created)" + " from Food_Order f " +
            "join Users u on f.user = u.id" + " join Menu m on f.menuitem = m.id" + " where f.status = 'RECEIVED' and u.firstname = :firstname " +
            " and f.deliveryDate between cast(cast(:start as text) as timestamp) and cast(cast(:end as text) as timestamp) group by u.firstname,m.food,f.transactionShorterDate,m.cost,f.created " +
            "ORDER BY f.created ASC, f.transactionShorterDate ASC")
    List<OrderResponse> getAllAcceptedOrdersAndUser(String firstname,LocalDate start, LocalDate end);


    @Transactional
    @Query(value = "SELECT (SUM(m.cost))" + " from Food_Order f " +
            "join Users u on f.user = u.id" + " join Menu m on f.menuitem = m.id" + " where f.status = 'RECEIVED' and u.firstname = :firstname " +
            " and f.deliveryDate >= cast(cast(:start as text) as timestamp) and f.deliveryDate <= cast(cast(:end as text) as timestamp) group by m.cost "
            )
    Double getTotalCostOfAllAcceptedOrdersAndUser(String firstname,LocalDate start, LocalDate end);

    @Query(value = "SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname, m.food, f.transactionShorterDate, m.cost, f.created) " +
            "FROM Food_Order f " +
            "JOIN Users u ON f.user = u.id " +
            "JOIN Menu m ON f.menuitem = m.id " +
            "WHERE f.status = 'RECEIVED' " +
            "AND f.deliveryDate >= cast(cast(:start as text) as timestamp) and f.deliveryDate <= cast(cast(:end as text) as timestamp) " +
            "GROUP BY u.firstname, m.food, f.transactionShorterDate, m.cost, f.created " +
            "ORDER BY f.created ASC, f.transactionShorterDate ASC")
    List<OrderResponse> getAllAcceptedOrdersAndUser2(LocalDate start, LocalDate end);


    @Query(value = "SELECT SUM(m.cost) " +
            "FROM Food_Order f " +
            "JOIN Users u ON f.user = u.id " +
            "JOIN Menu m ON f.menuitem = m.id " +
            "WHERE f.status = 'RECEIVED' " +
            "AND f.deliveryDate >= cast(cast(:start as text) as timestamp) " +
            "AND f.deliveryDate <= cast(cast(:end as text) as timestamp)")
    Double getTotalCostOfAllAcceptedOrdersAndUser2(LocalDate start, LocalDate end);


    @Query(value = "SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname,m.food,m.cost,f.deliveryDate)" + " from Food_Order f " +
            "join Users u on f.user = u.id" + " join Menu m on f.menuitem = m.id" + " where f.status = 'RECEIVED'" +
            " and u.firstname = :username")
    List<OrderResponse> getAllTransactionOfSpecificUser(String username);

    @Query(value = "SELECT new com.genkey.foodmgt.dto.SpecificUserOrders(m.food, m.cost, f.created, f.transactionShorterDate)"
            + " FROM Food_Order f JOIN Menu m ON f.menuitem = m.id"
            + " WHERE f.user = :id AND f.status = 'RECEIVED' ORDER BY f.created DESC")
    Page<SpecificUserOrders> getOnlyAcceptedSpecificUserOrders(Users id, Pageable pageable);

    @Query(value = "SELECT new com.genkey.foodmgt.dto.SpecificUserOrders(m.food,m.cost,f.status,f.transactionShorterDate,f.id)" + " from Food_Order f "
            + "join Menu m on f.menuitem = m.id" + " where f.user = :id and f.status = 'PENDING'")
    List<SpecificUserOrders> getOnlyPendingSpecificUserOrders(Users id);


    /*@Query(value = "SELECT new com.genkey.foodmgt.dto.OrderSummary(distinct m.food,count(m.menuItemPosition),sum(m.cost),f.deliveryDate)" + " from Food_Order f join Menu m on f.menuitem = m.id join Users u on f.user = u.username" +
            " group by u.username, m.food")
    OrderSummary getOrderSummary();*/

    //this query is for getting daily order summary.
    @Transactional(readOnly = true)
    @Query(value = "select new com.genkey.foodmgt.dto.OrderSummary(m.food,SUM(m.cost),COUNT(m.menuItemPosition))" + " from Food_Order f " +
            " join Menu m on f.menuitem = m.id where f.status = 'RECEIVED'"
            + " and f.deliveryDate = cast(cast(:sd as text) as timestamp)  " +
            "group by m.food,m.cost,m.menuItemPosition")
    List<OrderSummary> getSummary(LocalDate sd);


    //@Transactional(readOnly = true)
    @Transactional(readOnly = true)
    @Query(value = "select new com.genkey.foodmgt.dto.OrderSummary(m.food,COUNT(m.menuItemPosition))" + " from Food_Order f " +
            " join Menu m on f.menuitem = m.id where f.status = 'PENDING'"
            + " and f.deliveryDate > cast(cast(:sd as text) as timestamp) and f.deliveryDate < cast(cast(:ed as text) as timestamp) group by m.food, m.menuItemPosition")
    List<OrderSummary> getTodaySummary(LocalDate sd,LocalDate ed);

    @Transactional(readOnly = true)
    @Query(value = "select new com.genkey.foodmgt.dto.OrderSummary(m.food,COUNT(m.menuItemPosition))" + " from Food_Order f " +
            " join Menu m on f.menuitem = m.id where f.status = 'PENDING'"
            + " and f.deliveryDate = cast(cast(:sd as text) as timestamp) group by m.food, m.menuItemPosition")
    List<OrderSummary> getTodaySummaryByDate(LocalDate sd);

    @Transactional(readOnly = true)
    @Query(value = "select new com.genkey.foodmgt.dto.Summary(u.firstname, COUNT(m.menuItemPosition))" + " from Food_Order f " +
            " join Users u on f.user = u.id" +
            " join Menu m on f.menuitem = m.id where f.status = 'RECEIVED'"
            + " and f.deliveryDate between cast(cast(:sd as text) as timestamp) and cast(cast(:ed as text) as timestamp) group by u.firstname ")
    List<Summary> getSpecificSummary(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed);

    @Transactional(readOnly = true)

    @Query(value = "select new com.genkey.foodmgt.dto.Summary(f.id, u.firstname, m.food, f.deliveryDate, f.transactionShorterDate, f.status)" +
            " from Food_Order f " +
            " join Users u on f.user = u.id" +
            " join Menu m on f.menuitem = m.id where f.status = 'PENDING'" +
            " group by f.id, u.firstname,m.food, f.deliveryDate," +
            " f.transactionShorterDate, f.status")
    Page<Summary> receiveSpecificOrders(Pageable pageable);


    //this query is used in calculating the total bill of all user spending.
    @Transactional(readOnly = true)
    @Query(value = "select new com.genkey.foodmgt.dto.BillDto(u.firstname,sum(m.cost),u.userRoles)" + " from Food_Order f " +
            "join Users u on f.user = u.id " + " join Menu m on f.menuitem = m.id" + " where f.deliveryDate between cast(cast(:sd as text) as timestamp) and cast(cast(:ed as text) as timestamp)"
            + " and f.status = 'RECEIVED' group by u.firstname, m.cost, u.userRoles ")
    List<BillDto> getOrderSummary(LocalDate sd, LocalDate ed);


    @Query(value = "SELECT new com.genkey.foodmgt.dto.BillDto(u.username,sum(m.cost))" + " from Food_Order f join Menu m on f.menuitem = m.id join Users u on f.user = u.username" +
            " group by u.username")
    List<BillDto> ForBill();

    /*select distinct u.username,sum(m.cost) from Food_Order f join Menu m on f.food_id = m.id join Users u on f.username = u.username
    where f.status = 'ACCEPTED'  group by u.username*/

    /*select distinct u.username,u.user_roles,sum(m.cost) from Food_Order f join Menu m on f.food_id = m.id join Users u on f.username = u.username
    group by u.username, u.user_roles*/

    @Query(value = "select distinct u.username, sum(m.cost) from Food_Order f join Menu m on f.menuitem = m.id join Users u on f.user = u.id" +
            "  group by u.username")
    List<Food_Order> BillCall();

    @Query(value = "select count(food_id) from food_order f where f.status = 'PENDING' and f.delivery_date > cast(cast(:sd as text) as timestamp) and f.delivery_date < cast(cast(:ed as text) as timestamp)",nativeQuery = true)
    Integer pendingOrders(LocalDate sd, LocalDate ed);

    @Transactional
    @Query(value = "select sum(m.cost) from food_order f join menu m on f.food_id = m.id" +
            " where f.status = 'PENDING' and f.delivery_date > cast(cast(:sd as text) as timestamp) and f.delivery_date < cast(cast(:ed as text) as timestamp)",nativeQuery = true)
    Double TotalSumOfPendingOrders(LocalDate sd,LocalDate ed);

    @Transactional
    @Query(value = "select sum(m.cost) from food_order f join menu m on f.food_id = m.id" +
            " where f.status = 'RECEIVED' and f.user_id = :id and f.created > cast(cast(:sd as text) as timestamp) and f.created < cast(cast(:ed as text) as timestamp)",nativeQuery = true)
    Double TotalSumOfUserMonthlySpending(String id,LocalDate sd,LocalDate ed);

   /*@Transactional
    @Query(value = "select sum(m.cost) from food_order f join menu m on f.food_id = m.id" +
            " where f.status = 'RECEIVED' and f.created >= cast(cast(:sd as text) as timestamp)",nativeQuery = true)
    Double TotalMonthlyBill(Date sd);*/

    @Transactional
    @Query(value = "select sum(m.cost) from food_order f join menu m on f.food_id = m.id" +
            " where f.status = 'RECEIVED' and f.delivery_date >= cast(cast(:sd as text) as timestamp) and f.delivery_date <= cast(cast(:ed as text) as timestamp)",nativeQuery = true)
    Double TotalMonthlyBill(Date sd,Date ed);





    @Modifying
    @Query(value = "UPDATE food_order " +
            "SET status = 'RECEIVED' " + " WHERE status = 'PENDING' and food_id = :id ",nativeQuery = true)
           void processIndividualOrders(String id);



    @Modifying
    @Query(value = "UPDATE food_order " +
            "SET food_id = :ID " + " WHERE food_id = :id and user_id = :userId and status = 'PENDING'",nativeQuery = true)
    void changeOrder(String ID,String id,String userId);


    @Query(value = "select count(f.food_id) from food_order f where f.status = 'RECEIVED' and f.delivery_date >= cast(cast(:sd as text) as timestamp) and f.delivery_date <= cast(cast(:ed as text) as timestamp)",nativeQuery = true )
    Integer TotalNumOfFood(LocalDate sd,LocalDate ed);


    @Query(value = "SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname,m.food,m.cost,f.status,f.transactionShorterDate,f.id)" + " from Food_Order f " +
            "join Users u on f.user = u.id" + " join Menu m on f.menuitem = m.id" + " where f.status = :stats and " +
            " f.deliveryDate > cast(cast(:start as text) as timestamp) and f.deliveryDate < cast(cast(:end as text) as timestamp) group by u.firstname,m.food,m.cost,f.status,f.transactionShorterDate,f.id ")
    List<OrderResponse> filterAllOrders(Status stats, LocalDate start, LocalDate end);

    @Query(value = "SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname,m.food,m.cost,f.status,f.transactionShorterDate,f.id)" + " from Food_Order f " +
            "join Users u on f.user = u.id" + " join Menu m on f.menuitem = m.id" + " where " +
            " f.deliveryDate = cast(cast(:start as text) as timestamp)  group by u.firstname,m.food,m.cost,f.status,f.transactionShorterDate,f.id ")
    List<OrderResponse> getAllPendingOrdersAndUsers(LocalDate start);

}

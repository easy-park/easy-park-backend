package com.oocl.easyparkbackend.ParkingOrder.Repository;

import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Repository.CustomerRepository;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ParkingOrderRepositoryTest {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void should_return_parkingOrder_list_when_invoke_findAllByStatus_given_status(){
        ParkingBoy parkingBoy = new ParkingBoy("123","123","123","sdfsf",1,"12345",new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        ParkingLot parkingLot = new ParkingLot("123","456",5,5);
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("123","eree",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),5.0,6,parkingBoy,parkingLot);

        parkingOrderRepository.save(parkingOrder);
        List<ParkingOrder> parkingOrderList = parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy,6);

        assertThat(parkingOrderList.get(0).getCarNumber().equals("eree"));
        assertThat(parkingOrderList.get(0).getParkingLot().getAvailable().equals(5));

    }

    @Test
    public void should_return_parkingLot_list_when_find_parkingBoy_by_id(){
        ParkingLot parkingLot1 = new ParkingLot("124","456",5,5);
        ParkingLot parkingLot2 = new ParkingLot("122","476",5,5);
        ParkingLot parkingLot3 = new ParkingLot("121","466",5,5);
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingLot1);
        parkingLotList.add(parkingLot2);
        parkingLotList.add(parkingLot3);
        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);
        parkingLotRepository.save(parkingLot3);
        ParkingBoy parkingBoy = new ParkingBoy("123","123","123","sdfsf",1,"12345",parkingLotList);
        ParkingBoy returnParkingBoy = parkingBoyRepository.save(parkingBoy);

        List<ParkingLot> returnParkingLotList = parkingBoyRepository.findById(returnParkingBoy.getId()).get().getParkingLotList();

        assertThat(returnParkingLotList.get(0).getId().equals("124"));
        assertThat(returnParkingLotList.get(0).getId().equals("122"));
        assertThat(returnParkingLotList.get(0).getId().equals("121"));
    }

    @Test
    public void should_update_parkingOrder_status_when_invoke_save_given_parkingOrder(){
        ParkingBoy parkingBoy = new ParkingBoy("123","123","123","sdfsf",1,"12345",new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        ParkingLot parkingLot = new ParkingLot("224","456",5,5);
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("324","eree",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),5.0,1,parkingBoy,parkingLot);

        ParkingOrder returnParkingOrder = parkingOrderRepository.save(parkingOrder);
        returnParkingOrder.setStatus(2);
        ParkingOrder resultParkingOrder = parkingOrderRepository.save(returnParkingOrder);

        assertThat(resultParkingOrder.getStatus().equals(2));
    }

    @Test
    public void should_return_parkingBoy_unfinished_orders_and_will_fetch_first_given_parkingBoy_id() {
        ParkingBoy parkingBoy1 = new ParkingBoy("username","199729","stefan","13192269125",1,"953181215@qq.com",null);
        ParkingBoy parkingBoy2 = new ParkingBoy("username","199729","stefan","13192269125",1,"953181215@qq.com",null);
        ParkingOrder order1 = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy1, null);
        ParkingOrder order2 = new ParkingOrder("2", "55554", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy1, null);
        ParkingOrder order3 = new ParkingOrder("3", "55556", new Timestamp(System.currentTimeMillis()), null, null, 4, parkingBoy1, null);
        ParkingOrder order4 = new ParkingOrder("4", "55557", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy2, null);
        parkingBoyRepository.save(parkingBoy1);
        parkingBoyRepository.save(parkingBoy2);
        parkingOrderRepository.save(order1);
        parkingOrderRepository.save(order2);
        parkingOrderRepository.save(order3);
        parkingOrderRepository.save(order4);

        List<ParkingOrder> fetchOrders = parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy1, 4);
        fetchOrders.addAll(parkingOrderRepository.findAllByParkingBoyAndStatus( parkingBoy1, 3));

        assertThat(fetchOrders.get(0).getId().equals("3"));
        assertEquals(fetchOrders.size(), 3);
        assertThat(fetchOrders.get(2).getCarNumber().equals("55554"));
    }

    @Test
    public void should_get_parkingOrder_when_invoke_findBycarNumberAndendTime(){
        ParkingOrder parkingOrder = new ParkingOrder("324","eree",new Timestamp(new Date().getTime()),null,null,1,null,null);
        parkingOrderRepository.save(parkingOrder);

        ParkingOrder returnParkingOrder = parkingOrderRepository.findByCarNumberAndEndTime("eree",null);

        assertThat(returnParkingOrder.getCarNumber().equals("eree"));
    }


    @Test
    public void should_return_parkingOrder_list_when_invoke_findAllByStatus(){
        ParkingOrder parkingOrder = new ParkingOrder("324","eree",new Timestamp(new Date().getTime()),null,null,1,null,null);
        parkingOrderRepository.save(parkingOrder);

        List<ParkingOrder> parkingOrderList = parkingOrderRepository.findAllByStatus(1);

        assertThat(parkingOrderList.get(0).getCarNumber()).isEqualTo("eree");
    }

    @Test
    public void should_return_parkingOrder_list_when_invoke_findByCustomerAndStatus() {
        Customer customer = new Customer();
        customer.setName("sean");
        customer.setUsername("sean");
        customer.setPhone("15574957517");
        customer.setPassword("123");
        ParkingOrder parkingOrder = new ParkingOrder("324","eree",new Timestamp(new Date().getTime()),null,null,6,null,null);
        parkingOrder.setCustomer(customer);
        Customer returnCustomer = customerRepository.save(customer);
        parkingOrderRepository.save(parkingOrder);


        List<ParkingOrder> parkingOrderList = parkingOrderRepository.findAllByCustomerAndStatus(returnCustomer,6);

        assertThat(parkingOrderList.get(0).getCustomer().getName()).isEqualTo(customer.getName());
    }


}

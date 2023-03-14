package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * V1. 엔티티 직접 노출
 * - 엔티티가 변하면 API 스펙이 변한다.
 * - 트랜잭션 안에서 지연 로딩 필요
 * - 양방향 연관관계 문제
 *
 * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
 * - 트랜잭션 안에서 지연 로딩 필요
 * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
 * - 페이징 시에는 N 부분을 포기해야함(대신에 batch fetch size? 옵션 주면 N -> 1 쿼리로 변경
 가능)
 *
 * V4. JPA에서 DTO로 바로 조회, 컬렉션 N 조회 (1 + N Query)
 * - 페이징 가능
 * V5. JPA에서 DTO로 바로 조회, 컬렉션 1 조회 최적화 버전 (1 + 1 Query)
 * - 페이징 가능
 * V6. JPA에서 DTO로 바로 조회, 플랫 데이터(1Query) (1 Query)
 * - 페이징 불가능...
 *
 */


@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    private final OrderQueryRepository orderQueryRepository;



    @GetMapping("/api/v1/orders")
    public Result orderV1(){
        List<Order> all=orderRepository.findAllByString(new OrderSearch());
        for(Order order :all){
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o->o.getItem().getName());
        }
        return  new Result(all);
    }


    @Data
    @AllArgsConstructor
    static  class Result<T>{
        private T data;
    }


    @GetMapping("/api/v2/orders")
    public Result ordersV2(){
        List<Order> orders=orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return  new Result(result);
    }


    //V3: 엔티티를 DTO로 변환 - 페치 조인 최적화
    @GetMapping("/api/v3/orders")
    public Result ordersV3(){
        List<Order> orders=orderRepository.findAllWithItem();

        for(Order order :orders){
            System.out.println("order = " + order.getId());
        }
        List<OrderDto> result =orders.stream().map(o->new OrderDto(o)).collect(Collectors.toList());
        return  new Result(result);
    }



    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value="offset", defaultValue = "0") int offset,
            @RequestParam(value="limit", defaultValue = "100") int limit){

        List<Order> orders=orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result =orders.stream().map(o->new OrderDto(o)).collect(Collectors.toList());
        return result;
    }


    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> orderV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }



    @Getter
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime oderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;


        public OrderDto(Order order) {
            orderId=order.getId();
            name=order.getMember().getName();
            oderDate=order.getOrderDate();
            orderStatus=order.getStatus();
            address=order.getDelivery().getAddress();
            order.getOrderItems().stream().forEach(o->o.getItem().getName()); //프록시 초기화

            orderItems=order.getOrderItems().stream().map(orderItem->new OrderItemDto(orderItem)).collect(Collectors.toList());
        }


    }

    @Getter
    static  class  OrderItemDto{
        private String itemName;//상품 명
        private int count; //주문 수량
        private int orderPrice; //주문 가격

        public  OrderItemDto(OrderItem orderItem){
            itemName=orderItem.getItem().getName();
            orderPrice=orderItem.getOrderPrice();
            count=orderItem.getCount();
        }
    }

}




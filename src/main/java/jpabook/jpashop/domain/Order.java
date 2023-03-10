package jpabook.jpashop.domain;

import jdk.jfr.Timestamp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
//Order 객체 생성시 createOrder 메소들 이용하여 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //CascadeType.ALL 는 order
//    persist(orderItemA);
//    persist(orderItemA);
//    persist(orderItemB);
//    persist(orderItemB);
//    persist(order);

   // =>persist를 각각 해줘야 하는데 CascadeType.ALL  적용하면  persist(order); 한번에 적용된다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems =new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Comment("주문시간")
    private LocalDateTime orderDate; //주문시간

    @Comment("주문상태")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문상태 [ORDER, CANCEL]



    //==연관관계 메서드=//
    public void setMember(Member member){
        this.member =member;
        member.getOrders().add(this);
    }

    //Order  + OrderItem 엔티티 영속성 객체 주입
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    public void setDeliver(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }


    //==생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems ){

       Order order =new Order();
       order.setMember(member);
       order.setDeliver(delivery);

       for(OrderItem orderItem : orderItems){
           //엔티티 영속성 객체 주입
           order.addOrderItem(orderItem);
       }
       order.setStatus(OrderStatus.ORDER);
       order.setOrderDate(LocalDateTime.now());

       return  order;
    }



    //===비즈니스 로직 ==//
    /** 주문 취소 */
    public void cancel(){
        //데이터만 바뀌면 JPA 가 더티 체킹으로 알아서 update 처리를 해준다.
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem :this.orderItems){
            orderItem.cancel();
        }
    }

    /** 전체 주문 가격 조회 */
    //==조회 로직 ==//
    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

//    public int getTotalPrice(){
//        int totalPrice=0;
//        for(OrderItem orderItem :orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return  totalPrice;
//    }



}

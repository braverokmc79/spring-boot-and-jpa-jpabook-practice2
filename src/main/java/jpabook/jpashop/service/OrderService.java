package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;


    /** Order 객체 에서  orderItems  ,  delivery 가
     * cascade = CascadeType.ALL 적용 되어 있어서 현재 영속성 주입인  save 할 필요가 없는데,
     * CascadeType.ALL 의 사용은 현재 orderItems 이나 delivery 가 Order 에서만
     * 사용하고 있어서 가능하다. 만약에 다른 객체에서도 사용하고 있다면,
     * CascadeType.ALL 을 사용해서는 안된다.
     */

    /**주문 **/
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티
        Member member =memberRepository.findOne(memberId);
        Item item =itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery =new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem =OrderItem.createOrderItem(item, item.getPrice(), count);

        // 다음과 같이 기본생성 자로 주문상품을 생성할 가능성이 있기 때문에
        //protected 처릴르 하여  막는다.
        //OrderItem orderItem1 =new OrderItem();
        //@NoArgsConstructor(access = AccessLevel.PROTECTED)

        //주문 생성
        //Order  @NoArgsConstructor(access = AccessLevel.PROTECTED)
        Order order = Order.createOrder(member, delivery, orderItem);



        //주문 저장
        //주문을 저장할때  cascade = CascadeType.ALL 옵션이 있기 때문에
        //delivery 와  orderItem 은 자동으로 함게 persist 되면서 DB 에 함께 저장 처리 된다.
        orderRepository.save(order);

        return  order.getId();
    }


    /**
     *  취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order=orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }


    //검색

    public List<Order> findOrders(OrderSearch orderSearch){
        return  orderRepository.findAll(orderSearch);
    }


}

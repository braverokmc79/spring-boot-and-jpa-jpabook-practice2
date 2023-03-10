package jpabook.jpashop.repository;


import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository

@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){
            //신규 등록
            em.persist(item);
        }else{
            //item.getId()  있으면 업데이트 의미라 생각
            em.merge(item);
            em.flush();
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return  em.createQuery("select i from Item i" , Item.class).getResultList();
    }


}

package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item){
        itemRepository.save(item);
    }


    /**
     *
     merge 가 다음 메서드 코드와 동일하다고 생각하면 된다.
     merge 반환된 값은 영속성 컨텍트이다.
     ★★  주의 모든  필드들 교체  된다. 빠진 필드 객체들은 null 값으로 등록 처리, 따라서
     /왠만하면은 사용 하지마라!
     em.merge(item);
     em.flush();
     */
    @Transactional
    public Item updateItem(Long itemId, Book param){
        Item findItem =itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());

        return findItem;
    }


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}

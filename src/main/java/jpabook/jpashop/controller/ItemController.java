package jpabook.jpashop.controller;


import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String crateForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }


    @PostMapping("/items/new")
    public String crate(BookForm form){
        Book book=new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }



    @GetMapping("/items")
    public String items(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item=(Book) itemService.findOne(itemId);

        BookForm form =new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }


    /**
     * 상품 수정
     */
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form) {

        
        //아이디가 form.getId() 셋팅으로 JPA 가 식별할 수 있는  상태로 준영속성 객체
        //준영속성 엔티티 : 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다.
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        //데이터가 영속적인 상태라면 , 데이터만 변경해 처리하면  자동으로 더티체킹으로  업데이트 처리 되는데,
        //여기서는 book 은  DB 에서 데이틀 가져와서온 영속적인 상태가 아니라 ,
        //새롭게 북객체를 만들고 해당 아이디를 가지고 업데이트 처리를 하고 있다.
        itemService.saveItem(book);
        return "redirect:/items";
    }



}

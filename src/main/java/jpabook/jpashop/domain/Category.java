package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String name;

    //여기서 name=category_item 이름은 중간 테이블인 category_item 테이블명을을 매핑해준다는 의미이다.
    @ManyToMany
    @JoinTable(name="category_item",
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name="item_id")
    )
    private List<Item> items=new ArrayList<>();


    /** 같은 엔티티 셀프  */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private  Category parent;


    @OneToMany(mappedBy ="parent" )
    private List<Category> child=new ArrayList<>();


    //== 연관관계 메서드 ==//
    public void adddChildCateogy(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}

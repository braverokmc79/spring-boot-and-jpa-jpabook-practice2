package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;


/**
 *  엔티티의 값이 공유되면 전 a, b,c.. 모두 변경 처리 될수 있기때문에
 *   @Embeddable클래스를 불변 절대 공유되서는 안게 해준다.ㄴㄴ
 *  따라서, setter 은 사용하지 않는다.
 */
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //함부로 new 로 생성처리 하면 안된다.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

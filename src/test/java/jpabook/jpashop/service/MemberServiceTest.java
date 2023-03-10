package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // @Transactional 이 테스트 클래스에 존재하면 기본적으로  롤백 처리한다.
public class MemberServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;


    @Test
    //@Rollback(false)   롤백 처리 하지 않는다.
    public void 회원가입() throws Exception{
        //given
        Member member=new Member();
        member.setName("kim");

        //when
        Long savedId=memberService.join(member);

        //then
        em.flush();// 실질적으로 DB 에 반영후  롤백 처리 한다. insert 문을 볼수 있다. DB 에 가면 데이터는 안보인다.
        Assert.assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1=new Member();
        member1.setName("kim");

        Member member2=new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다!!

        //then
        fail("예외가 발생해야 한다.");
    }


}
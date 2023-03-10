package jpabook.jpashop;

import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpabookApplication {

	public static void main(String[] args) {


		SpringApplication.run(JpabookApplication.class, args);
	}

}

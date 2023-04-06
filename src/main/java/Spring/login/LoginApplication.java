package Spring.login;

import Spring.login.domain.user.Grade;
import Spring.login.domain.user.User;
import Spring.login.service.UserService;
import Spring.login.service.UserServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(UserConfig.class)
@SpringBootApplication
public class LoginApplication {

	public static void main(String[] args) {
		// 스프링 컨테이너
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UserConfig.class);
		UserService userService = applicationContext.getBean("userService", UserService.class);


//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//
//		try {
//			User user = new User(null, "test", "testid", "testpw", Grade.CLIENT);
//			em.persist(user);
//			tx.commit();
//		} catch (Exception e) {
//			tx.rollback();
//		} finally {
//			em.close();
//		}
//		emf.close();
//
//		SpringApplication.run(LoginApplication.class, args);
	}
}

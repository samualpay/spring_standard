package com.samual.standard;

import com.samual.standard.Entity.User;
import com.samual.standard.Repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Samual on 2018/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StandardRepositoryTests {
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    @Test
    public void test()throws Exception{
        userRepository.save(new User("samual",23));
        userRepository.save(new User("cherry",26));
        userRepository.save(new User("John",18));
        userRepository.save(new User("Linda",27));
        userRepository.save(new User("Linny",23));
        Assert.assertEquals(5,userRepository.findAll().size());
        Assert.assertEquals(26,userRepository.findByName("cherry").getAge().intValue());
        Assert.assertEquals(2,userRepository.findUsersByAge(23).size());
        Assert.assertEquals(23,userRepository.findByNameAndAge("Linny",23).getAge().intValue());
        userRepository.delete(userRepository.findByName("Linda"));
        Assert.assertEquals(4,userRepository.findAll().size());
        userRepository.deleteAll();
        Assert.assertEquals(0,userRepository.findAll().size());
    }
    @Test
    public void testSql()throws Exception{//測試利用SQL查詢
        userRepository.save(new User("samual",23));
        userRepository.save(new User("cherry",26));
        userRepository.save(new User("John",18));
        userRepository.save(new User("Linda",27));
        userRepository.save(new User("Linny",23));
        List<Object[]> users = em.createNativeQuery("Select u.name, u.age from user u").getResultList();
        Assert.assertEquals("samual",users.get(0)[0]);
        Assert.assertEquals(18,users.get(2)[1]);
        userRepository.deleteAll();
    }
}

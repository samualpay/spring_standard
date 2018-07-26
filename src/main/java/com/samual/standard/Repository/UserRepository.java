package com.samual.standard.Repository;

import com.samual.standard.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Samual on 2018/7/26.
 */
public interface UserRepository extends JpaRepository<User,Long> { //<實體類,主鍵類型>
    User findByName(String name);//透過方法名解析
    User findByNameAndAge(String name,Integer age); //透過方法名解析
    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);//透過HQL
}

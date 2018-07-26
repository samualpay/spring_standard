package com.samual.standard.Entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Samual on 2018/7/26.
 */
@Entity
@Table(name = "user")
public class User implements Serializable{//序列化在Jpa是必須的
    @Id //主键
    @GeneratedValue(strategy = GenerationType.AUTO) //自增长
    private Long id;
    @Column(nullable = false) //不能為空
    private String name;
    @Column(nullable = true) //可以為空
    private Long age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}

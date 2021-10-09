package com.revature.springskeleton.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "yipyip")
public class YipMessage {
    @Id
    @Column(name="id")
    @Getter  @Setter
    private long id;

    @Column(name= "message")
    @Getter  @Setter
    private String message;

}

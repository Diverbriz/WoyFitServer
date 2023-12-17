package com.example.woyfitserver.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetail {
    @Id
    private long id;
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}

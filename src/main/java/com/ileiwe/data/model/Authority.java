package com.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Role authority;

    public Authority(Role authority) {
        this.authority = authority;
    }
}

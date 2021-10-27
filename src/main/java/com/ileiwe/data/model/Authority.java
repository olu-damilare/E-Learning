package com.ileiwe.data.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Role authority;

}

package com.example.VacationRequest.usermgmt.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Table (name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;
    @OneToMany(mappedBy = "role")
    private Collection<User> user;


}

package com.betbull.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "player")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = false)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(name = "name")
    private String name;
    @NonNull
    @Column(name = "age")
    private Integer age;
    @NonNull
    @Column(name = "experience")
    private Integer experience;
    @ManyToOne
    @ToString.Exclude
    @NonNull
    private Team team;
}

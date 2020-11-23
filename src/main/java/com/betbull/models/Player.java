package com.betbull.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "player")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString(includeFieldNames = false)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(name = "name")
    private String name;
    @ManyToOne
    @ToString.Exclude
    @NonNull
    private Team team;
}

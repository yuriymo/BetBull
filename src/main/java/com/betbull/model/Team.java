package com.betbull.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString(includeFieldNames = false)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(name = "name")
    private String name;
}

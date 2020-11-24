package com.betbull.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = false)
public class PlayerDto {
    private Long id;
    private String name;
    private Integer age;
    private Integer experience;
    private Long teamId;
    private String teamName;
}

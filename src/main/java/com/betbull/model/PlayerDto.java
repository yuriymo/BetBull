package com.betbull.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
public class PlayerDto {
    private Long id;
    private String name;
    private Integer age;
    private Integer experience;
    private Long teamId;
    private String teamName;
}

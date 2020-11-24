package com.betbull.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = false)
public class TeamDto {
    private Long id;
    private String name;
}

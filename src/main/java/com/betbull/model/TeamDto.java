package com.betbull.model;

import lombok.*;

@Data(staticConstructor = "of")
@NoArgsConstructor
@ToString(includeFieldNames = false)
public class TeamDto {
    private Long id;
    private String name;
}

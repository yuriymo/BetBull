package com.betbull.models;

import lombok.*;

@Builder
@Getter
@ToString(includeFieldNames = false)
public class PlayerDto {
    private final Long id;
    private final String name;
    private final Integer age;
    private final Integer experience;
    private final Long teamId;
    private final String teamName;
}

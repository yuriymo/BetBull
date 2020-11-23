package com.betbull.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = false)
public class PlayerDto {
    private Long id;
    private String name;
    private Long teamId;
    private String teamName;
}

package com.betbull.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
@ApiModel(description = "Player representation")
public class PlayerDto {
    private Long id;
    private String name;
    private Integer age;
    private Integer experience;
    @ApiModelProperty(notes = "the team id in which the player is member for")
    private Long teamId;
    private String teamName;
}

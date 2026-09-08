package com.gamebasic.game.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RenameRequest {
    @NotBlank
    private String playerName;
}

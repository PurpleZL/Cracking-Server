package com.security.cracking.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "username", "role"}) // orden personalizado
public class UserResponseDTO {
    @NotBlank
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String role;
}

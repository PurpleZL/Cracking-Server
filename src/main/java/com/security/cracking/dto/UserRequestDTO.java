package com.security.cracking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username cant be empty")
    private String username;

    //TODO: Validaciones de contraseña fuerte
    /*
    Min 8 - 12 max
    ~1 May ~1 Min ~1 SpecialChar
     */
    @NotBlank(message = "Password cant be empty")
    private String password;

}

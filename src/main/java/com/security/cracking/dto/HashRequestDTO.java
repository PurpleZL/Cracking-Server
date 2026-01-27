package com.security.cracking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class HashRequestDTO {

    @NotNull
    private String hash;

    private String hashType;
    private String passwd;
    private MultipartFile passListF;

}

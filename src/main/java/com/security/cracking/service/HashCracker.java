package com.security.cracking.service;

import com.security.cracking.dto.HashRequestDTO;
import com.security.cracking.dto.HashResponseDTO;

import java.util.Optional;

public interface HashCracker {

    Optional<String> crack(HashRequestDTO request);
    boolean supports(String hashType);
    boolean hasPassword(HashRequestDTO request) ;
    boolean hasPassList(HashRequestDTO request);

}

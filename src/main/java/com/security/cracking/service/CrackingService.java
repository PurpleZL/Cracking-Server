package com.security.cracking.service;

import com.security.cracking.dto.HashRequestDTO;
import com.security.cracking.dto.HashResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CrackingService {

    public HashResponseDTO crackPasswd(HashRequestDTO hashReq){
        HashResponseDTO hashResp = new HashResponseDTO();

        return hashResp;
    }

}

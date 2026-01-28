package com.security.cracking.service;

import com.security.cracking.dto.HashRequestDTO;
import com.security.cracking.dto.HashResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class CrackingService {

    public HashResponseDTO crackPasswd(HashRequestDTO hashReq){
        HashResponseDTO hashResp = null;
        switch (hashReq.getHashType()){
            case "BCrypt": {
                hashResp = crackBcrypt(hashReq);
                break;
            }
        }

        return hashResp;
    }

    public HashResponseDTO crackBcrypt(HashRequestDTO hashReq){
        HashResponseDTO hashResp = new HashResponseDTO();
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if (hasPassword(hashReq)){
            if(bCrypt.matches(hashReq.getPasswd(), hashReq.getHash())){
                hashResp = success(hashReq, hashReq.getPasswd());
                return hashResp;
            }
        }
        if (hasPassList(hashReq)){
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(hashReq.getPassListF().getInputStream()))) {

                String password;
                while ((password = br.readLine()) != null) {

                    if (bCrypt.matches(password, hashReq.getHash())) {
                        success(hashReq, password);
                        return hashResp;
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("Error reading passlist file", e);
            }
        }

        hashResp = error(hashReq, " Hash not cracked");
        return hashResp;
    }

    /**
     * Auxiliares
     */

    private boolean hasPassword(HashRequestDTO req) {
        return req.getPasswd() != null && !req.getPasswd().isBlank();
    }

    private boolean hasPassList(HashRequestDTO req) {
        return req.getPassListF() != null && !req.getPassListF().isEmpty();
    }

    private HashResponseDTO success(HashRequestDTO req, String password) {
        HashResponseDTO r = new HashResponseDTO();
        r.setSuccess(true);
        r.setHash(req.getHash());
        r.setHashType(req.getHashType());
        r.setPasswdCracked(password);
        r.setMessage("Hash cracked");
        return r;
    }

    private HashResponseDTO error(HashRequestDTO req, String msg) {
        HashResponseDTO r = new HashResponseDTO();
        r.setSuccess(false);
        r.setHash(req.getHash());
        r.setHashType(req.getHashType());
        r.setMessage(msg);
        return r;
    }

}

package com.security.cracking.service;

import com.security.cracking.dto.HashRequestDTO;
import java.security.MessageDigest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;


@Component
public class MD5Cracker implements HashCracker{

    @Override
    public Optional<String> crack(HashRequestDTO hashReq) {
        if (hasPassword(hashReq)) {
            if (md5(hashReq.getPasswd()).equals(hashReq.getHash())) {
                return Optional.of(hashReq.getPasswd());
            }
        }
        if (hasPassList(hashReq)) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(hashReq.getPassListF().getInputStream()))) {

                String password;
                while ((password = br.readLine()) != null) {
                    if (md5(password).equals(hashReq.getHash())) {
                        return Optional.of(password);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("Error reading passlist file", e);
            }
        }
        return Optional.empty();
    }
    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supports(String hashType) {
        return "MD5".equalsIgnoreCase(hashType);
    }

    @Override
    public boolean hasPassword(HashRequestDTO req) {
        return req.getPasswd() != null && !req.getPasswd().isBlank();
    }

    @Override
    public boolean hasPassList(HashRequestDTO req) {
        return req.getPassListF() != null && !req.getPassListF().isEmpty();
    }

    @Override
    public boolean hasSaltList(HashRequestDTO req){
        return false;
    }

    @Override
    public boolean hasSalt(HashRequestDTO req){
        return false;
    }

    @Override
    public String getSupportedHashType(){ return "MD5";}
}

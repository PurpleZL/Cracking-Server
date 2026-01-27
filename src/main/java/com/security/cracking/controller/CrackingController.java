package com.security.cracking.controller;

import com.security.cracking.dto.HashRequestDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CrackingController {

    @PostMapping("/hashcracking")
    public String HashCracking (@ModelAttribute HashRequestDTO hashReq){
        if (hashReq.getPasswd().isEmpty() && hashReq.getPassListF().isEmpty()) return "Nothing";

        HashCracking(hashReq);


        return "Nothing";
    }

}

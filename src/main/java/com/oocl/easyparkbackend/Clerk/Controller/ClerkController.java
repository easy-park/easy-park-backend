package com.oocl.easyparkbackend.Clerk.Controller;

import com.oocl.easyparkbackend.Clerk.Entity.Clerk;
import com.oocl.easyparkbackend.Clerk.Service.ClerkService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClerkController {

    @Autowired
    private ClerkService clerkService;

    @GetMapping("/clerks")
    public ResponseVO getAllClerkMessage() {
        List<Clerk> clerkList = clerkService.showAllClerkMessage();
        return ResponseVO.success(clerkList);
    }

    @GetMapping(path = "/clerklist",params = "name")
    public ResponseVO selectClerkMessageByName(@RequestParam String name){
        List<Clerk> clerkList = clerkService.findClerkMessageByName(name);
        return ResponseVO.success(clerkList);
    }
    @GetMapping(path = "/clerklist",params = "id")
    public ResponseVO selectClerkMessageById(@RequestParam Integer id){
        List<Clerk> clerkList = clerkService.findClerkMessageById(id);
        return ResponseVO.success(clerkList);
    }
    @GetMapping(path = "/clerklist",params = "email")
    public ResponseVO selectClerkMessageByEmail(@RequestParam String email){
        List<Clerk> clerkList = clerkService.findClerkMessageByEmail(email);
        return ResponseVO.success(clerkList);
    }
    @GetMapping(path = "/clerklist",params = "phone")
    public ResponseVO selectClerkMessageByPhone(@RequestParam String phone){
        List<Clerk> clerkList = clerkService.findClerkMessageByPhone(phone);
        return ResponseVO.success(clerkList);
    }
}

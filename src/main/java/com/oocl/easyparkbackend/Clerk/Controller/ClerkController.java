package com.oocl.easyparkbackend.Clerk.Controller;

import com.oocl.easyparkbackend.Clerk.Entity.Clerk;
import com.oocl.easyparkbackend.Clerk.Service.ClerkService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}

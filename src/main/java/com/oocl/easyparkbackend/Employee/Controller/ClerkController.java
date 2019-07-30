package com.oocl.easyparkbackend.Employee.Controller;

import com.oocl.easyparkbackend.Employee.Entity.Clerk;
import com.oocl.easyparkbackend.Employee.Exception.ClerkEmailAndPhoneNumberNotNullException;
import com.oocl.easyparkbackend.Employee.Exception.ClerkIdErrorException;
import com.oocl.easyparkbackend.Employee.Exception.NoSuchPositionException;
import com.oocl.easyparkbackend.Employee.Service.ClerkService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/clerks")
    public ResponseVO updateClerkMessage(@RequestBody Clerk clerk) {
        Clerk returnClerk = clerkService.update(clerk);
        return ResponseVO.success(returnClerk);
    }

    @ExceptionHandler(ClerkEmailAndPhoneNumberNotNullException.class)
    public ResponseVO handleClerkEmailAndPhoneNumberNotNullException(ClerkEmailAndPhoneNumberNotNullException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(ClerkIdErrorException.class)
    public ResponseVO handleClerkIdErrorException(ClerkIdErrorException exception){
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(NoSuchPositionException.class)
    public ResponseVO handleNoSuchPositionException(NoSuchPositionException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }
}

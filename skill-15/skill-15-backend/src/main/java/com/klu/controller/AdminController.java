package com.klu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    @PostMapping("/admin/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@RequestParam String employeeName) {
        return ResponseEntity.ok(Map.of(
                "message", "Employee record added",
                "employeeName", employeeName
        ));
    }

    @DeleteMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmployee(@RequestParam String employeeName) {
        return ResponseEntity.ok(Map.of(
                "message", "Employee record deleted",
                "employeeName", employeeName
        ));
    }
}

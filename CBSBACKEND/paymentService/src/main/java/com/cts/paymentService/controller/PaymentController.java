package com.cts.paymentService.controller;
import com.cts.paymentService.model.Payment;
import com.cts.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public Payment submitPayment(@RequestBody Payment payment) {
        return paymentService.submitPayment(payment);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getpaymentstatusforride/{requestId}")
    public List<String> getPaymentDetailsForRide(@PathVariable Long requestId){
        return paymentService.paymentStatusByRequestId(requestId);
    }
}

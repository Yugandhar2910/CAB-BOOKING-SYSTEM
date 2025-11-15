package com.cts.paymentService.service;

import com.cts.paymentService.model.Payment;

import java.util.List;

public interface PaymentService {
    Payment submitPayment(Payment payment);
    List<String> paymentStatusByRequestId(Long requestId);
}

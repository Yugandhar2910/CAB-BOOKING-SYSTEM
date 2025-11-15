package com.cts.paymentService.service;

import com.cts.paymentService.exceptions.PaymentNotFoundException;
import com.cts.paymentService.model.Payment;
import com.cts.paymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment submitPayment(Payment payment) {
        logger.info("Submitting payment for requestId: {}", payment.getRequestId());
        Payment paymentDone = paymentRepository.save(payment);
        logger.info("Payment submitted successfully with ID: {}", paymentDone.getPaymentId());
        return paymentDone;
    }

    @Override
    public List<String> paymentStatusByRequestId(Long requestId) {
        logger.info("Fetching payment status for requestId: {}", requestId);
        Payment details = paymentRepository.findByRequestId(requestId);

        if (details == null) {
            logger.warn("No payment found for requestId: {}", requestId);
          //  throw new PaymentNotFoundException("Payment not found for requestId: " + requestId);
            return null;
        }

        List<String> status = new ArrayList<>();
        status.add(details.getStatus());
        status.add(details.getMethod());

        logger.info("Payment status retrieved: status={}, method={}", details.getStatus(), details.getMethod());
        return status;
    }
}

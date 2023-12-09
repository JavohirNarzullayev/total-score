package uz.tenge.totalscore.totalscore.web.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.tenge.totalscore.totalscore.domain.payment.PaymentMapper;
import uz.tenge.totalscore.totalscore.domain.payment.PaymentPayload;
import uz.tenge.totalscore.totalscore.domain.payment.PaymentResponse;
import uz.tenge.totalscore.totalscore.domain.payment.PaymentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payment")
public class PaymentResource {
    private final PaymentService paymentService;

    @GetMapping("/{eventId}/list")
    public List<PaymentResponse> payments(
            @PathVariable Long eventId
    ) {
        return PaymentMapper.toResponse(paymentService.getPaymentsByEventId(eventId));
    }

    @GetMapping("/{id}")
    public PaymentResponse getById(@PathVariable Long id) {
        return PaymentMapper.toResponse(paymentService.getById(id));
    }

    @PostMapping
    public PaymentResponse pay(
            @RequestBody PaymentPayload payload
    ) {
        return PaymentMapper.toResponse(paymentService.save(payload));
    }

}

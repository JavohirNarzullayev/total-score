package uz.tenge.totalscore.totalscore.web.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payment resource",description = "Payment api for pay event")
public class PaymentResource {
    private final PaymentService paymentService;

    @GetMapping("/{eventId}/list")
    @Operation(summary = "Get payments by event id")
    public List<PaymentResponse> payments(
            @PathVariable Long eventId
    ) {
        return PaymentMapper.toResponse(paymentService.getPaymentsByEventId(eventId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by id")
    public PaymentResponse getById(@PathVariable Long id) {
        return PaymentMapper.toResponse(paymentService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Pay for event")
    public PaymentResponse pay(
            @RequestBody PaymentPayload payload
    ) {
        return PaymentMapper.toResponse(paymentService.save(payload));
    }

}

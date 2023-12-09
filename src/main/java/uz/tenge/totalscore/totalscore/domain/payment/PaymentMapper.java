package uz.tenge.totalscore.totalscore.domain.payment;


import lombok.experimental.UtilityClass;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeMapper;
import uz.tenge.totalscore.totalscore.domain.event.Event;
import uz.tenge.totalscore.totalscore.domain.event.EventMapper;
import uz.tenge.totalscore.totalscore.domain.event.EventResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class PaymentMapper {

    public static PaymentResponse toResponse(Payment entity) {
        if (entity == null) return null;
        var response = new PaymentResponse();
        response.setId(entity.getId());
        response.setAmount(entity.getAmount());
        response.setEvent(EventMapper.toResponse(entity.getEvent()));
        response.setEmployee(EmployeeMapper.toResponse(entity.getEmployee()));
        return response;
    }


    public static List<PaymentResponse> toResponse(List<Payment> employee) {
        if (employee == null) return new ArrayList<>();
        return employee.stream()
                .map(PaymentMapper::toResponse)
                .filter(Objects::nonNull)
                .toList();
    }

}

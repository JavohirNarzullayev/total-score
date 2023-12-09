package uz.tenge.totalscore.totalscore.domain.event;


import lombok.experimental.UtilityClass;
import uz.tenge.totalscore.totalscore.domain.employee.Employee;
import uz.tenge.totalscore.totalscore.domain.employee.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class EventMapper {

    public static EventResponse toResponse(Event entity) {
        if (entity == null) return null;
        var response = new EventResponse();
        response.setId(entity.getId());
        response.setAmount(entity.getAmount());
        response.setName(entity.getName());
        return response;
    }


    public static List<EventResponse> toResponse(List<Event> employee) {
        if (employee == null) return new ArrayList<>();
        return employee.stream()
                .map(EventMapper::toResponse)
                .filter(Objects::nonNull)
                .toList();
    }

}

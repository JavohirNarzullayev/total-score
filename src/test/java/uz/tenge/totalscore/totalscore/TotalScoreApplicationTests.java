package uz.tenge.totalscore.totalscore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.tenge.totalscore.totalscore.domain.employee.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@SpringBootTest
class TotalScoreApplicationTests {
    @Autowired
    private EmployeeService employeeService;

    @Test
    void contextLoads() {
        List<Employee> employee = employeeService.getEmployee();
        List<CalculateResponse> calculateResponses = employeeService.getCalculateResult(null);

        LinkedList<FeesResponse> haveToGets = new LinkedList<>();
        LinkedList<FeesResponse> haveToGives = new LinkedList<>();

        for (var payment : calculateResponses) {
            if (payment.getResult().compareTo(BigDecimal.ZERO) > 0) {
                haveToGets.add(new FeesResponse(payment));
            } else if (payment.getResult().compareTo(BigDecimal.ZERO) < 0) {
                haveToGives.add(new FeesResponse(payment));
            }
        }
        System.out.println(haveToGets);//minus
        System.out.println(haveToGives);// plus
        System.out.println("####");

        HashMap<BigInteger, LinkedList<FeesResponse>> transactions = new HashMap<>();

        FeesResponse pGet = haveToGets.removeFirst();
        FeesResponse pGive = haveToGives.removeFirst();

        BigDecimal negate = new BigDecimal(-1);
        do {
            FeesResponse finalPGive = pGive;
            FeesResponse finalPGet = pGet;

            int c = pGet.getResult().compareTo(pGive.getResult());
            if (c == 0) { // get == give
                transactions.compute(pGet.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(finalPGive);
                    return list;
                });
                transactions.compute(pGive.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGet.getId(),finalPGet.getFio(), finalPGet.getResult().multiply(negate)));
                    return list;
                });
                pGet = haveToGets.isEmpty() ? null : haveToGets.removeFirst();
                pGive = haveToGives.isEmpty() ? null : haveToGives.removeFirst();
            } else if (c < 0) { // get < give
                transactions.compute(pGet.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGive.getId(),finalPGive.getFio(), finalPGet.getResult()));
                    return list;
                });
                transactions.compute(pGive.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGet.getId(),finalPGet.getFio(), finalPGet.getResult().multiply(negate)));
                    return list;
                });
                pGive = new FeesResponse(pGive.getId(),pGive.getFio(), pGive.getResult().subtract(pGet.getResult()));
                pGet = haveToGets.isEmpty() ? null : haveToGets.removeFirst();
            } else { // get > give
                transactions.compute(pGet.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(finalPGive);
                    return list;
                });
                transactions.compute(pGive.getId(), (key, list) -> {
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(new FeesResponse(finalPGet.getId(),finalPGet.getFio(), finalPGive.getResult().multiply(negate)));
                    return list;
                });
                pGet = new FeesResponse(pGet.getId(),pGet.getFio(), pGet.getResult().subtract(pGive.getResult()));
                pGive = haveToGives.isEmpty() ? null : haveToGives.removeFirst();
            }
        } while (pGet != null && pGive != null);

        List<FeesResponse> feesResponses = new ArrayList<>();
        employee.forEach(employee1 -> {
            var response = new FeesResponse(
                    BigInteger.valueOf(employee1.getId()),
                    employee1.getFio(),
                    null
            );
            LinkedList<FeesResponse> orDefault = transactions.getOrDefault(
                    BigInteger.valueOf(employee1.getId()),
                    new LinkedList<>());
            response.setFees(orDefault);
            feesResponses.add(response);
        });


    }


}

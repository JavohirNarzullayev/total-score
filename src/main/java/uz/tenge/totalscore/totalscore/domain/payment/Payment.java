package uz.tenge.totalscore.totalscore.domain.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tenge.totalscore.totalscore.domain.employee.Employee;
import uz.tenge.totalscore.totalscore.domain.event.Event;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "payment")
@ToString
public class Payment {
    @Id
    @SequenceGenerator(name = "payment_id_seq", sequenceName = "payment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @ToString.Exclude
    private Employee employee;

    private BigDecimal amount;
}

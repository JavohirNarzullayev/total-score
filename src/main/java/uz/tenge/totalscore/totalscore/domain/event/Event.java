package uz.tenge.totalscore.totalscore.domain.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "event")
@ToString
public class Event {
    @Id
    @SequenceGenerator(name = "event_id_seq", sequenceName = "event_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_seq")
    @Column(name = "id")
    private Long id;
    private String name;
    private BigDecimal amount;
    private BigDecimal paid = BigDecimal.ZERO;


    public BigDecimal sum(){
        return this.amount.subtract(paid);
    }
    public void pay(BigDecimal paid){
        this.paid = this.paid.add(paid);
    }

}

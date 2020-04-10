package hu.isakots.martosgym.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TOOL")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOOL_ID")
    private Long id;

    @NotNull
    @Column(name = "NAME", length=63, nullable = false)
    private String name;

    @NotNull
    @Min(value = 1)
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "IS_REACHABLE")
    private boolean isReachable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean reachable) {
        isReachable = reachable;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", isReachable=" + isReachable +
                '}';
    }
}

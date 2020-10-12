package hu.isakots.martosgym.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TOOL")
public class Tool {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "TOOL_ID", length = 36)
    private String id;

    @NotNull
    @Column(name = "NAME", length=63, nullable = false)
    private String name;

    @NotNull
    @Min(value = 1)
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "IS_REACHABLE")
    private boolean isReachable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

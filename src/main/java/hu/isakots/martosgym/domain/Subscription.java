package hu.isakots.martosgym.domain;

import hu.isakots.martosgym.service.model.SubscriptionType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SUBSCRIPTION")
public class Subscription {

    @Id
    @Column(name = "NAME", length = 63)
    @Enumerated(EnumType.STRING)
    private SubscriptionType name;

    public SubscriptionType getName() {
        return name;
    }

    public void setName(SubscriptionType name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subscription that = (Subscription) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "name='" + name + '\'' +
                '}';
    }
}

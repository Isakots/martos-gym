package hu.isakots.martosgym.service.util;

import hu.isakots.martosgym.domain.Subscription;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.service.model.SubscriptionType;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for application logic
 */
public final class ApplicationUtil {

    private ApplicationUtil() {
        // utility class
    }

    public static void mapSubscriptions(Set<String> subscriptionList, User storedUser) {
        storedUser.setSubscriptions(
                subscriptionList.stream()
                        .map(subscriptionName -> {
                            Subscription subscription = new Subscription();
                            try {
                                subscription.setName(SubscriptionType.valueOf(subscriptionName));
                            } catch (IllegalArgumentException | NullPointerException exception) {
                                throw new UnsupportedOperationException("Invalid subscriptionType");
                            }
                            return subscription;
                        })
                        .collect(Collectors.toSet())
        );
    }
}

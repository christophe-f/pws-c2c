package ninja.spring;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DestinationClientFallback implements DestinationClient {

    @Override
    public Resources<Destination> destinations() {

        return new Resources<>(Collections.singletonList(Destination.builder()
                .city("Toronto")
                .country("Canada")
                .build()),
                new Links());
    }
}

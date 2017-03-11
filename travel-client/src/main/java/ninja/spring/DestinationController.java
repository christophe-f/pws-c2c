package ninja.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class DestinationController {

    private final DestinationClient destinationClient;

    @Autowired
    public DestinationController(DestinationClient destinationClient) {
        this.destinationClient = destinationClient;
    }

    @GetMapping("/destinations")
    public Collection<Destination> getDestinations() {
        return this.destinationClient.destinations()
                .getContent()
                .stream()
                .collect(Collectors.toList());
    }
}

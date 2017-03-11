package ninja.spring;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "travel-service", fallback = DestinationClientFallback.class)
public interface DestinationClient {

    @GetMapping("/destinations")
    Resources<Destination> destinations();
}

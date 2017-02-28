package ninja.spring;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("travel-service")
public interface DestinationClient {

    @RequestMapping(method = RequestMethod.GET, value = "/destinations")
    Resources<Destination> destinations();
}

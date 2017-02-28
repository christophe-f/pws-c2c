package ninja.spring;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Destination {
    private Long id;
    private String city;
    private String country;
}

package ninja.spring;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Destination {
    @Id
    @GeneratedValue
    private Long id;

    private String city;
    private String country;
}

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@ToString
public class IdService {
    public UUID returnUUID() {
        return UUID.randomUUID();
    };
}

package telran.microservices.probes.entities;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RedisHash
@Getter
public class ListProbeValues {
    long id;
    List<Integer> values = new ArrayList<>();

    public ListProbeValues(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListProbeValues that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

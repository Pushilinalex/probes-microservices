package telran.microservices.probes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import telran.microservices.probes.entities.Person;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Long> {
    @Query(value="select * from persons where sensor_id=:id", nativeQuery = true)
    List<Person> findBySensorId(long id);
}

/*logging.level.telran=trace
        spring.cloud.config.enabled=false
        spring.datasource.url=jdbc:h2:mem:testdb
        spring.datasource.driver-class-name=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=password
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        spring.jpa.hibernate.ddl-auto=create-drop*/

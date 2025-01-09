package me.sniemzig.data.db;

import me.sniemzig.shared.Calculation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface CalculationRepository extends MongoRepository<Calculation, String> {
}

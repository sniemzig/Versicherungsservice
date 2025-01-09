package me.sniemzig.data;

import me.sniemzig.shared.Calculation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CalculationRepository extends MongoRepository<Calculation, String> {
}

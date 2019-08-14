package cl.trabajando.api_ejemplo.repo.mongo;

import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cl.trabajando.api_ejemplo.model.DatoEjemploMongo;

/**
 * 
 * @author Cristian Gonzalez
 * 13 ago. 2019 
 *
 * Esto es un ejemplo de uso de javers y mongo db
 */
@JaversSpringDataAuditable
@Repository
public interface MongoRepoEjemplo extends MongoRepository<DatoEjemploMongo, String> {
    
    @Override
    Optional<DatoEjemploMongo> findById(String id);
}

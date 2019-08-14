package cl.trabajando.api_ejemplo.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.trabajando.api_ejemplo.model.DatoEjemplo;
import cl.trabajando.api_ejemplo.model.DatoEjemploMongo;
import cl.trabajando.api_ejemplo.model.HolaMundo;
import cl.trabajando.api_ejemplo.repo.HolaMundoRepo;
import cl.trabajando.api_ejemplo.repo.mongo.MongoRepoEjemplo;

/**
 * 
 * @author Cristian Gonzalez 8 ago. 2019
 *
 *         En la capa de servicios debe implmentarse toda la logica de
 *         navegacion
 */
@Service
public class ApiEjemploService {

   
    @Autowired private HolaMundoRepo repo;
    
    @Autowired private MongoRepoEjemplo mongoRepo;
     
    /** EJemplo basico de la capa de servicos
     * 
     * @return
     */
    public HolaMundo getHolaMundo() {
	return new HolaMundo(LocalDateTime.now());
    }

     public void insertarDato(DatoEjemplo dato) {
	 repo.insertarEjemplo(dato);
     }
     
     /**
      * Ejemplo de guardado en mongoDB
      * @param ejemplo
      * @return
      */
     public DatoEjemploMongo guardarDato(DatoEjemploMongo ejemplo) {
	 mongoRepo.save(ejemplo);
	 return ejemplo;
     }
}

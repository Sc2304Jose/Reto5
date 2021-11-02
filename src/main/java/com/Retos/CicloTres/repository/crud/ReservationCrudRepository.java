/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Retos.CicloTres.repository.crud;

import com.Retos.CicloTres.model.Reservation;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Juan Camilo
 */
public interface ReservationCrudRepository extends CrudRepository<Reservation, Integer>{
    
    public List<Reservation> findAllByStatus (String status);
    
    public List<Reservation> findAllByStartDateAfterAndStartDateBefore(Date dateOne, Date dateTwo);
    
    //select clientid, count(*) as "total" from reservation group by clientid order by total DESC
    
    @Query("SELECT c.client, COUNT(c.client) FROM Reservation AS c group by c.client order by COUNT(c.client)DESC")
    
    public List<Object[]> countTotalReservationByClient();
    
}

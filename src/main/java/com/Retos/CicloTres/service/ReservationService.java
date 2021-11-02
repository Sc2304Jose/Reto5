/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Retos.CicloTres.service;
//imports del proyecto
import com.Retos.CicloTres.Reportes.CountClient;
import com.Retos.CicloTres.Reportes.StatusReservation;
import com.Retos.CicloTres.model.Reservation;
import com.Retos.CicloTres.repository.ReservationRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//le indicamos que es un servicio
@Service
public class ReservationService {
    
     @Autowired
     //llamemos el repositorio para poder llamar los getters y setters de los campos requeridos
    private ReservationRepository reservationRepository;

     //generamos una lista que nos retorna todos los datos de la tabla
    public List<Reservation> getAll(){
        return reservationRepository.getAll();
    }

     //Generamos una lista para obtener todos los datos de una tabla dependiendo del id
    public Optional<Reservation> getReservation(int id){
        return reservationRepository.getReservation(id);
    }

    
    //Creamos el metodo para guardar datos con la ayuda del .save que creamos en el repositorio
    public Reservation save(Reservation reserva){
        if(reserva.getIdReservation()==null){
            return reservationRepository.save(reserva);
        }else{
            Optional<Reservation> paux=reservationRepository.getReservation(reserva.getIdReservation());
            if(paux.isEmpty()){
                return reservationRepository.save(reserva);
            }else{
                return reserva;
            }
        }
    }
    //Creamos el metodo para eliminar por id los campos de la tabla
    public boolean deleteReservation(int id){
        Optional<Reservation> reserva=getReservation(id);
        if(!reserva.isEmpty()){
            reservationRepository.delete(reserva.get());
            return true;
        }
        return false;
    }
    //Generamos varias listas las cuales contaran el numero de reservas completas y canceladas
    public StatusReservation getReservationStatusReport(){
        
        List<Reservation> completed=reservationRepository.getReservationByStatus("completed");
        List<Reservation> cancelled=reservationRepository.getReservationByStatus("cancelled");
        return new StatusReservation(completed.size(), cancelled.size());
    }
    //Generamos una lista la cual contendra inicialmente 2 String a los cuales despues les cambio el tipo de dato a Date
    public List<Reservation> getReservationPeriod(String dateA, String dateB){
        SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd");
        Date aDate= new Date();
        Date bDate= new Date();
        
        try {
        
            aDate = parser.parse(dateA);
            bDate = parser.parse(dateB);
            
        }catch(ParseException event){
            event.printStackTrace();
        }
        if(aDate.before(bDate)){
            return reservationRepository.getReservationPeriod(aDate, bDate);
        }else{
            return new ArrayList<>();
        }          
    }
    //Genero una lista que va a contener el getTopClients que cree previamente en el repository
    public List<CountClient> getTopClients(){
        return reservationRepository.getTopClients();
    }
    
    
    
}

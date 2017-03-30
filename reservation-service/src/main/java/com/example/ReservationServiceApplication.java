package com.example;

import javax.persistence.Id;

import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

@Component
class DataCLR implements CommandLineRunner{
	
	private final ReservationRepository reservationRepository;

	public DataCLR(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		
		Stream.of("Burak","Gökhan","Murat","Eda","Zeki","Tolgahan").forEach(name -> reservationRepository.save(new Reservation(name)));
		reservationRepository.findAll().forEach(System.out::println);
		
		
	}
	
}


@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
}

@RestController
@RefreshScope
class MessageRestController{
	
	
	private final String value;
	
	public MessageRestController(@Value("${message}") String value) {
		this.value = value;
	}

	@GetMapping("/message")
	String read(){
		return this.value;
	}
}

@Entity
class Reservation{
	
	@Id
	@GeneratedValue
	private Long Id;
	
	private String reservationName;

	Reservation() {

	}
	
	public Reservation(String reservationName) {
		this.reservationName = reservationName;
	}

	@Override
	public String toString() {
		return "Reservation [Id=" + Id + ", reservationName=" + reservationName + "]";
	}

	public Long getId() {
		return Id;
	}

	public String getReservationName() {
		return reservationName;
	}
	
	
	
}

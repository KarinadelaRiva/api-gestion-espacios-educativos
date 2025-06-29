package com.apigestionespacios.apigestionespacios;

import com.apigestionespacios.apigestionespacios.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApiGestionEspaciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGestionEspaciosApplication.class, args);


	}

}

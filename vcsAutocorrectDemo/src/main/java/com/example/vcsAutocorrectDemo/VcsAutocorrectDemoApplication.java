package com.example.vcsAutocorrectDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication/*(scanBasePackages = {"com.example.vcsAutocorrectDemo.controller"})*/
public class VcsAutocorrectDemoApplication {

	public static void main(String[] args) {
		/*Class[] sources = new Class[3];
		sources[2] = AcquirerController.class;
		sources[1] = MerchantController.class;
		sources[0] = VcsAutocorrectDemoApplication.class;
		SpringApplication.run(sources, args);*/
		SpringApplication.run(VcsAutocorrectDemoApplication.class, args);
	}

}

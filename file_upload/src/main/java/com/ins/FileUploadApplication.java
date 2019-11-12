package com.ins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ins.property.FileStorageProperties;



@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
@EnableJpaAuditing
public class FileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadApplication.class, args);
	}

}

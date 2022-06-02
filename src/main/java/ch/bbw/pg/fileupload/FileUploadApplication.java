package ch.bbw.pg.fileupload;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileUploadApplication {

    private int maxUploadSizeInMb = 10 * 1024 * 1024;

    public static void main(String[] args) {
        SpringApplication.run(FileUploadApplication.class, args);
    }



}

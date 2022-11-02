package cn.edu.bit.GSDB;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.edu.bit.GSDB.dao")
public class GsdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsdbApplication.class, args);
    }

}

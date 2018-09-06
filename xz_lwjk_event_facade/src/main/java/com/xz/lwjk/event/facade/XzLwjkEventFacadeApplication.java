package com.xz.lwjk.event.facade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author hr
 */
@SpringBootApplication
@EnableSwagger2
//@ImportResource(value = {"classpath:spring/spring-provider.xml","classpath:spring/job.xml"})
@EntityScan(basePackages = {"com.event.common.entity","com.xz.lwjk.event.facade.entity"})
@ServletComponentScan(value = "com.xz.lwjk.event.facade.filter")
@MapperScan(value = "com.xz.lwjk.event.facade.mapper")
public class XzLwjkEventFacadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(XzLwjkEventFacadeApplication.class, args);
	}

}

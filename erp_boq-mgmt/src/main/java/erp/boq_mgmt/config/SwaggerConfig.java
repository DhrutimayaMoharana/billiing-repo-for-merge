package erp.boq_mgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("erp.boq_mgmt.controller")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ERP BOQ Management API's")
				.description("ERP BOQ Management API references for developers")
//				.termsOfServiceUrl("http://billing.nkcproject.com")
//				.contact(new Contact("Billing", "http://billing.nkcproject.com", "billing@erp.com")).license("Billing License")
//				.licenseUrl("billing@erp.com")
				.version("1.0").build();
	}

}
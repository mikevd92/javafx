package configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.annotation.EnableTransactionManagement; 

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"service"},includeFilters=@ComponentScan.Filter(value = {org.springframework.stereotype.Service.class},type=FilterType.ANNOTATION))
@Import(PersistenceConfig.class)
public class ServiceConfig {
	@Bean
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
    {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("datasource.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }   
	@Bean
	public PersistenceAnnotationBeanPostProcessor postProcessor(){
	    	PersistenceAnnotationBeanPostProcessor pp=new PersistenceAnnotationBeanPostProcessor();
	    	return pp;
	}
}

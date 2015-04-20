package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import FXPresentation.DefaultLoginController;
import FXPresentation.DefaultUserController;
import FXPresentation.DefaultManagerController;
import FXPresentation.DefaultSignUpController;
@Configuration
@EnableTransactionManagement
@Import(ServiceConfig.class)
public class ControllerConfig {
	    @Bean
	    @Scope("prototype")
	    public DefaultLoginController defaultLoginController()
	    {
	        return new DefaultLoginController();
	    }
	    @Bean
	    @Scope("prototype")
	    public DefaultManagerController defaultManagerController()
	    {
	        return new DefaultManagerController();
	    }
	    @Bean
	    @Scope("prototype")
	    public DefaultSignUpController defaultSignUpController()
	    {
	        return new DefaultSignUpController();
	    }
	    @Bean
	    @Scope("prototype")
	    public DefaultUserController defaultUserController()
	    {
	        return new DefaultUserController();
	    }  
}

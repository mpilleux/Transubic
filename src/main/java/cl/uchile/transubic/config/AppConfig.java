package cl.uchile.transubic.config;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan({ "cl.uchile.transubic.*" })
@EnableTransactionManagement
@Import({ SecurityConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public SessionFactory sessionFactoryTransubic() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSourceTransubic());

		builder.scanPackages("cl.uchile.transubic.user.model").addProperties(getHibernateProperties(false));
		builder.scanPackages("cl.uchile.transubic.calendarEvent.model").addProperties(getHibernateProperties(false));
		builder.scanPackages("cl.uchile.transubic.claseEjemplo.model").addProperties(getHibernateProperties(false));

		return builder.buildSessionFactory();
	}

	private Properties getHibernateProperties(boolean users) {
		Properties prop = new Properties();
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.show_sql", "true");
		prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		if (!users) {// do not change the users table in db travel
			prop.put("hibernate.hbm2ddl.auto", "update");
		}
		return prop;
	}

	@Bean(name = "dataSourceTransubic")
	public BasicDataSource dataSourceTransubic() {

		return DBConfig.dataSourceTransubic();
	}

	@Bean(name = "txManager")
	@Primary
	public HibernateTransactionManager txManagerAUICommuting() {
		return new HibernateTransactionManager(sessionFactoryTransubic());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}

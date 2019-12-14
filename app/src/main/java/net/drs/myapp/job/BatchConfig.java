package net.drs.myapp.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.drs.myapp.job.step.JobCompletionListener;
import net.drs.myapp.job.step.Processor;
import net.drs.myapp.job.step.Reader;
import net.drs.myapp.job.step.Writer;
import net.drs.myapp.model.Email;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

//@Configuration
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job processJob() {
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer()).listener(listener())
				.flow(orderStep1()).end().build();
	}

	@Bean
	public Step orderStep1() {
		return stepBuilderFactory.get("orderStep1").<String, String> chunk(1)
				.reader(new Reader()).processor(new Processor())
				.writer(new Writer()).build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

	
	@Autowired
	private DataSource dataSource;

	
	 @Autowired
	 private Environment environment;
	
/*	 @Bean
	 public DataSource dataSource() {
		 
		 System.out.println("environment");
	  final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	  dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	  dataSource.setUrl("jdbc:mysql://localhost/springbatch");
	 // dataSource.setEmailname("root");
	  dataSource.setPassword("root");
	  
	  return dataSource;
	 }
*/	 
	 
	 
	/* @Bean
	 public JdbcCursorItemReader<Email> reader(){
	  JdbcCursorItemReader<Email> reader = new JdbcCursorItemReader<Email>();
	  reader.setDataSource(dataSource);
	  reader.setSql("SELECT * FROM EMAIL_NOTIFICATION ");
	  reader.setRowMapper(new EmailRowMapper());
	  return reader;
	 }*/
	 
	 	
	
	    static DataSource dataSource(Environment environment) {

		 SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
	        String pw = environment.getProperty("dataSource.password"),
	                user = environment.getProperty("dataSource.user"),
	                url = environment.getProperty("dataSource.url");
	        
	        try {
	        	Class c; 
	        	
	        	dataSource.setPassword(pw);
	        	dataSource.setUrl(url);
	        	dataSource.setUsername(user);
	        
	        	c = Class.forName("org.h2.Driver");
	        	 dataSource.setDriverClass(c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	       

	        return dataSource;
	    }
	 
	 
	 public DataSource getDataSource() {
			return dataSource;
		}

		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}


	public class EmailRowMapper implements RowMapper<Email>{
	  @Override
	  public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
	   Email email = new Email();
	   email.setEmailId(rs.getString("name"));
	   return email;
	 }
	}
	
}

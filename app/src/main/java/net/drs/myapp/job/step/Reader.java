package net.drs.myapp.job.step;



import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.drs.myapp.dao.INotifyByEmailDAO;
import net.drs.myapp.model.Email;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Configuration
public class Reader implements ItemReader<String> {

	@Autowired
	private INotifyByEmailDAO notficationEmailDAO;
	
	/*@Override
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		List listofEmailIds = new ArrayList();
		listofEmailIds = notficationEmailDAO.getEntriesTowhichEmailNeedstobeSent();
		
		for (Object item : listofEmailIds) {
		
			return item.toString();
		}
		return null;
		
		
	}*/
	 @Autowired
	 public DataSource dataSource;

	
	 @Autowired
	 private Environment environment;	
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	
	 public JdbcCursorItemReader<Email> reader(){
	  JdbcCursorItemReader<Email> reader = new JdbcCursorItemReader<Email>();
	  
	  
	 
	  reader.setDataSource(dataSource);
	  reader.setSql("SELECT * FROM Email");
	  try {
	//	reader.read();
	} catch (UnexpectedInputException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  reader.setRowMapper(new EmailRowMapper());
	  return reader;
	 }

	
	public class EmailRowMapper implements RowMapper<Email>{

		  @Override
		  public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
		   Email email = new Email();
		   email.setEmailId(rs.getString("name"));
		   
		   
		   return email;
		  }
	}

	@Override
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		
		reader();
		return null;
	}
	
}
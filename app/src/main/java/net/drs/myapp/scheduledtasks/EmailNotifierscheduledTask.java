package net.drs.myapp.scheduledtasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.drs.myapp.api.INotifyByEmail;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifierscheduledTask {


    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    INotifyByEmail notificationByEmailService;
    
    @Autowired
    private JobLauncher jobLauncher;
    
    
    @Autowired
    Job processJob;
    
    
/*    @Scheduled(fixedRate = 24000)
    public void scheduleTaskWithFixedRate() {
        notificationByEmailService.sendNotofication();	
    }
*/    
    
   // @Scheduled(fixedRate = 1000)
    /*public void scheduleTaskWithFixedRate1() {
    	
    	JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
    	
    	try {
			jobLauncher.run(processJob, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
        //jobLauncher.run(processJob, jobParameters);   	notificationByEmailService.sendNotofication();
    //	System.out.println("Fixed Rate Task :: Execution Time - {}"+ dateTimeFormatter.format(LocalDateTime.now()));
    //}

    
    
    
    
}
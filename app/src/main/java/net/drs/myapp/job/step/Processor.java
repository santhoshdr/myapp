package net.drs.myapp.job.step;


import java.util.List;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		// TODO Auto-generated method stub
		return sendEmail(item);
	}

/*
	@Override
	public String process(List item) throws Exception {
		return sendEmail(item);
	}

	private String sendEmail(List item) {
	
		System.out.println("Email sent to "  + item);
		return "";
	}
*/
	
	
	private String sendEmail(String item) {
		
		System.out.println("Email sent to "  + item);
		return "";
	}
}
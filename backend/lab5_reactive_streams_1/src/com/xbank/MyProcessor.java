package com.xbank;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

import com.xbank.beans.Account;
import com.xbank.beans.Freelancer;

public class MyProcessor extends SubmissionPublisher<Freelancer> implements Processor<Account, Freelancer> {

	private Subscription subscription;
	private Function<Account,Freelancer> function;
	
	public MyProcessor(Function<Account,Freelancer> function) {
	    super();  
	    this.function = function;  
	  }  
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Account emp) {
		submit((Freelancer) function.apply(emp));  
	    subscription.request(1);  
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("Done");
	}

}

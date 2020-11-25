package com.xbank;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import com.xbank.beans.Account;

public class MyReactiveApp {

	public static void main(String args[]) throws InterruptedException {

		// Create Publisher
		SubmissionPublisher<Account> publisher = new SubmissionPublisher<>();

		// Register Subscriber
		MySubscriber subs = new MySubscriber();
		publisher.subscribe(subs);

		List<Account> emps = Helper.getEmps();

		// Publish items
		System.out.println("Publishing Items to Subscriber");
		emps.stream().forEach(i -> publisher.submit(i));

		// logic to wait till processing of all messages are over
		//while (emps.size() != subs.getCounter()  || !publisher.isSubscribed(subs)) {
			Thread.sleep(1000);
		//}
		// close the Publisher
		publisher.close();

		System.out.println("Exiting the app");

	}

}

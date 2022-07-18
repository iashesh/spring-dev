/**
 * 
 */
package com.binarray.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

/**
 * @author Ashesh Krishna
 *
 */
public class PersonSkipListener implements SkipListener<Person, Person> {
	private static final Logger log = LoggerFactory.getLogger(PersonSkipListener.class);

	@Override
	public void onSkipInProcess(Person item, Throwable t) {
		log.warn("Skipping person in processor: " + item);

	}

	@Override
	public void onSkipInRead(Throwable t) {
		log.warn("Skipping in read. Message: {}", t.getMessage());
	}

	@Override
	public void onSkipInWrite(Person item, Throwable t) {
		log.warn("Skipping person in writer: " + item);
	}

}

/**
 * 
 */
package com.binarray.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.dao.DuplicateKeyException;

/**
 * @author Ashesh Krishna
 *
 */
public class CustomSkipPolicy implements SkipPolicy {

	private static final Logger log = LoggerFactory.getLogger(CustomSkipPolicy.class);
	private static final int MAX_SKIP_COUNT = 2;

	@Override
	public boolean shouldSkip(Throwable throwable, int skipCount) throws SkipLimitExceededException {

		if (throwable instanceof DuplicateKeyException && skipCount < MAX_SKIP_COUNT) {
			log.warn("Skipping DuplicateKeyException. SkipCount: {}", skipCount);
			return true;
		}

		return false;
	}
}

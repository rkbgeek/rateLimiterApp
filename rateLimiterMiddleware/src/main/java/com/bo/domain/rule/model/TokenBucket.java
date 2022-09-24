package com.bo.domain.rule.model;

public class TokenBucket {

	private Long windowStartingTime;
	private Long bucketAvailableCount;
	
	
	public TokenBucket() {
	 
	}

	public TokenBucket(Long windowStartingTime, Long bucketAvailableCount) {
		this.windowStartingTime = windowStartingTime;
		this.bucketAvailableCount = bucketAvailableCount;
	}
	
	public Long getWindowStartingTime() {
		return windowStartingTime;
	}
	public void setWindowStartingTime(Long windowStartingTime) {
		this.windowStartingTime = windowStartingTime;
	}
	public Long getBucketAvailableCount() {
		return bucketAvailableCount;
	}
	public void setBucketAvailableCount(Long bucketAvailableCount) {
		this.bucketAvailableCount = bucketAvailableCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
        builder.append("{\"windowStartingTime\" :");
        builder.append(windowStartingTime);
        builder.append(", \"bucketAvailableCount\" :");
        builder.append(bucketAvailableCount);
        builder.append("}");
    return builder.toString();
	}

	 
	
	
	
	
}

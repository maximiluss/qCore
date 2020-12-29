package com.github.maximiluss.utils;

public class TimerBuilder {

	private Long current;
	private Long delay;

	public TimerBuilder(Long current, Long delay) {
		this.current = current;
		this.delay = delay;
	}

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	public Long getPassedTime() {
		return System.currentTimeMillis() - current;
	}

	public String getTimeToString() {
		int delay = (int) ((getDelay() - getPassedTime()) / 1000);
		return TimeUtils.setTimeToMessage(delay);
	}

	public boolean hisFished() {
		if (System.currentTimeMillis() - current >= delay)
			return true;

		return false;
	}

}

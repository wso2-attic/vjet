package org.ebayopensource.dsf.javatojs.control.translate;

import java.util.concurrent.Callable;

/**
 * The ResponseCommand abstract class that will be implemented by a concrete
 * instance of this class.
 */
public abstract class ResponseCommand implements Callable {
	// flag to indicate that command has been cancelled
	private boolean m_isCancelled;
	// flag for indicating that the Cmd is done.
	// Used when there is no associated Tracker.
	public boolean done = false;
	// flag for indicating Cmd complted successfully
	public boolean success = false;
	// The ResponseTracker that this Cmd is linked in with.
	// Set volatile to make sure that it isn't held in thread cache
	// and not flushed to main memory quick enough for a fast
	// acting command thread to see its assignment.

	private Throwable m_th = null;

	private long m_childId;
	private long m_childThreadId;
	private long m_calStartTime;

	public ResponseCommand() {
	}

	/**
	 * Return success/failure of cmd
	 * 
	 * @return boolean
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Set the success/failure flag
	 * 
	 * @param boolean success
	 */
	protected void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * check in the absence of a Tracker this parameter
	 * 
	 * @return boolean true/false
	 */
	public boolean isDone() {
		return done;
	}

	public Throwable getError() {
		return m_th;
	}

	/**
	 * setIsDone called by the Worker thread - in the absence of Tracker would
	 * set the done flag and call on notify in the presence of tracker would
	 * call setDone on tracker
	 * 
	 * Note that command should not call this method - it will be called by the
	 * CommandRunner itself.
	 */
	synchronized void setIsDone() {
		done = true;

		notify();

	}

	synchronized void onCommandStart() {

	}

	protected void setError(Throwable t) {
		m_th = t;
	}

	/**
	 * Checks whether command has been cancelled
	 * 
	 * @return boolean true/false
	 */
	public synchronized boolean isCancelled() {
		return m_isCancelled;
	}

	public abstract void execute();

	@Override
	public Object call() throws Exception {
		execute();
		return null;
	}

	/**
	 * Marks command as cancelled. Worker will not execute cancelled commands.
	 * Command implementation is free to overrise this method in order to
	 * perform graceful cancellation for the cases when command has already
	 * started.
	 */
	public synchronized void cancel() {
		m_isCancelled = true;
	}

}
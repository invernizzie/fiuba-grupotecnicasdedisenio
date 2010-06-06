package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.notifiers;

import org.eclipse.swt.widgets.Display;

public abstract class AsyncChanger implements Runnable {

	private boolean inQueue;
	private boolean updatedRecently;
	
	public synchronized boolean isInQueue() {
		return inQueue;
	}
	
	@Override
	public abstract void run();

	protected synchronized void setInQueue(boolean inQueue) {
		this.inQueue = inQueue;
	}
	
	public void queue(Display display) {

		synchronized (this) {
			
			if (!this.isInQueue()) {
				
				this.setInQueue(true);
			}
		}

		display.asyncExec(this);
	}

	public boolean hasUpdatedRecently() {
		return updatedRecently;
	}

	public void setUpdatedRecently(boolean hasUpdatedRecently) {
		this.updatedRecently = hasUpdatedRecently;
	}

	
}

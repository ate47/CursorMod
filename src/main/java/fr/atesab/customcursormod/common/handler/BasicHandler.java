package fr.atesab.customcursormod.common.handler;

public class BasicHandler<T> {
	protected T handle;
	public BasicHandler(T handle) {
		this.handle = handle;
	}
	@SuppressWarnings("unchecked")
	public <O> O getHandle() {
		return (O) handle;
	}
}

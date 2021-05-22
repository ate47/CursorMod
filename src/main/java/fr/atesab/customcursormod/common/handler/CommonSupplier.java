package fr.atesab.customcursormod.common.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import fr.atesab.customcursormod.common.CursorMod;

/**
 * A supplier asking for the {@link GameType}
 */
public class CommonSupplier<I, T> {
	private boolean buffer;
	private Function<I, T> bufferedFunction;
	private T store = null;
	private Map<GameType, Function<I, T>> suppliers = new HashMap<>();

	/**
	 * A supplier
	 * 
	 * @param buffer if the value must be buffered
	 */
	public CommonSupplier(boolean buffer) {
		this.buffer = buffer;
	}

	/**
	 * register an action for a type
	 * 
	 * @param type   the game type
	 * @param action the action
	 * @return this
	 * @see #forType(GameType, Supplier)
	 */
	public CommonSupplier<I, T> forType(GameType type, Runnable action) {
		return forType(type, i -> {
			action.run();
			return null;
		});
	}

	/**
	 * register an supplier for a type
	 * 
	 * @param type   the game type
	 * @param action the action
	 * @return this
	 * @see #forType(GameType, Function)
	 * @see #forType(GameType, Runnable)
	 */
	public CommonSupplier<I, T> forType(GameType type, Supplier<T> supplier) {
		return forType(type, i -> supplier.get());
	}

	/**
	 * register a supplier for a type
	 * 
	 * @param type     the game type
	 * @param supplier the supplier
	 * @return this
	 * @see #forType(GameType, Runnable)
	 */
	public CommonSupplier<I, T> forType(GameType type, Function<I, T> supplier) {
		suppliers.put(Objects.requireNonNull(type, "type can't be null"),
				Objects.requireNonNull(supplier, "supplier can't be null"));
		return this;
	}

	/**
	 * @return the fetched value for the GameType
	 * @throws UnhandledGameTypeException if the type isn't registered
	 * @see #fetch(Object)
	 */
	public T fetch() {
		return fetch(null);
	}
	/**
	 * @return the fetched value for the GameType
	 * @throws UnhandledGameTypeException if the type isn't registered
	 * @see #fetch()
	 */
	public T fetch(I input) {
		// Check buffer
		if (buffer && store != null) {
			return store;
		}
		// Check function buffer
		if (bufferedFunction == null) {
			GameType type = CursorMod.getInstance().getType();
			Function<I, T> s;

			s = suppliers.get(type);
			if (s == null) {
				s = suppliers.get(GameType.COMMON);
				if (s == null)
					throw new UnhandledGameTypeException(type);
			}
			bufferedFunction = s;
			suppliers.clear();
			suppliers = null; // gc
		}
		T out = bufferedFunction.apply(input);
		if (buffer) {
			store = out;
			if (store != null) {
				bufferedFunction = null; // gc
			}
		}
		return out;
	}
}

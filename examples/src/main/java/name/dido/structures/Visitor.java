package name.dido.structures;

public interface Visitor<T> {
	public boolean accept(T value);
}

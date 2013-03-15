package ee.pri.rl.prevayler;

import java.io.IOException;
import java.io.Serializable;

import org.prevayler.PrevaylerFactory;

public class GenericPrevaylerFactory {
	
	public static <B extends Serializable> GenericPrevayler<B> createPrevayler(B serializable, String base) throws IOException, ClassNotFoundException {
		return new GenericPrevayler<B>(PrevaylerFactory.createPrevayler(serializable, base));
	}
}

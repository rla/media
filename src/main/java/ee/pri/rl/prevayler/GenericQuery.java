package ee.pri.rl.prevayler;

import java.util.Date;

import org.prevayler.Query;

public abstract class GenericQuery<B, T> implements Query {

	@SuppressWarnings("unchecked")
	public Object query(Object prevalentObject, Date timestamp) throws Exception {
		return doQuery((B) prevalentObject, timestamp);
	}

	protected abstract T doQuery(B prevalentObject, Date timestamp) throws Exception;

}

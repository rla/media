package ee.pri.rl.prevayler;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public abstract class GenericTransactionWithQuery<B, T> implements TransactionWithQuery {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public Object executeAndQuery(Object prevalentObject, Date timestamp) throws Exception {
		return doExecuteAndQuery((B) prevalentObject, timestamp);
	}
	
	protected abstract T doExecuteAndQuery(B prevalentObject, Date timestamp) throws Exception;

}

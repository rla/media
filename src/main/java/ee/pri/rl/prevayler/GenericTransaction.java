package ee.pri.rl.prevayler;

import java.util.Date;

import org.prevayler.Transaction;

public abstract class GenericTransaction<B> implements Transaction {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void executeOn(Object prevalentObject, Date timestamp) {
		doExecuteOn((B) prevalentObject, timestamp);
	}
	
	protected abstract void doExecuteOn(B prevalentObject, Date timestamp);

}

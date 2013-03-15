package ee.pri.rl.prevayler;

import java.io.IOException;

import org.prevayler.Clock;
import org.prevayler.Prevayler;

public class GenericPrevayler<B> {
	private final Prevayler prevayler;

	public GenericPrevayler(Prevayler prevayler) {
		this.prevayler = prevayler;
	}

	public Clock clock() {
		return prevayler.clock();
	}

	public void close() throws IOException {
		prevayler.close();
	}

	@SuppressWarnings("unchecked")
	public <T> T execute(GenericQuery<B, T> query) throws Exception {
		return (T) prevayler.execute(query);
	}

	public void execute(GenericTransaction<B> query) {
		prevayler.execute(query);
	}

	@SuppressWarnings("unchecked")
	public <T> T execute(GenericTransactionWithQuery<B, T> query) throws Exception {
		return (T) prevayler.execute(query);
	}

	public void takeSnapshot() throws IOException {
		prevayler.takeSnapshot();
	}

	@SuppressWarnings("unchecked")
	public B prevalentSystem() {
		return (B) prevayler.prevalentSystem();
	}
	
}

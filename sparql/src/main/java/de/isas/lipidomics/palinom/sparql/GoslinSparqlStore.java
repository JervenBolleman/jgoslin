package de.isas.lipidomics.palinom.sparql;

import org.eclipse.rdf4j.sail.helpers.AbstractSail;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.sail.SailConnection;
import org.eclipse.rdf4j.sail.SailException;

public class GoslinSparqlStore extends AbstractSail {
	private final ValueFactory vf = SimpleValueFactory.getInstance();
	
	public GoslinSparqlStore() {
		super();
	}

	@Override
	public boolean isWritable() throws SailException {
		return false;
	}

	@Override
	public ValueFactory getValueFactory() {
		return vf;
	}

	@Override
	protected void shutDownInternal() throws SailException {

	}

	@Override
	protected SailConnection getConnectionInternal() throws SailException {
		return new GoslinSailConnection(vf);
	}
	
}

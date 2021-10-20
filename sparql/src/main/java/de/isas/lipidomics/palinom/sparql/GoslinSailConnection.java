package de.isas.lipidomics.palinom.sparql;

import org.eclipse.rdf4j.common.iteration.CloseableIteration;
import org.eclipse.rdf4j.common.iteration.CloseableIteratorIteration;
import org.eclipse.rdf4j.common.iteration.EmptyIteration;
import org.eclipse.rdf4j.common.transaction.IsolationLevel;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.Dataset;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.algebra.TupleExpr;
import org.eclipse.rdf4j.query.algebra.evaluation.EvaluationStrategy;
import org.eclipse.rdf4j.query.algebra.evaluation.impl.BindingAssigner;
import org.eclipse.rdf4j.query.algebra.evaluation.impl.FilterOptimizer;
import org.eclipse.rdf4j.query.algebra.evaluation.impl.StrictEvaluationStrategy;
import org.eclipse.rdf4j.query.impl.EmptyBindingSet;
import org.eclipse.rdf4j.sail.SailConnection;
import org.eclipse.rdf4j.sail.SailException;
import org.eclipse.rdf4j.sail.UnknownSailTransactionStateException;
import org.eclipse.rdf4j.sail.UpdateContext;

public class GoslinSailConnection implements SailConnection {
	private final ValueFactory vf;

	public GoslinSailConnection(ValueFactory vf) {
		this.vf = vf;
	}

	@Override
	public boolean isOpen() throws SailException {
		return true;
	}

	@Override
	public void close() throws SailException {

	}

	@Override
	public CloseableIteration<? extends BindingSet, QueryEvaluationException> evaluate(TupleExpr tupleExpr,
			Dataset dataset, BindingSet bindings, boolean includeInferred) throws SailException {
		try {

			GoslinTripleSource tripleSource = new GoslinTripleSource(vf);
			EvaluationStrategy strategy = new StrictEvaluationStrategy(tripleSource, fd);
			tupleExpr = tupleExpr.clone();
			new BindingAssigner().optimize(tupleExpr, dataset, bindings);

			new FilterOptimizer().optimize(tupleExpr, dataset, bindings);
			
			return strategy.evaluate(tupleExpr, EmptyBindingSet.getInstance());
		} catch (QueryEvaluationException e) {
			throw new SailException(e);
		}
	}

	@Override
	public CloseableIteration<? extends Resource, SailException> getContextIDs() throws SailException {
		return new EmptyIteration<>();
	}

	@Override
	public void addStatement(Resource arg0, IRI arg1, Value arg2, Resource... arg3) throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void addStatement(UpdateContext arg0, Resource arg1, IRI arg2, Value arg3, Resource... arg4)
			throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void begin() throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void begin(IsolationLevel arg0) throws UnknownSailTransactionStateException, SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void clear(Resource... arg0) throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void clearNamespaces() throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void commit() throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void endUpdate(UpdateContext arg0) throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void flush() throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public String getNamespace(String arg0) throws SailException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloseableIteration<? extends Namespace, SailException> getNamespaces() throws SailException {
		return new EmptyIteration<>();
	}

	@Override
	public CloseableIteration<Statement, SailException> getStatements(Resource arg0, IRI arg1, Value arg2,
			boolean arg3, Resource... arg4) throws SailException {

		CloseableIteration<? extends Statement, QueryEvaluationException> statements = new GoslinTripleSource()
				.getStatements(arg0, arg1, arg2, arg4);
		return new CloseableIteratorIteration<Statement, SailException>() {

			@Override
			public boolean hasNext() throws SailException {
				try {
					return statements.hasNext();
				} catch (QueryEvaluationException e) {
					throw new SailException(e);
				}
			}

			@Override
			public Statement next() throws SailException {
				try {
					return statements.next();
				} catch (QueryEvaluationException e) {
					throw new SailException(e);
				}
			}

			@Override
			protected void handleClose() throws SailException {
				try {
					statements.close();
				} catch (QueryEvaluationException e) {
					throw new SailException(e);
				}
				super.handleClose();
			}
		};

	}

	@Override
	public boolean isActive() throws UnknownSailTransactionStateException {
		return true;
	}

	@Override
	public boolean pendingRemovals() {
		return false;
	}

	@Override
	public void prepare() throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");
	}

	@Override
	public void removeNamespace(String arg0) throws SailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeStatement(UpdateContext arg0, Resource arg1, IRI arg2, Value arg3, Resource... arg4)
			throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");
	}

	@Override
	public void removeStatements(Resource arg0, IRI arg1, Value arg2, Resource... arg3) throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void rollback() throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public void setNamespace(String arg0, String arg1) throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");

	}

	@Override
	public long size(Resource... arg0) throws SailException {
		return 0;
	}

	@Override
	public void startUpdate(UpdateContext arg0) throws SailException {
		throw new SailException("Goslin is a virtual sparql endpoint");
	}

}

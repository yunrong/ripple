/*
 * $URL$
 * $Revision$
 * $Author$
 *
 * Copyright (C) 2007-2009 Joshua Shinavier
 */


package net.fortytwo.ripple.libs.graph;

import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.flow.Sink;
import net.fortytwo.ripple.model.PrimitiveStackMapping;
import net.fortytwo.ripple.model.RippleValue;
import net.fortytwo.ripple.model.ModelConnection;
import net.fortytwo.ripple.model.StackContext;
import net.fortytwo.ripple.model.RippleList;


/**
 * A primitive which consumes a subject, predicate and object, then produces the
 * subject after removing the corresponding RDF statement from the triple store.
 */
public class DenyInContext extends PrimitiveStackMapping
{
	private static final int ARITY = 4;

    private static final String[] IDENTIFIERS = {
            GraphLibrary.NS_2008_08 + "denyInContext"};

    public String[] getIdentifiers()
    {
        return IDENTIFIERS;
    }

	public DenyInContext()
		throws RippleException
	{
		super();
	}

	public int arity()
	{
		return ARITY;
	}

	public void apply( final StackContext arg,
						 final Sink<StackContext, RippleException> solutions )
		throws RippleException
	{
		final ModelConnection mc = arg.getModelConnection();
		RippleList stack = arg.getStack();

		RippleValue subj, pred, obj, ctx;

        ctx = stack.getFirst();
        stack = stack.getRest();
		obj = stack.getFirst();
		stack = stack.getRest();
		pred = stack.getFirst();
		stack = stack.getRest();
		subj = stack.getFirst();
		stack = stack.getRest();

        mc.remove( subj, pred, obj, ctx );

		// TODO: store added and removed statements in a buffer until the
		// ModelConnection commits.  You may not simply wait to commit,
		// as writing and then reading without first committing may result
		// in a deadlock.  The LinkedDataSail already does this sort of
		// buffering, which is why it does not deadlock w.r.t. its base
		// Sail.
		mc.commit();

		solutions.put( arg.with( stack.push( subj ) ) );
	}
}
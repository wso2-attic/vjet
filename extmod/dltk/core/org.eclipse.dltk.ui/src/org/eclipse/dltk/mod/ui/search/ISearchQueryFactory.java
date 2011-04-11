package org.eclipse.dltk.mod.ui.search;

import org.eclipse.search.ui.ISearchQuery;

public interface ISearchQueryFactory {

	ISearchQuery createQuery(QuerySpecification createQuery);

}

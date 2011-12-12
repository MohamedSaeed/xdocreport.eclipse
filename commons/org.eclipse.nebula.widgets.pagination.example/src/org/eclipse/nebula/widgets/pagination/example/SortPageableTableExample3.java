/*******************************************************************************
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.pagination.example;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.nebula.widgets.pagination.LazyTableSelectionListener;
import org.eclipse.nebula.widgets.pagination.springdata.PageLoaderStrategyHelper;
import org.eclipse.nebula.widgets.pagination.springdata.PageableController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.springframework.data.domain.collections.PageListHelper;
import org.springframework.data.domain.collections.PageLoader;

/**
 * Basic Picture control example.
 * 
 */
public class SortPageableTableExample3 {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);

		final List<String> items = createList();

		int pageSize = 10;

		final Table table = new Table(shell, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		
		// 2) Initialize the table viewer
		final TableViewer viewer = new TableViewer(table);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new LabelProvider());

		final PageLoader pageLoader = PageListHelper.createPageLoader(items);
		final PageableController controller = new PageableController(pageSize);
		controller.addPageChangedListener(PageLoaderStrategyHelper
				.createloadPageAndAddItemsListener(controller, viewer, pageLoader));

		viewer.getTable().addSelectionListener(
				new LazyTableSelectionListener(controller));

		// Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		createColumns(viewer);

		// 3) Set current page to 0 to refresh the table
		controller.setCurrentPage(0);
		
		shell.setSize(350, 250);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static void createColumns(final TableViewer viewer) {

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(viewer, "Name", 150);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String p = (String) element;
				return p;
			}
		});
		// col.getColumn().addSelectionListener(
		// new SortTableColumnSelectionListener("name"));
		// // col.getColumn().addSelectionListener(new SelectionAdapter() {
		//
		// private boolean b = false;
		//
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// Order order = new Order(b ? Direction.ASC : Direction.DESC,
		// "name");
		// Sort sort = new Sort(order);
		// pageableTable.getController().setSort(sort);
		// pageableTable.refreshPage();
		// b = !b;
		// }
		//
		// });
	}

	private static List<String> createList() {
		List<String> names = new ArrayList<String>();
		for (int i = 1; i < 2012; i++) {
			names.add("Name " + i);
		}
		return names;
	}

	private static TableViewerColumn createTableViewerColumn(
			TableViewer viewer, String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
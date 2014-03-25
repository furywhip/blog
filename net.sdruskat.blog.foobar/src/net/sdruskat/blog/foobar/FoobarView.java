package net.sdruskat.blog.foobar;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

public class FoobarView extends ViewPart {
	
	@Override
    public void createPartControl(Composite parent) {
        SourceViewer sourceViewer = new SourceViewer(parent, null, null, false, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.READ_ONLY);
        // Add a MenuManager
        MenuManager menuManager = new MenuManager ();
        Menu menu = menuManager.createContextMenu(sourceViewer.getControl());
        sourceViewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuManager, sourceViewer);
        // Make the selection available to the workbench
        getSite().setSelectionProvider(sourceViewer);
        Document document = new Document("We do need some text here, don't we!");
        sourceViewer.setDocument(document);
    }
	
	@Override
	public void setFocus() {
		
	}
}
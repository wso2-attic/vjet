package org.ebayopensource.vjet.eclipse.internal.debug.ui.launchConf;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchLabelProvider;


/**
 * Customized {@link FilteredResourcesSelectionDialog} for selecting 
 * JavaScript script files
 * 
 * @since 1.0
 */
public class ScriptSelectionDialog extends FilteredResourcesSelectionDialog {

	/**
	 * Default extension
	 */
	public static final String JS_EXTENSION_PATTERN = "*.js"; //$NON-NLS-1$

	private final class ScriptLabelProvider extends LabelProvider implements ILabelProviderListener {
		private ListenerList listeners = new ListenerList();
		WorkbenchLabelProvider provider = new WorkbenchLabelProvider();
		ILabelDecorator decorator = PlatformUI.getWorkbench()
		  	.getDecoratorManager().getLabelDecorator();

		public Image getImage(Object element) {
			               if (!(element instanceof IResource)) {
			                 return super.getImage(element);
			             }
			
			             IResource res = (IResource) element;
			
			             Image img = provider.getImage(res);
			
			             return decorator.decorateImage(img, res);
			         }

		/*
		  * (non-)
		  *
		  * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		  */
		 public String  getText(Object  element) {
		     if (!(element instanceof IResource)) {
		         return super.getText(element);
		     }

		     IResource res = (IResource) element;

		     String str = res.getName() + " - " + res.getParent().getFullPath().makeRelative().toString();

		     // extra info for duplicates
 if (isDuplicateElement(element))
		         str = str  + " - " + res.getParent().getFullPath().makeRelative().toString(); //$NON-NLS-1$

		     return decorator.decorateText(str, res);
		 }

		/*
		  * (non-)
		  *
		  * @see org.eclipse.jface.viewers.LabelProvider#dispose()
		  */
		 public void dispose() {
		     provider.removeListener(this);
		     provider.dispose();

		     decorator.removeListener(this);
		     decorator.dispose();

		     super.dispose();
		 }

		/*
		  * (non-)
		  *
		  * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
		  */
		 public void addListener(ILabelProviderListener listener) {
		     listeners.add(listener);
		 }

		/*
		  * (non-)
		  *
		  * @see org.eclipse.jface.viewers.LabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
		  */
		 public void removeListener(ILabelProviderListener listener) {
		     listeners.remove(listener);
		 }

		/*
		  * (non-)
		  *
		  * @see org.eclipse.jface.viewers.ILabelProviderListener#labelProviderChanged(org.eclipse.jface.viewers.LabelProviderChangedEvent)
		  */
		 public void labelProviderChanged(LabelProviderChangedEvent event) {
		     Object [] l = listeners.getListeners();
		     for (int i = 0; i < listeners.size(); i++) {
		         ((ILabelProviderListener) l[i]).labelProviderChanged(event);
		     }
		 }
	}

	class ScriptFilter extends ResourceFilter {
		/* (non-)
		 * @see org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog.ResourceFilter#matchItem(java.lang.Object)
		 */
		public boolean matchItem(Object item) {
			return super.matchItem(item) && isJavaScriptContent((IFile) item);
		}
		
		boolean isJavaScriptContent(IFile file) {
			
			// TODO should filter out vjo meta types  
			
			IContentType filetype = IDE.getContentType(file);
			if(filetype == null) {
				return false;
			}
    		IContentType scripttype = Platform.getContentTypeManager().getContentType("org.eclipse.dltk.mod.vjoContentType");
    		return filetype.equals(scripttype);
//    		return true;
		}
	}
	
	/**
	 * Constructor
	 * @param shell
	 * @param multi
	 * @param container
	 */
	public ScriptSelectionDialog(Shell shell, boolean multi, IContainer container) {
		super(shell, multi, container, IResource.FILE);
		setInitialPattern(JS_EXTENSION_PATTERN);
		 setListLabelProvider( new ScriptLabelProvider());
	   
	}
	
	
	/* (non-)
	 * @see org.eclipse.ui.dialogs.SelectionDialog#getMessage()
	 */
	protected String getMessage() {
		return "Select JavaScript File";
	}
	
	/* (non-)
	 * @see org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog#createFilter()
	 */
	protected ItemsFilter createFilter() {
		return new ScriptFilter();
	}
}

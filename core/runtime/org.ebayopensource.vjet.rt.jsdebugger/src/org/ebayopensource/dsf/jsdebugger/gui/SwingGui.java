/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger.gui;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.MenuComponent;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.ebayopensource.dsf.jsdebugger.gui.BreakpointCache.Breakpoints;
import org.ebayopensource.dsf.jsdi.FunctionSource;
import org.ebayopensource.dsf.jsdi.IDebuggerControl;
import org.ebayopensource.dsf.jsdi.IGuiCallback;
import org.ebayopensource.dsf.jsdi.ISourceInfo;
import org.ebayopensource.dsf.jsdi.IValue;
import org.ebayopensource.dsf.jsdi.StackFrameInfo;
import org.ebayopensource.dsf.jsdi.Value;
import org.ebayopensource.dsf.jsdi.Variable;
import org.ebayopensource.dsf.jsdi.VariableType;
import org.ebayopensource.dsf.jsdi.agent.remote.LocalSourceInfo;
import org.mozilla.mod.javascript.Kit;

/**
 * GUI for the Rhino debugger.
 */
public class SwingGui extends JFrame implements IGuiCallback {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = -8217029773456711621L;

	/**
	 * The debugger.
	 */

	IDebuggerControl m_debuggerControl;

	/**
	 * The action to run when the 'Exit' menu item is chosen or the frame is
	 * closed.
	 */
	private Runnable m_exitAction;

	/**
	 * The {@link JDesktopPane} that holds the script windows.
	 */
	private JDesktopPane m_desk;

	/**
	 * The {@link JPanel} that shows information about the context.
	 */
	private ContextWindow m_contextWindow;

	/**
	 * The menu bar.
	 */
	private Menubar m_menubar;

	/**
	 * The tool bar.
	 */
	private JToolBar m_toolBar;

	/**
	 * The console that displays I/O from the script.
	 */
	private JSInternalConsole m_console;

	/**
	 * The {@link JSplitPane} that separates {@link #desk} from
	 * {@link org.mozilla.javascript.Context}.
	 */
	private JSplitPane m_split1;

	/**
	 * The status bar.
	 */
	private JLabel m_statusBar;

	/**
	 * Hash table of internal frame names to the internal frames themselves.
	 */
	private Hashtable<String, JFrame> m_toplevels = new Hashtable<String, JFrame>();

	/**
	 * Hash table of script URLs to their internal frames.
	 */
	private Hashtable<String, FileWindow> m_fileWindows = new Hashtable<String, FileWindow>();

	/**
	 * The {@link FileWindow} that last had the focus.
	 */
	private FileWindow m_currentWindow;

	/**
	 * The AWT EventQueue. Used for manually pumping AWT events from
	 * {@link #dispatchNextGuiEvent()}.
	 */
	private EventQueue m_awtEventQueue;
	
	/**
	 * File choose dialog for loading a script.
	 */
	static JFileChooser s_fileChooserDlg = null;
	
	/**
	 * Creates a new SwingGui.
	 */
	public SwingGui(String title) {
		super(title);
		init();
	}
	
	public void connect(IDebuggerControl debuggerControl) {
		m_debuggerControl = debuggerControl;
		try {
			m_debuggerControl.setGuiCallback(this);
		} catch (RemoteException e) {
		}

		if (m_fileWindows.size() == 0) {
			return;
		}	
		clear();
	}
	
	private void clear() {
		FileWindow[] windows = new FileWindow[m_fileWindows.size()];
		m_fileWindows.values().toArray(windows);
		for (FileWindow window : windows) {
			m_desk.remove(window);
			window.dispose();
		}
		m_desk.revalidate();
		m_desk.updateUI();
		m_fileWindows.clear();
		m_contextWindow.reset();
	}


	/**
	 * Returns the Menubar of this debugger frame.
	 */
	public Menubar getMenubar() {
		return m_menubar;
	}

	/**
	 * Sets the {@link Runnable} that will be run when the "Exit" menu item is
	 * chosen.
	 */
	public void setExitAction(Runnable r) {
		m_exitAction = r;
	}

	/**
	 * Returns the debugger console component.
	 */
	public JSInternalConsole getConsole() {
		return m_console;
	}

	/**
	 * Sets the visibility of the debugger GUI.
	 */
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			// this needs to be done after the window is visible
			m_console.consoleTextArea.requestFocus();
			m_contextWindow.m_split.setDividerLocation(0.5);
			try {
				m_console.setMaximum(true);
				m_console.setSelected(true);
				m_console.show();
				m_console.consoleTextArea.requestFocus();
			} catch (Exception exc) {
			}
		}
	}

	/**
	 * Records a new internal frame.
	 */
	void addTopLevel(String key, JFrame frame) {
		if (frame != this) {
			m_toplevels.put(key, frame);
		}
	}

	/**
	 * Constructs the debugger GUI.
	 */
	private void init() {
		m_menubar = new Menubar(this);
		setJMenuBar(m_menubar);
		m_toolBar = new JToolBar();
		JButton button;
		JButton breakButton, goButton, stepIntoButton, stepOverButton, stepOutButton;
		String[] toolTips = { "Break (Pause)", "Go (F5)", "Step Into (F11)",
				"Step Over (F7)", "Step Out (F8)" };
		int count = 0;
		button = breakButton = new JButton("Break");
		button.setToolTipText("Break");
		button.setActionCommand("Break");
		button.addActionListener(m_menubar);
		button.setEnabled(true);
		button.setToolTipText(toolTips[count++]);

		button = goButton = new JButton("Go");
		button.setToolTipText("Go");
		button.setActionCommand("Go");
		button.addActionListener(m_menubar);
		button.setEnabled(false);
		button.setToolTipText(toolTips[count++]);

		button = stepIntoButton = new JButton("Step Into");
		button.setToolTipText("Step Into");
		button.setActionCommand("Step Into");
		button.addActionListener(m_menubar);
		button.setEnabled(false);
		button.setToolTipText(toolTips[count++]);

		button = stepOverButton = new JButton("Step Over");
		button.setToolTipText("Step Over");
		button.setActionCommand("Step Over");
		button.setEnabled(false);
		button.addActionListener(m_menubar);
		button.setToolTipText(toolTips[count++]);

		button = stepOutButton = new JButton("Step Out");
		button.setToolTipText("Step Out");
		button.setActionCommand("Step Out");
		button.setEnabled(false);
		button.addActionListener(m_menubar);
		button.setToolTipText(toolTips[count++]);

		Dimension dim = stepOverButton.getPreferredSize();
		breakButton.setPreferredSize(dim);
		breakButton.setMinimumSize(dim);
		breakButton.setMaximumSize(dim);
		breakButton.setSize(dim);
		goButton.setPreferredSize(dim);
		goButton.setMinimumSize(dim);
		goButton.setMaximumSize(dim);
		stepIntoButton.setPreferredSize(dim);
		stepIntoButton.setMinimumSize(dim);
		stepIntoButton.setMaximumSize(dim);
		stepOverButton.setPreferredSize(dim);
		stepOverButton.setMinimumSize(dim);
		stepOverButton.setMaximumSize(dim);
		stepOutButton.setPreferredSize(dim);
		stepOutButton.setMinimumSize(dim);
		stepOutButton.setMaximumSize(dim);
		m_toolBar.add(breakButton);
		m_toolBar.add(goButton);
		m_toolBar.add(stepIntoButton);
		m_toolBar.add(stepOverButton);
		m_toolBar.add(stepOutButton);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		getContentPane().add(m_toolBar, BorderLayout.NORTH);
		getContentPane().add(contentPane, BorderLayout.CENTER);
		m_desk = new JDesktopPane();
		m_desk.setPreferredSize(new Dimension(600, 300));
		m_desk.setMinimumSize(new Dimension(150, 50));
		m_desk.add(m_console = new JSInternalConsole("JavaScript Console"));
		m_contextWindow = new ContextWindow(this);
		m_contextWindow.setPreferredSize(new Dimension(600, 120));
		m_contextWindow.setMinimumSize(new Dimension(50, 50));

		m_split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, m_desk, m_contextWindow);
		m_split1.setOneTouchExpandable(true);
		SwingGui.setResizeWeight(m_split1, 0.66);
		contentPane.add(m_split1, BorderLayout.CENTER);
		m_statusBar = new JLabel();
		m_statusBar.setText("Thread: ");
		contentPane.add(m_statusBar, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	
	/**
	 * JFileChooser dialog can't be peroperly created by servlet request
	 * thread. To enable it , one needs to call this method in main thread.
	 */
	public static void initFileChooser() {
		if (s_fileChooserDlg != null) {
			return;
		}
		s_fileChooserDlg = new JFileChooser();
		javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String n = f.getName();
				int i = n.lastIndexOf('.');
				if (i > 0 && i < n.length() - 1) {
					String ext = n.substring(i + 1).toLowerCase();
					if (ext.equals("js")) {
						return true;
					}
				}
				return false;
			}

			public String getDescription() {
				return "JavaScript Files (*.js)";
			}
		};
		s_fileChooserDlg.addChoosableFileFilter(filter);
	}

	/**
	 * Runs the {@link #m_exitAction}.
	 */
	private void exit() {
		if (m_exitAction != null) {
			SwingUtilities.invokeLater(m_exitAction);
		}
		try {
			if (m_debuggerControl != null) {
				m_debuggerControl.setReturnValue(IDebuggerControl.EXIT);
			}
			else {
				System.exit(0);
			}
		} catch (RemoteException e) {
		}
	}

	/**
	 * Returns the {@link FileWindow} for the given URL.
	 */
	FileWindow getFileWindow(String url) {
		if (url == null || url.equals("<stdin>")) {
			return null;
		}
		return m_fileWindows.get(url);
	}

	/**
	 * Returns a short version of the given URL.
	 */
	static String getShortName(String url) {
		int lastSlash = url.lastIndexOf('/');
		if (lastSlash < 0) {
			lastSlash = url.lastIndexOf('\\');
		}
		String shortName = url;
		if (lastSlash >= 0 && lastSlash + 1 < url.length()) {
			shortName = url.substring(lastSlash + 1);
		}
		return shortName;
	}

	/**
	 * Closes the given {@link FileWindow}.
	 */
	void removeWindow(FileWindow w) {
		m_fileWindows.remove(w.getUrl());
		JMenu windowMenu = getWindowMenu();
		int count = windowMenu.getItemCount();
		JMenuItem lastItem = windowMenu.getItem(count - 1);
		String name = getShortName(w.getUrl());
		for (int i = 5; i < count; i++) {
			JMenuItem item = windowMenu.getItem(i);
			if (item == null)
				continue; // separator
			String text = item.getText();
			// 1 D:\foo.js
			// 2 D:\bar.js
			int pos = text.indexOf(' ');
			if (text.substring(pos + 1).equals(name)) {
				windowMenu.remove(item);
				// Cascade [0]
				// Tile [1]
				// ------- [2]
				// Console [3]
				// ------- [4]
				if (count == 6) {
					// remove the final separator
					windowMenu.remove(4);
				} else {
					int j = i - 4;
					for (; i < count - 1; i++) {
						JMenuItem thisItem = windowMenu.getItem(i);
						if (thisItem != null) {
							// 1 D:\foo.js
							// 2 D:\bar.js
							text = thisItem.getText();
							if (text.equals("More Windows...")) {
								break;
							} else {
								pos = text.indexOf(' ');
								thisItem.setText((char) ('0' + j) + " "
										+ text.substring(pos + 1));
								thisItem.setMnemonic('0' + j);
								j++;
							}
						}
					}
					if (count - 6 == 0 && lastItem != item) {
						if (lastItem.getText().equals("More Windows...")) {
							windowMenu.remove(lastItem);
						}
					}
				}
				break;
			}
		}
		windowMenu.revalidate();
	}

	/**
	 * Shows the line at which execution in the given stack frame just stopped.
	 */
	void showStopLine(String sourceName, int lineNumber) {
		if (sourceName == null || sourceName.equals("<stdin>")) {
			if (m_console.isVisible()) {
				m_console.show();
			}
		} else {
			showFileWindow(sourceName, -1);
			FileWindow w = getFileWindow(sourceName);
			if (w != null) {
				setFilePosition(w, lineNumber);
			}
		}
	}

	/**
	 * Shows a {@link FileWindow} for the given source, creating it if it
	 * doesn't exist yet. if <code>lineNumber</code> is greater than -1, it
	 * indicates the line number to select and display.
	 * 
	 * @param sourceUrl
	 *            the source URL
	 * @param lineNumber
	 *            the line number to select, or -1
	 */
	protected void showFileWindow(String sourceUrl, int lineNumber) {
		FileWindow w = getFileWindow(sourceUrl);
		if (w == null) {
			try {
				ISourceInfo si = m_debuggerControl.getSourceInfo(sourceUrl);
				createFileWindow(si, -1);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			w = getFileWindow(sourceUrl);
		}
		if (lineNumber > -1) {
			int start = w.getPosition(lineNumber - 1);
			int end = w.getPosition(lineNumber) - 1;
			w.m_textArea.select(start);
			w.m_textArea.setCaretPosition(start);
			w.m_textArea.moveCaretPosition(end);
		}
		try {
			if (w.isIcon()) {
				w.setIcon(false);
			}
			w.setVisible(true);
			w.moveToFront();
			w.setSelected(true);
			requestFocus();
			w.requestFocus();
			w.m_textArea.requestFocus();
		} catch (Exception exc) {
		}
	}

	/**
	 * Creates and shows a new {@link FileWindow} for the given source.
	 */
	protected void createFileWindow(ISourceInfo sourceInfo, int line) throws RemoteException {
		boolean activate = true;

		String url = sourceInfo.getUri();
		FileWindow w = new FileWindow(this, sourceInfo);
		m_fileWindows.put(url, w);
		if (line != -1) {
			if (m_currentWindow != null) {
				m_currentWindow.setPosition(-1);
			}
			try {
				w.setPosition(w.m_textArea.getLineStartOffset(line - 1));
			} catch (BadLocationException exc) {
				try {
					w.setPosition(w.m_textArea.getLineStartOffset(0));
				} catch (BadLocationException ee) {
					w.setPosition(-1);
				}
			}
		}
		m_desk.add(w);
		if (line != -1) {
			m_currentWindow = w;
		}
		m_menubar.addFile(url);
		w.setVisible(true);

		if (activate) {
			try {
				w.setMaximum(true);
				w.setSelected(true);
				w.moveToFront();
			} catch (Exception exc) {
			}
		}
	}

	/**
	 * Update the source text for <code>sourceInfo</code>. This returns true
	 * if a {@link FileWindow} for the given source exists and could be updated.
	 * Otherwise, this does nothing and returns false.
	 * 
	 * @param sourceInfo
	 *            the source info
	 * @return true if a {@link FileWindow} for the given source exists and
	 *         could be updated, false otherwise.
	 */
	protected boolean updateFileWindow(ISourceInfo sourceInfo) throws RemoteException {
		String fileName = sourceInfo.getUri();
		FileWindow w = getFileWindow(fileName);
		if (w != null) {
			w.updateText(sourceInfo);
			w.show();
			return true;
		}
		return false;
	}

	/**
	 * Moves the current position in the given {@link FileWindow} to the given
	 * line.
	 */
	private void setFilePosition(FileWindow w, int line) {
		boolean activate = true;
		JTextArea ta = w.m_textArea;
		try {
			if (line == -1) {
				w.setPosition(-1);
				if (m_currentWindow == w) {
					m_currentWindow = null;
				}
			} else {
				int loc = ta.getLineStartOffset(line - 1);
				if (m_currentWindow != null && m_currentWindow != w) {
					m_currentWindow.setPosition(-1);
				}
				w.setPosition(loc);
				m_currentWindow = w;
			}
		} catch (BadLocationException exc) {
			// fix me
		}
		if (activate) {
			if (w.isIcon()) {
				m_desk.getDesktopManager().deiconifyFrame(w);
			}
			m_desk.getDesktopManager().activateFrame(w);
			try {
				w.show();
				w.toFront(); // required for correct frame layering (JDK
								// 1.4.1)
				w.setSelected(true);
			} catch (Exception exc) {
			}
		}
	}

	/**
	 * Handles script interruption.
	 * @throws RemoteException 
	 */
	void enterInterruptImpl(StackFrameInfo frameInfo, String threadTitle,
			String alertMessage) throws RemoteException {
		m_statusBar.setText("Thread: " + threadTitle);

		showStopLine(frameInfo.getSourceName(), frameInfo.getLineNumber());

		if (alertMessage != null) {
			MessageDialogWrapper.showMessageDialog(this, alertMessage,
					"Exception in Script", JOptionPane.ERROR_MESSAGE);
		}

		updateEnabled(true);

		JComboBox ctx = m_contextWindow.m_context;
		Vector<String> toolTips = m_contextWindow.m_toolTips;
		m_contextWindow.disableUpdate();
		int frameCount = m_debuggerControl.getFrameCount();
		ctx.removeAllItems();
		// workaround for JDK 1.4 bug that caches selected value even after
		// removeAllItems() is called
		ctx.setSelectedItem(null);
		toolTips.removeAllElements();
		for (int i = 0; i < frameCount; i++) {
			StackFrameInfo frame = m_debuggerControl.getFrameInfo(i);
			String url = frame.getSourceName();
			int lineNumber = frame.getLineNumber();
			String shortName = url;
			if (url.length() > 20) {
				shortName = "..." + url.substring(url.length() - 17);
			}
			String location = "\"" + shortName + "\", line " + lineNumber;
			ctx.insertItemAt(location, i);
			location = "\"" + url + "\", line " + lineNumber;
			toolTips.addElement(location);
		}
		m_contextWindow.enableUpdate();
		ctx.setSelectedIndex(0);
		ctx.setMinimumSize(new Dimension(50, ctx.getMinimumSize().height));
	}

	/**
	 * Returns the 'Window' menu.
	 */
	private JMenu getWindowMenu() {
		return m_menubar.getMenu(3);
	}

	/**
	 * Displays a {@link JFileChooser} and returns the selected filename.
	 */
	private String chooseFile(String title) {
		if (s_fileChooserDlg == null) {
			return null;
		}
		s_fileChooserDlg.setDialogTitle(title);
		File CWD = null;
		String dir = System.getProperty("user.dir");
		if (dir != null) {
			CWD = new File(dir);
		}
		if (CWD != null) {
			s_fileChooserDlg.setCurrentDirectory(CWD);
		}
		int returnVal = s_fileChooserDlg.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				String result = s_fileChooserDlg.getSelectedFile().getCanonicalPath();
				CWD = s_fileChooserDlg.getSelectedFile().getParentFile();
				Properties props = System.getProperties();
				props.put("user.dir", CWD.getPath());
				System.setProperties(props);
				return result;
			} catch (IOException ignored) {
			} catch (SecurityException ignored) {
			}
		}
		return null;
	}

	/**
	 * Returns the current selected internal frame.
	 */
	private JInternalFrame getSelectedFrame() {
		JInternalFrame[] frames = m_desk.getAllFrames();
		for (int i = 0; i < frames.length; i++) {
			if (frames[i].isShowing()) {
				return frames[i];
			}
		}
		return frames[frames.length - 1];
	}

	/**
	 * Enables or disables the menu and tool bars with respect to the state of
	 * script execution.
	 */
	private void updateEnabled(boolean interrupted) {
		((Menubar) getJMenuBar()).updateEnabled(interrupted);
		for (int ci = 0, cc = m_toolBar.getComponentCount(); ci < cc; ci++) {
			boolean enableButton;
			if (ci == 0) {
				// Break
				enableButton = !interrupted;
			} else {
				enableButton = interrupted;
			}
			m_toolBar.getComponent(ci).setEnabled(enableButton);
		}
		if (interrupted) {
			m_toolBar.setEnabled(true);
			// raise the debugger window
			int state = getExtendedState();
			if (state == Frame.ICONIFIED) {
				setExtendedState(Frame.NORMAL);
			}
			toFront();
			m_contextWindow.enable();
		} else {
			if (m_currentWindow != null)
				m_currentWindow.setPosition(-1);
			m_contextWindow.disable();
		}
	}

	/**
	 * Calls {@link JSplitPane#setResizeWeight} via reflection. For
	 * compatibility, since JDK &lt; 1.3 does not have this method.
	 */
	static void setResizeWeight(JSplitPane pane, double weight) {
		try {
			Method m = JSplitPane.class.getMethod("setResizeWeight",
					new Class[] { double.class });
			m.invoke(pane, new Object[] { new Double(weight) });
		} catch (NoSuchMethodException exc) {
		} catch (IllegalAccessException exc) {
		} catch (java.lang.reflect.InvocationTargetException exc) {
		}
	}

	/**
	 * Reads the file with the given name and returns its contents as a String.
	 */
	private String readFile(String fileName) {
		String text;
		try {
			Reader r = new FileReader(fileName);
			try {
				text = Kit.readReader(r);
			} finally {
				r.close();
			}
		} catch (IOException ex) {
			MessageDialogWrapper.showMessageDialog(this, ex.getMessage(),
					"Error reading " + fileName, JOptionPane.ERROR_MESSAGE);
			text = null;
		}
		return text;
	}

	// GuiCallback

	/**
	 * Called when the source text for a script has been updated.
	 */
	public void updateSourceText(ISourceInfo sourceInfo) {
		RunProxy proxy = new RunProxy(this, RunProxy.UPDATE_SOURCE_TEXT);
		proxy.m_sourceInfo = sourceInfo;
		SwingUtilities.invokeLater(proxy);
	}

	/**
	 * Called when the interrupt loop has been entered.
	 * @throws RemoteException 
	 */
	public void enterInterrupt(StackFrameInfo frameInfo, String threadTitle,
			String alertMessage) throws RemoteException {

		if (SwingUtilities.isEventDispatchThread()) {
			enterInterruptImpl(frameInfo, threadTitle, alertMessage);
		} else {
			RunProxy proxy = new RunProxy(this, RunProxy.ENTER_INTERRUPT);
			proxy.m_lastFrame = frameInfo;
			proxy.m_threadTitle = threadTitle;
			proxy.m_alertMessage = alertMessage;
			SwingUtilities.invokeLater(proxy);
		}
	}

	/**
	 * Returns whether the current thread is the GUI event thread.
	 */
	public boolean isGuiEventThread() {
		return SwingUtilities.isEventDispatchThread();
	}

	/**
	 * Processes the next GUI event.
	 */
	public void dispatchNextGuiEvent() throws InterruptedException {
		EventQueue queue = m_awtEventQueue;
		if (queue == null) {
			queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
			m_awtEventQueue = queue;
		}
		AWTEvent event = queue.getNextEvent();
		if (event instanceof ActiveEvent) {
			((ActiveEvent) event).dispatch();
		} else {
			Object source = event.getSource();
			if (source instanceof Component) {
				Component comp = (Component) source;
				comp.dispatchEvent(event);
			} else if (source instanceof MenuComponent) {
				((MenuComponent) source).dispatchEvent(event);
			}
		}
	}
	
	public int[] getBreakPoints(String url) {
		Breakpoints bpCache = BreakpointCache.getBreakpoints(url);
		if (bpCache == null) {
			return null;
		}
		return bpCache.getBreakpoints();
	}
		
	public void detach(boolean shutdown) {
		clear();
		if (shutdown) {
			setVisible(false);
			dispose();
		}
		m_debuggerControl = null;
	}

	// ActionListener
	/**
	 * Performs an action from the menu or toolbar.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		int returnValue = -1;
		if (cmd.equals("Cut") || cmd.equals("Copy") || cmd.equals("Paste")) {
			JInternalFrame f = getSelectedFrame();
			if (f != null && f instanceof ActionListener) {
				((ActionListener) f).actionPerformed(e);
			}
		} else if (cmd.equals("Step Over")) {
			returnValue = IDebuggerControl.STEP_OVER;
		} else if (cmd.equals("Step Into")) {
			returnValue = IDebuggerControl.STEP_INTO;
		} else if (cmd.equals("Step Out")) {
			returnValue = IDebuggerControl.STEP_OUT;
		} else if (cmd.equals("Go")) {
			returnValue = IDebuggerControl.GO;
		} else if (cmd.equals("Break")) {
			try {
				m_debuggerControl.setBreak();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (cmd.equals("Exit")) {
			exit();
		} else if (cmd.equals("Open")) {
			String fileName = chooseFile("Select a file to compile");
			if (fileName != null) {
				String text = readFile(fileName);
				if (text != null) {
					RunProxy proxy = new RunProxy(this, RunProxy.OPEN_FILE);
					proxy.m_fileName = fileName;
					proxy.m_text = text;
					new Thread(proxy).start();
				}
			}
		} else if (cmd.equals("Load")) {
			String fileName = chooseFile("Select a file to execute");
			if (fileName != null) {
				String text = readFile(fileName);
				if (text != null) {
					RunProxy proxy = new RunProxy(this, RunProxy.LOAD_FILE);
					proxy.m_fileName = fileName;
					proxy.m_text = text;
					new Thread(proxy).start();
				}
			}
		} else if (cmd.equals("More Windows...")) {
			MoreWindows dlg = new MoreWindows(this, m_fileWindows, "Window",
					"Files");
			dlg.showDialog(this);
		} else if (cmd.equals("Console")) {
			if (m_console.isIcon()) {
				m_desk.getDesktopManager().deiconifyFrame(m_console);
			}
			m_console.show();
			m_desk.getDesktopManager().activateFrame(m_console);
			m_console.consoleTextArea.requestFocus();
		} else if (cmd.equals("Cut")) {
		} else if (cmd.equals("Copy")) {
		} else if (cmd.equals("Paste")) {
		} else if (cmd.equals("Go to function...")) {
			FindFunction dlg = new FindFunction(this, "Go to function",
					"Function");
			dlg.showDialog(this);
		} else if (cmd.equals("Tile")) {
			JInternalFrame[] frames = m_desk.getAllFrames();
			int count = frames.length;
			int rows, cols;
			rows = cols = (int) Math.sqrt(count);
			if (rows * cols < count) {
				cols++;
				if (rows * cols < count) {
					rows++;
				}
			}
			Dimension size = m_desk.getSize();
			int w = size.width / cols;
			int h = size.height / rows;
			int x = 0;
			int y = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					int index = (i * cols) + j;
					if (index >= frames.length) {
						break;
					}
					JInternalFrame f = frames[index];
					try {
						f.setIcon(false);
						f.setMaximum(false);
					} catch (Exception exc) {
					}
					m_desk.getDesktopManager().setBoundsForFrame(f, x, y, w, h);
					x += w;
				}
				y += h;
				x = 0;
			}
		} else if (cmd.equals("Cascade")) {
			JInternalFrame[] frames = m_desk.getAllFrames();
			int count = frames.length;
			int x, y, w, h;
			x = y = 0;
			h = m_desk.getHeight();
			int d = h / count;
			if (d > 30)
				d = 30;
			for (int i = count - 1; i >= 0; i--, x += d, y += d) {
				JInternalFrame f = frames[i];
				try {
					f.setIcon(false);
					f.setMaximum(false);
				} catch (Exception exc) {
				}
				Dimension dimen = f.getPreferredSize();
				w = dimen.width;
				h = dimen.height;
				m_desk.getDesktopManager().setBoundsForFrame(f, x, y, w, h);
			}
		} else {
			Object obj = getFileWindow(cmd);
			if (obj != null) {
				FileWindow w = (FileWindow) obj;
				try {
					if (w.isIcon()) {
						w.setIcon(false);
					}
					w.setVisible(true);
					w.moveToFront();
					w.setSelected(true);
				} catch (Exception exc) {
				}
			}
		}
		if (returnValue != -1) {
			updateEnabled(false);
			try {
				m_debuggerControl.setReturnValue(returnValue);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

/**
 * Helper class for showing a message dialog.
 */
class MessageDialogWrapper {

	/**
	 * Shows a message dialog, wrapping the <code>msg</code> at 60 columns.
	 */
	public static void showMessageDialog(Component parent, String msg,
			String title, int flags) {
		if (msg.length() > 60) {
			StringBuffer buf = new StringBuffer();
			int len = msg.length();
			int j = 0;
			int i;
			for (i = 0; i < len; i++, j++) {
				char c = msg.charAt(i);
				buf.append(c);
				if (Character.isWhitespace(c)) {
					int k;
					for (k = i + 1; k < len; k++) {
						if (Character.isWhitespace(msg.charAt(k))) {
							break;
						}
					}
					if (k < len) {
						int nextWordLen = k - i;
						if (j + nextWordLen > 60) {
							buf.append('\n');
							j = 0;
						}
					}
				}
			}
			msg = buf.toString();
		}
		JOptionPane.showMessageDialog(parent, msg, title, flags);
	}
}

/**
 * Extension of JTextArea for script evaluation input.
 */
class EvalTextArea extends JTextArea implements KeyListener, DocumentListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = -3918033649601064194L;

	/**
	 * The debugger GUI.
	 */
	private SwingGui debugGui;

	/**
	 * History of expressions that have been evaluated
	 */
	private Vector<String> history;

	/**
	 * Index of the selected history item.
	 */
	private int historyIndex = -1;

	/**
	 * Position in the display where output should go.
	 */
	private int outputMark;

	/**
	 * Creates a new EvalTextArea.
	 */
	public EvalTextArea(SwingGui debugGui) {
		this.debugGui = debugGui;
		history = new Vector<String>();
		Document doc = getDocument();
		doc.addDocumentListener(this);
		addKeyListener(this);
		setLineWrap(true);
		setFont(new Font("Monospaced", 0, 12));
		append("% ");
		outputMark = doc.getLength();
	}

	/**
	 * Selects a subrange of the text.
	 */
	public void select(int start, int end) {
		// requestFocus();
		super.select(start, end);
	}

	/**
	 * Called when Enter is pressed.
	 * @throws RemoteException 
	 */
	private synchronized void returnPressed() throws RemoteException {
		Document doc = getDocument();
		int len = doc.getLength();
		Segment segment = new Segment();
		try {
			doc.getText(outputMark, len - outputMark, segment);
		} catch (javax.swing.text.BadLocationException ignored) {
			ignored.printStackTrace();
		}
		String text = segment.toString();
		if (debugGui.m_debuggerControl.stringIsCompilableUnit(text)) {
			if (text.trim().length() > 0) {
				history.addElement(text);
				historyIndex = history.size();
			}
			append("\n");
			String result = debugGui.m_debuggerControl.eval(text);
			if (result.length() > 0) {
				append(result);
				append("\n");
			}
			append("% ");
			outputMark = doc.getLength();
		} else {
			append("\n");
		}
	}

	/**
	 * Writes output into the text area.
	 */
	public synchronized void write(String str) {
		insert(str, outputMark);
		int len = str.length();
		outputMark += len;
		select(outputMark, outputMark);
	}

	// KeyListener

	/**
	 * Called when a key is pressed.
	 */
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_LEFT) {
			if (outputMark == getCaretPosition()) {
				e.consume();
			}
		} else if (code == KeyEvent.VK_HOME) {
			int caretPos = getCaretPosition();
			if (caretPos == outputMark) {
				e.consume();
			} else if (caretPos > outputMark) {
				if (!e.isControlDown()) {
					if (e.isShiftDown()) {
						moveCaretPosition(outputMark);
					} else {
						setCaretPosition(outputMark);
					}
					e.consume();
				}
			}
		} else if (code == KeyEvent.VK_ENTER) {
			try {
				returnPressed();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.consume();
		} else if (code == KeyEvent.VK_UP) {
			historyIndex--;
			if (historyIndex >= 0) {
				if (historyIndex >= history.size()) {
					historyIndex = history.size() - 1;
				}
				if (historyIndex >= 0) {
					String str = (String) history.elementAt(historyIndex);
					int len = getDocument().getLength();
					replaceRange(str, outputMark, len);
					int caretPos = outputMark + str.length();
					select(caretPos, caretPos);
				} else {
					historyIndex++;
				}
			} else {
				historyIndex++;
			}
			e.consume();
		} else if (code == KeyEvent.VK_DOWN) {
			int caretPos = outputMark;
			if (history.size() > 0) {
				historyIndex++;
				if (historyIndex < 0) {
					historyIndex = 0;
				}
				int len = getDocument().getLength();
				if (historyIndex < history.size()) {
					String str = (String) history.elementAt(historyIndex);
					replaceRange(str, outputMark, len);
					caretPos = outputMark + str.length();
				} else {
					historyIndex = history.size();
					replaceRange("", outputMark, len);
				}
			}
			select(caretPos, caretPos);
			e.consume();
		}
	}

	/**
	 * Called when a key is typed.
	 */
	public void keyTyped(KeyEvent e) {
		int keyChar = e.getKeyChar();
		if (keyChar == 0x8 /* KeyEvent.VK_BACK_SPACE */) {
			if (outputMark == getCaretPosition()) {
				e.consume();
			}
		} else if (getCaretPosition() < outputMark) {
			setCaretPosition(outputMark);
		}
	}

	/**
	 * Called when a key is released.
	 */
	public synchronized void keyReleased(KeyEvent e) {
	}

	// DocumentListener

	/**
	 * Called when text was inserted into the text area.
	 */
	public synchronized void insertUpdate(DocumentEvent e) {
		int len = e.getLength();
		int off = e.getOffset();
		if (outputMark > off) {
			outputMark += len;
		}
	}

	/**
	 * Called when text was removed from the text area.
	 */
	public synchronized void removeUpdate(DocumentEvent e) {
		int len = e.getLength();
		int off = e.getOffset();
		if (outputMark > off) {
			if (outputMark >= off + len) {
				outputMark -= len;
			} else {
				outputMark = off;
			}
		}
	}

	/**
	 * Attempts to clean up the damage done by {@link #updateUI()}.
	 */
	public synchronized void postUpdateUI() {
		// requestFocus();
		setCaret(getCaret());
		select(outputMark, outputMark);
	}

	/**
	 * Called when text has changed in the text area.
	 */
	public synchronized void changedUpdate(DocumentEvent e) {
	}
}

/**
 * An internal frame for evaluating script.
 */
class EvalWindow extends JInternalFrame implements ActionListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = -2860585845212160176L;

	/**
	 * The text area into which expressions can be typed.
	 */
	private EvalTextArea evalTextArea;

	/**
	 * Creates a new EvalWindow.
	 */
	public EvalWindow(String name, SwingGui debugGui) {
		super(name, true, false, true, true);
		evalTextArea = new EvalTextArea(debugGui);
		evalTextArea.setRows(24);
		evalTextArea.setColumns(80);
		JScrollPane scroller = new JScrollPane(evalTextArea);
		setContentPane(scroller);
		// scroller.setPreferredSize(new Dimension(600, 400));
		pack();
		setVisible(true);
	}

	/**
	 * Sets whether the text area is enabled.
	 */
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		evalTextArea.setEnabled(b);
	}

	// ActionListener

	/**
	 * Performs an action on the text area.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Cut")) {
			evalTextArea.cut();
		} else if (cmd.equals("Copy")) {
			evalTextArea.copy();
		} else if (cmd.equals("Paste")) {
			evalTextArea.paste();
		}
	}
}

/**
 * Internal frame for the console.
 */
class JSInternalConsole extends JInternalFrame implements ActionListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = -5523468828771087292L;

	/**
	 * Creates a new JSInternalConsole.
	 */
	public JSInternalConsole(String name) {
		super(name, true, false, true, true);
		consoleTextArea = new ConsoleTextArea(null);
		consoleTextArea.setRows(24);
		consoleTextArea.setColumns(80);
		JScrollPane scroller = new JScrollPane(consoleTextArea);
		setContentPane(scroller);
		pack();
		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameActivated(InternalFrameEvent e) {
				// hack
				if (consoleTextArea.hasFocus()) {
					consoleTextArea.getCaret().setVisible(false);
					consoleTextArea.getCaret().setVisible(true);
				}
			}
		});
	}

	/**
	 * The console text area.
	 */
	ConsoleTextArea consoleTextArea;

	/**
	 * Returns the input stream of the console text area.
	 */
	public InputStream getIn() {
		return consoleTextArea.getIn();
	}

	/**
	 * Returns the output stream of the console text area.
	 */
	public PrintStream getOut() {
		return consoleTextArea.getOut();
	}

	/**
	 * Returns the error stream of the console text area.
	 */
	public PrintStream getErr() {
		return consoleTextArea.getErr();
	}

	// ActionListener

	/**
	 * Performs an action on the text area.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Cut")) {
			consoleTextArea.cut();
		} else if (cmd.equals("Copy")) {
			consoleTextArea.copy();
		} else if (cmd.equals("Paste")) {
			consoleTextArea.paste();
		}
	}
}

/**
 * Popup menu class for right-clicking on {@link FileTextArea}s.
 */
class FilePopupMenu extends JPopupMenu {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 3589525009546013565L;

	/**
	 * The popup x position.
	 */
	int x;

	/**
	 * The popup y position.
	 */
	int y;

	/**
	 * Creates a new FilePopupMenu.
	 */
	public FilePopupMenu(FileTextArea w) {
		JMenuItem item;
		add(item = new JMenuItem("Set Breakpoint"));
		item.addActionListener(w);
		add(item = new JMenuItem("Clear Breakpoint"));
		item.addActionListener(w);
		add(item = new JMenuItem("Run"));
		item.addActionListener(w);
	}

	/**
	 * Displays the menu at the given coordinates.
	 */
	public void show(JComponent comp, int x, int y) {
		this.x = x;
		this.y = y;
		super.show(comp, x, y);
	}
}

/**
 * Text area to display script source.
 */
class FileTextArea extends JTextArea implements ActionListener,
		PopupMenuListener, KeyListener, MouseListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = -25032065448563720L;

	/**
	 * The owning {@link FileWindow}.
	 */
	private FileWindow w;

	/**
	 * The popup menu.
	 */
	private FilePopupMenu popup;

	/**
	 * Creates a new FileTextArea.
	 */
	public FileTextArea(FileWindow w) {
		this.w = w;
		popup = new FilePopupMenu(this);
		popup.addPopupMenuListener(this);
		addMouseListener(this);
		addKeyListener(this);
		setFont(new Font("Monospaced", 0, 12));
	}

	/**
	 * Moves the selection to the given offset.
	 */
	public void select(int pos) {
		if (pos >= 0) {
			try {
				int line = getLineOfOffset(pos);
				Rectangle rect = modelToView(pos);
				if (rect == null) {
					select(pos, pos);
				} else {
					try {
						Rectangle nrect = modelToView(getLineStartOffset(line + 1));
						if (nrect != null) {
							rect = nrect;
						}
					} catch (Exception exc) {
					}
					JViewport vp = (JViewport) getParent();
					Rectangle viewRect = vp.getViewRect();
					if (viewRect.y + viewRect.height > rect.y) {
						// need to scroll up
						select(pos, pos);
					} else {
						// need to scroll down
						rect.y += (viewRect.height - rect.height) / 2;
						scrollRectToVisible(rect);
						select(pos, pos);
					}
				}
			} catch (BadLocationException exc) {
				select(pos, pos);
				// exc.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the popup menu should be shown.
	 */
	private void checkPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup.show(this, e.getX(), e.getY());
		}
	}

	// MouseListener

	/**
	 * Called when a mouse button is pressed.
	 */
	public void mousePressed(MouseEvent e) {
		checkPopup(e);
	}

	/**
	 * Called when the mouse is clicked.
	 */
	public void mouseClicked(MouseEvent e) {
		checkPopup(e);
		requestFocus();
		getCaret().setVisible(true);
	}

	/**
	 * Called when the mouse enters the component.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Called when the mouse exits the component.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Called when a mouse button is released.
	 */
	public void mouseReleased(MouseEvent e) {
		checkPopup(e);
	}

	// PopupMenuListener

	/**
	 * Called before the popup menu will become visible.
	 */
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	}

	/**
	 * Called before the popup menu will become invisible.
	 */
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}

	/**
	 * Called when the popup menu is cancelled.
	 */
	public void popupMenuCanceled(PopupMenuEvent e) {
	}

	// ActionListener

	/**
	 * Performs an action.
	 */
	public void actionPerformed(ActionEvent e) {
		int pos = viewToModel(new Point(popup.x, popup.y));
		popup.setVisible(false);
		String cmd = e.getActionCommand();
		int line = -1;
		try {
			line = getLineOfOffset(pos);
		} catch (Exception exc) {
		}
		if (cmd.equals("Set Breakpoint")) {
			w.setBreakPoint(line + 1);
		} else if (cmd.equals("Clear Breakpoint")) {
			w.clearBreakPoint(line + 1);
		} else if (cmd.equals("Run")) {
			w.load();
		}
	}

	// KeyListener

	/**
	 * Called when a key is pressed.
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_DELETE:
		case KeyEvent.VK_TAB:
			e.consume();
			break;
		}
	}

	/**
	 * Called when a key is typed.
	 */
	public void keyTyped(KeyEvent e) {
		e.consume();
	}

	/**
	 * Called when a key is released.
	 */
	public void keyReleased(KeyEvent e) {
		e.consume();
	}
}

/**
 * Dialog to list the available windows.
 */
class MoreWindows extends JDialog implements ActionListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 5177066296457377546L;

	/**
	 * Last selected value.
	 */
	private String value;

	/**
	 * The list component.
	 */
	private JList list;

	/**
	 * Our parent frame.
	 */
	private SwingGui swingGui;

	/**
	 * The "Select" button.
	 */
	private JButton setButton;

	/**
	 * The "Cancel" button.
	 */
	private JButton cancelButton;

	/**
	 * Creates a new MoreWindows.
	 */
	MoreWindows(SwingGui frame, Hashtable<String, FileWindow> fileWindows,
			String title, String labelText) {
		super(frame, title, true);
		this.swingGui = frame;
		// buttons
		cancelButton = new JButton("Cancel");
		setButton = new JButton("Select");
		cancelButton.addActionListener(this);
		setButton.addActionListener(this);
		getRootPane().setDefaultButton(setButton);

		// dim part of the dialog
		list = new JList(new DefaultListModel());
		DefaultListModel model = (DefaultListModel) list.getModel();
		model.clear();
		// model.fireIntervalRemoved(model, 0, size);
		Enumeration e = fileWindows.keys();
		while (e.hasMoreElements()) {
			String data = e.nextElement().toString();
			model.addElement(data);
		}
		list.setSelectedIndex(0);
		// model.fireIntervalAdded(model, 0, data.length);
		setButton.setEnabled(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.addMouseListener(new MouseHandler());
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(320, 240));
		// XXX: Must do the following, too, or else the scroller thinks
		// XXX: it's taller than it is:
		listScroller.setMinimumSize(new Dimension(250, 80));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);

		// Create a container so that we can add a title around
		// the scroll pane. Can't add a title directly to the
		// scroll pane because its background would be white.
		// Lay out the label and scroll pane from top to button.
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
		JLabel label = new JLabel(labelText);
		label.setLabelFor(list);
		listPane.add(label);
		listPane.add(Box.createRigidArea(new Dimension(0, 5)));
		listPane.add(listScroller);
		listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Lay out the buttons from left to right.
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(setButton);

		// Put everything together, using the content pane's BorderLayout.
		Container contentPane = getContentPane();
		contentPane.add(listPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		pack();
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				int code = ke.getKeyCode();
				if (code == KeyEvent.VK_ESCAPE) {
					ke.consume();
					value = null;
					setVisible(false);
				}
			}
		});
	}

	/**
	 * Shows the dialog.
	 */
	public String showDialog(Component comp) {
		value = null;
		setLocationRelativeTo(comp);
		setVisible(true);
		return value;
	}

	// ActionListener

	/**
	 * Performs an action.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Cancel")) {
			setVisible(false);
			value = null;
		} else if (cmd.equals("Select")) {
			value = (String) list.getSelectedValue();
			setVisible(false);
			swingGui.showFileWindow(value, -1);
		}
	}

	/**
	 * MouseListener implementation for {@link #list}.
	 */
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				setButton.doClick();
			}
		}
	}
}

/**
 * Find function dialog.
 */
class FindFunction extends JDialog implements ActionListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 559491015232880916L;

	/**
	 * Last selected function.
	 */
	private String value;

	/**
	 * List of functions.
	 */
	private JList list;

	/**
	 * The debug GUI frame.
	 */
	private SwingGui debugGui;

	/**
	 * The "Select" button.
	 */
	private JButton setButton;

	/**
	 * The "Cancel" button.
	 */
	private JButton cancelButton;

	/**
	 * Creates a new FindFunction.
	 */
	public FindFunction(SwingGui debugGui, String title, String labelText) {
		super(debugGui, title, true);
		this.debugGui = debugGui;

		cancelButton = new JButton("Cancel");
		setButton = new JButton("Select");
		cancelButton.addActionListener(this);
		setButton.addActionListener(this);
		getRootPane().setDefaultButton(setButton);

		list = new JList(new DefaultListModel());
		DefaultListModel model = (DefaultListModel) list.getModel();
		model.clear();

		try {
			String[] a = debugGui.m_debuggerControl.functionNames();
			java.util.Arrays.sort(a);
			for (int i = 0; i < a.length; i++) {
				model.addElement(a[i]);
			}
			list.setSelectedIndex(0);

			setButton.setEnabled(a.length > 0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.addMouseListener(new MouseHandler());
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(320, 240));
		listScroller.setMinimumSize(new Dimension(250, 80));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);

		// Create a container so that we can add a title around
		// the scroll pane. Can't add a title directly to the
		// scroll pane because its background would be white.
		// Lay out the label and scroll pane from top to button.
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
		JLabel label = new JLabel(labelText);
		label.setLabelFor(list);
		listPane.add(label);
		listPane.add(Box.createRigidArea(new Dimension(0, 5)));
		listPane.add(listScroller);
		listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Lay out the buttons from left to right.
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(setButton);

		// Put everything together, using the content pane's BorderLayout.
		Container contentPane = getContentPane();
		contentPane.add(listPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		pack();
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				int code = ke.getKeyCode();
				if (code == KeyEvent.VK_ESCAPE) {
					ke.consume();
					value = null;
					setVisible(false);
				}
			}
		});
	}

	/**
	 * Shows the dialog.
	 */
	public String showDialog(Component comp) {
		value = null;
		setLocationRelativeTo(comp);
		setVisible(true);
		return value;
	}

	// ActionListener

	/**
	 * Performs an action.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Cancel")) {
			setVisible(false);
			value = null;
		} else if (cmd.equals("Select")) {
			if (list.getSelectedIndex() < 0) {
				return;
			}
			try {
				value = (String) list.getSelectedValue();
			} catch (ArrayIndexOutOfBoundsException exc) {
				return;
			}
			setVisible(false);
			FunctionSource item = null;
			try {
				item = debugGui.m_debuggerControl.functionSourceByName(value);
				if (item != null) {
					ISourceInfo si = item.sourceInfo();
					String url = si.getUri();
					int lineNumber = item.firstLine();
					debugGui.showFileWindow(url, lineNumber);
				}
			} catch (RemoteException re) {
				throw new RuntimeException(re);
			}

		}
	}

	/**
	 * MouseListener implementation for {@link #list}.
	 */
	class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				setButton.doClick();
			}
		}
	}
}

/**
 * Gutter for FileWindows.
 */
class FileHeader extends JPanel implements MouseListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = -2858905404778259127L;

	/**
	 * The line that the mouse was pressed on.
	 */
	private int m_pressLine = -1;

	/**
	 * The owning FileWindow.
	 */
	private FileWindow m_fileWindow;

	/**
	 * Creates a new FileHeader.
	 */
	public FileHeader(FileWindow fileWindow) {
		m_fileWindow = fileWindow;
		addMouseListener(this);
		update();
	}

	/**
	 * Updates the gutter.
	 */
	public void update() {
		FileTextArea textArea = m_fileWindow.m_textArea;
		Font font = textArea.getFont();
		setFont(font);
		FontMetrics metrics = getFontMetrics(font);
		int h = metrics.getHeight();
		int lineCount = textArea.getLineCount() + 1;
		String dummy = Integer.toString(lineCount);
		if (dummy.length() < 2) {
			dummy = "99";
		}
		Dimension d = new Dimension();
		d.width = metrics.stringWidth(dummy) + 16;
		d.height = lineCount * h + 100;
		setPreferredSize(d);
		setSize(d);
	}

	/**
	 * Paints the component.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		FileTextArea textArea = m_fileWindow.m_textArea;
		Font font = textArea.getFont();
		g.setFont(font);
		FontMetrics metrics = getFontMetrics(font);
		Rectangle clip = g.getClipBounds();
		g.setColor(getBackground());
		g.fillRect(clip.x, clip.y, clip.width, clip.height);
		int ascent = metrics.getMaxAscent();
		int h = metrics.getHeight();
		int lineCount = textArea.getLineCount() + 1;
		String dummy = Integer.toString(lineCount);
		if (dummy.length() < 2) {
			dummy = "99";
		}
		int startLine = clip.y / h;
		int endLine = (clip.y + clip.height) / h + 1;
		int width = getWidth();
		if (endLine > lineCount)
			endLine = lineCount;
		for (int i = startLine; i < endLine; i++) {
			String text;
			int pos = -2;
			try {
				pos = textArea.getLineStartOffset(i);
			} catch (BadLocationException ignored) {
			}
			boolean isBreakPoint = m_fileWindow.isBreakPoint(i + 1);
			text = Integer.toString(i + 1) + " ";
			int y = i * h;
			g.setColor(Color.blue);
			g.drawString(text, 0, y + ascent);
			int x = width - ascent;
			if (isBreakPoint) {
				g.setColor(new Color(0x80, 0x00, 0x00));
				int dy = y + ascent - 9;
				g.fillOval(x, dy, 9, 9);
				g.drawOval(x, dy, 8, 8);
				g.drawOval(x, dy, 9, 9);
			}
			if (pos == m_fileWindow.m_currentPos) {
				Polygon arrow = new Polygon();
				int dx = x;
				y += ascent - 10;
				int dy = y;
				arrow.addPoint(dx, dy + 3);
				arrow.addPoint(dx + 5, dy + 3);
				for (x = dx + 5; x <= dx + 10; x++, y++) {
					arrow.addPoint(x, y);
				}
				for (x = dx + 9; x >= dx + 5; x--, y++) {
					arrow.addPoint(x, y);
				}
				arrow.addPoint(dx + 5, dy + 7);
				arrow.addPoint(dx, dy + 7);
				g.setColor(Color.yellow);
				g.fillPolygon(arrow);
				g.setColor(Color.black);
				g.drawPolygon(arrow);
			}
		}
	}

	// MouseListener

	/**
	 * Called when the mouse enters the component.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Called when a mouse button is pressed.
	 */
	public void mousePressed(MouseEvent e) {
		Font font = m_fileWindow.m_textArea.getFont();
		FontMetrics metrics = getFontMetrics(font);
		int h = metrics.getHeight();
		m_pressLine = e.getY() / h;
	}

	/**
	 * Called when the mouse is clicked.
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Called when the mouse exits the component.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Called when a mouse button is released.
	 */
	public void mouseReleased(MouseEvent e) {
		if (e.getComponent() == this
				&& (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			int y = e.getY();
			Font font = m_fileWindow.m_textArea.getFont();
			FontMetrics metrics = getFontMetrics(font);
			int h = metrics.getHeight();
			int line = y / h;
			if (line == m_pressLine) {
				m_fileWindow.toggleBreakPoint(line + 1);
			} else {
				m_pressLine = -1;
			}
		}
	}
}

/**
 * An internal frame for script files.
 */
class FileWindow extends JInternalFrame implements ActionListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = -6212382604952082370L;

	/**
	 * The debugger GUI.
	 */
	private SwingGui m_debugGui;

	/**
	 * The SourceInfo object that describes the file.
	 */
	private ISourceInfo m_sourceInfo;
	
	/**
	 * the URL from SourceInfo
	 */
	private String m_url;

	/**
	 * The FileTextArea that displays the file.
	 */
	FileTextArea m_textArea;

	/**
	 * The FileHeader that is the gutter for {@link #m_textArea}.
	 */
	private FileHeader m_fileHeader;

	/**
	 * Scroll pane for containing {@link #m_textArea}.
	 */
	private JScrollPane m_p;

	/**
	 * The current offset position.
	 */
	int m_currentPos;

	/**
	 * Loads the file.
	 */
	void load() {
		String url = getUrl();
		if (url != null) {
			RunProxy proxy = new RunProxy(m_debugGui, RunProxy.LOAD_FILE);
			proxy.m_fileName = url;
			try {
				proxy.m_text = m_sourceInfo.getText();
			} catch (RemoteException re) {
				throw new RuntimeException(re);
			}
			new Thread(proxy).start();
		}
	}

	/**
	 * Returns the offset position for the given line.
	 */
	public int getPosition(int line) {
		int result = -1;
		try {
			result = m_textArea.getLineStartOffset(line);
		} catch (javax.swing.text.BadLocationException exc) {
		}
		return result;
	}

	/**
	 * Returns whether the given line has a breakpoint.
	 */
	public boolean isBreakPoint(int line) {
		try {
			return m_sourceInfo.isBreakableLine(line) && m_sourceInfo.isBreakpoint(line);
		} catch (RemoteException re) {
			return false;
		}
	}

	/**
	 * Toggles the breakpoint on the given line.
	 */
	public void toggleBreakPoint(int line) {
		if (!isBreakPoint(line)) {
			setBreakPoint(line);
		} else {
			clearBreakPoint(line);
		}
	}

	/**
	 * Sets a breakpoint on the given line.
	 */
	public void setBreakPoint(int line) {
		try {
			if (m_sourceInfo.isBreakableLine(line)) {
				boolean changed = m_sourceInfo.setBreakpoint(line, true);
				if (changed) {
					Breakpoints bpCache = BreakpointCache.getBreakpoints(m_sourceInfo.getUri());
					if (bpCache == null) {
						bpCache = BreakpointCache.createBreakpoints(m_sourceInfo.getUri());
					}
					bpCache.setBreakpoint(line, true);
					m_fileHeader.repaint();
				}
			}
		} catch (RemoteException re) {
			throw new RuntimeException(re);
		}
	}

	/**
	 * Clears a breakpoint from the given line.
	 */
	public void clearBreakPoint(int line) {
		try {
			if (m_sourceInfo.isBreakableLine(line)) {
				boolean changed = m_sourceInfo.setBreakpoint(line, false);
				if (changed) {
					Breakpoints bpCache = BreakpointCache.getBreakpoints(m_sourceInfo.getUri());
					if (bpCache != null) {
						bpCache.setBreakpoint(line, false);
					}
					m_fileHeader.repaint();
				}
			}
		} catch (RemoteException re) {
			throw new RuntimeException(re);
		}
	}

	/**
	 * Creates a new FileWindow.
	 */
	public FileWindow(SwingGui debugGui, ISourceInfo sourceInfo) throws RemoteException {
		super(SwingGui.getShortName(sourceInfo.getUri()), true, true, true, true);
		m_debugGui = debugGui;
		m_sourceInfo = sourceInfo;
		m_url = m_sourceInfo.getUri();
		updateToolTip();
		m_currentPos = -1;
		m_textArea = new FileTextArea(this);
		m_textArea.setRows(24);
		m_textArea.setColumns(80);
		m_p = new JScrollPane();
		m_fileHeader = new FileHeader(this);
		m_p.setViewportView(m_textArea);
		m_p.setRowHeaderView(m_fileHeader);
		setContentPane(m_p);
		pack();
		updateText(sourceInfo);
		m_textArea.select(0);
	}

	/**
	 * Updates the tool tip contents.
	 */
	private void updateToolTip() {
		// Try to set tool tip on frame. On Mac OS X 10.5,
		// the number of components is different, so try to be safe.
		int n = getComponentCount() - 1;
		if (n > 1) {
			n = 1;
		} else if (n < 0) {
			return;
		}
		Component c = getComponent(n);
		// this will work at least for Metal L&F
		if (c != null && c instanceof JComponent) {
			((JComponent) c).setToolTipText(getUrl());
		}
	}

	/**
	 * Returns the URL of the source.
	 */
	public String getUrl() {
		return m_url;
	}

	/**
	 * Called when the text of the script has changed.
	 */
	public void updateText(ISourceInfo sourceInfo) throws RemoteException {
		m_sourceInfo = sourceInfo;
		m_url = m_sourceInfo.getUri();
		String newText = sourceInfo.getText();
		if (!m_textArea.getText().equals(newText)) {
			m_textArea.setText(newText);
			int pos = 0;
			if (m_currentPos != -1) {
				pos = m_currentPos;
			}
			m_textArea.select(pos);
		}
		m_fileHeader.update();
		m_fileHeader.repaint();
	}

	/**
	 * Sets the cursor position.
	 */
	public void setPosition(int pos) {
		m_textArea.select(pos);
		m_currentPos = pos;
		m_fileHeader.repaint();
	}

	/**
	 * Selects a range of characters.
	 */
	public void select(int start, int end) {
		int docEnd = m_textArea.getDocument().getLength();
		m_textArea.select(docEnd, docEnd);
		m_textArea.select(start, end);
	}

	/**
	 * Disposes this FileWindow.
	 */
	public void dispose() {
		m_debugGui.removeWindow(this);
		super.dispose();
	}

	// ActionListener

	/**
	 * Performs an action.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Cut")) {
			// textArea.cut();
		} else if (cmd.equals("Copy")) {
			m_textArea.copy();
		} else if (cmd.equals("Paste")) {
			// textArea.paste();
		}
	}
}

/**
 * Table model class for watched expressions.
 */
class SimpleTableModel extends AbstractTableModel {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 2971618907207577000L;

	/**
	 * The debugger GUI.
	 */
	private SwingGui m_debugGui;

	/**
	 * Vector of watched expressions.
	 */
	private Vector<String> m_expressions;

	/**
	 * Vector of values from evaluated from {@link #m_expressions}.
	 */
	private Vector<String> m_values;

	/**
	 * Creates a new MyTableModel.
	 */
	public SimpleTableModel(SwingGui debugGui) {
		m_debugGui = debugGui;
		m_expressions = new Vector<String>();
		m_values = new Vector<String>();
		m_expressions.addElement("");
		m_values.addElement("");
	}
	
	public void clear() {
		m_expressions.clear();
		m_values.clear();
		m_expressions.addElement("");
		m_values.addElement("");
	}

	/**
	 * Returns the number of columns in the table (2).
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * Returns the number of rows in the table.
	 */
	public int getRowCount() {
		return m_expressions.size();
	}

	/**
	 * Returns the name of the given column.
	 */
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Expression";
		case 1:
			return "Value";
		}
		return null;
	}

	/**
	 * Returns whether the given cell is editable.
	 */
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	/**
	 * Returns the value in the given cell.
	 */
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return m_expressions.elementAt(row);
		case 1:
			return m_values.elementAt(row);
		}
		return "";
	}

	/**
	 * Sets the value in the given cell.
	 */
	public void setValueAt(Object value, int row, int column) {
		switch (column) {
		case 0:
			String expr = value.toString();
			m_expressions.setElementAt(expr, row);
			String result = "";
			if (expr.length() > 0) {
				try {
					result = m_debugGui.m_debuggerControl.eval(expr);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (result == null)
					result = "";
			}
			m_values.setElementAt(result, row);
			updateModel();
			if (row + 1 == m_expressions.size()) {
				m_expressions.addElement("");
				m_values.addElement("");
				fireTableRowsInserted(row + 1, row + 1);
			}
			break;
		case 1:
			// just reset column 2; ignore edits
			fireTableDataChanged();
		}
	}

	/**
	 * Re-evaluates the expressions in the table.
	 */
	void updateModel() {
		for (int i = 0; i < m_expressions.size(); ++i) {
			Object value = m_expressions.elementAt(i);
			String expr = value.toString();
			String result = "";
			if (expr.length() > 0) {
				try {
					result = m_debugGui.m_debuggerControl.eval(expr);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (result == null)
					result = "";
			} else {
				result = "";
			}
			result = result.replace('\n', ' ');
			m_values.setElementAt(result, i);
		}
		fireTableDataChanged();
	}
}

/**
 * A table for evaluated expressions.
 */
class Evaluator extends JTable {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 8133672432982594256L;

	/**
	 * The {@link TableModel} for this table.
	 */
	SimpleTableModel tableModel;

	/**
	 * Creates a new Evaluator.
	 */
	public Evaluator(SwingGui debugGui) {
		super(new SimpleTableModel(debugGui));
		tableModel = (SimpleTableModel) getModel();
	}
}

/**
 * A tree table for browsing script objects.
 */
class MyTreeTable extends JTreeTable {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 3457265548184453049L;

	/**
	 * Creates a new MyTreeTable.
	 */
	public MyTreeTable(VariableDataModel model) {
		super(model);
	}

	/**
	 * Initializes a tree for this tree table.
	 */
	public JTree resetTree(TreeTableModel treeTableModel) {
		tree = new TreeTableCellRenderer(treeTableModel);

		// Install a tableModel representing the visible rows in the tree.
		super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

		// Force the JTable and JTree to share their row selection models.
		ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();
		tree.setSelectionModel(selectionWrapper);
		setSelectionModel(selectionWrapper.getListSelectionModel());

		// Make the tree and table row heights the same.
		if (tree.getRowHeight() < 1) {
			// Metal looks better like this.
			setRowHeight(18);
		}

		// Install the tree editor renderer and editor.
		setDefaultRenderer(TreeTableModel.class, tree);
		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
		setShowGrid(true);
		setIntercellSpacing(new Dimension(1, 1));
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		DefaultTreeCellRenderer r = (DefaultTreeCellRenderer) tree
				.getCellRenderer();
		r.setOpenIcon(null);
		r.setClosedIcon(null);
		r.setLeafIcon(null);
		return tree;
	}

	/**
	 * Returns whether the cell under the coordinates of the mouse in the
	 * {@link EventObject} is editable.
	 */
	public boolean isCellEditable(EventObject e) {
		if (e instanceof MouseEvent) {
			MouseEvent me = (MouseEvent) e;
			// If the modifiers are not 0 (or the left mouse button),
			// tree may try and toggle the selection, and table
			// will then try and toggle, resulting in the
			// selection remaining the same. To avoid this, we
			// only dispatch when the modifiers are 0 (or the left mouse
			// button).
			if (me.getModifiers() == 0
					|| ((me.getModifiers() & (InputEvent.BUTTON1_MASK | 1024)) != 0 && (me
							.getModifiers() & (InputEvent.SHIFT_MASK
							| InputEvent.CTRL_MASK | InputEvent.ALT_MASK
							| InputEvent.BUTTON2_MASK | InputEvent.BUTTON3_MASK
							| 64 | // SHIFT_DOWN_MASK
							128 | // CTRL_DOWN_MASK
							512 | // ALT_DOWN_MASK
							2048 | // BUTTON2_DOWN_MASK
					4096 // BUTTON3_DOWN_MASK
					)) == 0)) {
				int row = rowAtPoint(me.getPoint());
				for (int counter = getColumnCount() - 1; counter >= 0; counter--) {
					if (TreeTableModel.class == getColumnClass(counter)) {
						MouseEvent newME = new MouseEvent(
								MyTreeTable.this.tree, me.getID(),
								me.getWhen(), me.getModifiers(), me.getX()
										- getCellRect(row, counter, true).x, me
										.getY(), me.getClickCount(), me
										.isPopupTrigger());
						MyTreeTable.this.tree.dispatchEvent(newME);
						break;
					}
				}
			}
			if (me.getClickCount() >= 3) {
				return true;
			}
			return false;
		}
		if (e == null) {
			return true;
		}
		return false;
	}
}

/**
 * Panel that shows information about the context.
 */
class ContextWindow extends JPanel implements ActionListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 2306040975490228051L;

	/**
	 * The debugger GUI.
	 */
	private SwingGui m_debugGui;

	/**
	 * The combo box that holds the stack frames.
	 */
	JComboBox m_context;

	/**
	 * Tool tips for the stack frames.
	 */
	Vector<String> m_toolTips;

	/**
	 * Tabbed pane for "this" and "locals".
	 */
	private JTabbedPane m_tabs;

	/**
	 * Tabbed pane for "watch" and "evaluate".
	 */
	private JTabbedPane m_tabs2;

	/**
	 * The table showing the "this" object.
	 */
	private MyTreeTable m_thisTable;

	/**
	 * The table showing the stack local variables.
	 */
	private MyTreeTable m_localsTable;

	/**
	 * The {@link #m_evaluator}'s table model.
	 */
	private SimpleTableModel m_watchTableModel;

	/**
	 * The script evaluator table.
	 */
	private Evaluator m_evaluator;

	/**
	 * The script evaluation text area.
	 */
	private EvalTextArea m_cmdLine;

	/**
	 * The split pane.
	 */
	JSplitPane m_split;

	/**
	 * Whether the ContextWindow is enabled.
	 */
	private boolean m_enabled;

	/**
	 * Creates a new ContextWindow.
	 */
	public ContextWindow(final SwingGui debugGui) {
		m_debugGui = debugGui;
		m_enabled = false;
		JPanel left = new JPanel();
		JToolBar t1 = new JToolBar();
		t1.setName("Variables");
		t1.setLayout(new GridLayout());
		t1.add(left);
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout());
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout());
		p1.add(t1);
		JLabel label = new JLabel("Context:");
		m_context = new JComboBox();
		m_context.setLightWeightPopupEnabled(false);
		m_toolTips = new Vector<String>();
		label.setBorder(m_context.getBorder());
		m_context.addActionListener(this);
		m_context.setActionCommand("ContextSwitch");
		GridBagLayout layout = new GridBagLayout();
		left.setLayout(layout);
		GridBagConstraints lc = new GridBagConstraints();
		lc.insets.left = 5;
		lc.anchor = GridBagConstraints.WEST;
		lc.ipadx = 5;
		layout.setConstraints(label, lc);
		left.add(label);
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		layout.setConstraints(m_context, c);
		left.add(m_context);
		m_tabs = new JTabbedPane(SwingConstants.BOTTOM);
		m_tabs.setPreferredSize(new Dimension(500, 300));
		m_thisTable = new MyTreeTable(new VariableDataModel());
		JScrollPane jsp = new JScrollPane(m_thisTable);
		jsp.getViewport().setViewSize(new Dimension(5, 2));
		m_tabs.add("this", jsp);
		m_localsTable = new MyTreeTable(new VariableDataModel());
		m_localsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		m_localsTable.setPreferredSize(null);
		jsp = new JScrollPane(m_localsTable);
		m_tabs.add("Locals", jsp);
		c.weightx = c.weighty = 1;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		layout.setConstraints(m_tabs, c);
		left.add(m_tabs);
		m_evaluator = new Evaluator(debugGui);
		m_cmdLine = new EvalTextArea(debugGui);
		// cmdLine.requestFocus();
		m_watchTableModel = m_evaluator.tableModel;
		jsp = new JScrollPane(m_evaluator);
		JToolBar t2 = new JToolBar();
		t2.setName("Evaluate");
		m_tabs2 = new JTabbedPane(SwingConstants.BOTTOM);
		m_tabs2.add("Watch", jsp);
		m_tabs2.add("Evaluate", new JScrollPane(m_cmdLine));
		m_tabs2.setPreferredSize(new Dimension(500, 300));
		t2.setLayout(new GridLayout());
		t2.add(m_tabs2);
		p2.add(t2);
		m_evaluator.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		m_split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
		m_split.setOneTouchExpandable(true);
		SwingGui.setResizeWeight(m_split, 0.5);
		setLayout(new BorderLayout());
		add(m_split, BorderLayout.CENTER);

		final JToolBar finalT1 = t1;
		final JToolBar finalT2 = t2;
		final JPanel finalP1 = p1;
		final JPanel finalP2 = p2;
		final JSplitPane finalSplit = m_split;
		final JPanel finalThis = this;

		ComponentListener clistener = new ComponentListener() {
			boolean t2Docked = true;

			void check(Component comp) {
				Component thisParent = finalThis.getParent();
				if (thisParent == null) {
					return;
				}
				Component parent = finalT1.getParent();
				boolean leftDocked = true;
				boolean rightDocked = true;
				boolean adjustVerticalSplit = false;
				if (parent != null) {
					if (parent != finalP1) {
						while (!(parent instanceof JFrame)) {
							parent = parent.getParent();
						}
						JFrame frame = (JFrame) parent;
						debugGui.addTopLevel("Variables", frame);

						// We need the following hacks because:
						// - We want an undocked toolbar to be
						// resizable.
						// - We are using JToolbar as a container of a
						// JComboBox. Without this JComboBox's popup
						// can get left floating when the toolbar is
						// re-docked.
						//
						// We make the frame resizable and then
						// remove JToolbar's window listener
						// and insert one of our own that first ensures
						// the JComboBox's popup window is closed
						// and then calls JToolbar's window listener.
						if (!frame.isResizable()) {
							frame.setResizable(true);
							frame
									.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
							final EventListener[] l = frame
									.getListeners(WindowListener.class);
							frame.removeWindowListener((WindowListener) l[0]);
							frame.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
									m_context.hidePopup();
									((WindowListener) l[0]).windowClosing(e);
								}
							});
							// adjustVerticalSplit = true;
						}
						leftDocked = false;
					} else {
						leftDocked = true;
					}
				}
				parent = finalT2.getParent();
				if (parent != null) {
					if (parent != finalP2) {
						while (!(parent instanceof JFrame)) {
							parent = parent.getParent();
						}
						JFrame frame = (JFrame) parent;
						debugGui.addTopLevel("Evaluate", frame);
						frame.setResizable(true);
						rightDocked = false;
					} else {
						rightDocked = true;
					}
				}
				if (leftDocked && t2Docked && rightDocked && t2Docked) {
					// no change
					return;
				}
				t2Docked = rightDocked;
				JSplitPane split = (JSplitPane) thisParent;
				if (leftDocked) {
					if (rightDocked) {
						finalSplit.setDividerLocation(0.5);
					} else {
						finalSplit.setDividerLocation(1.0);
					}
					if (adjustVerticalSplit) {
						split.setDividerLocation(0.66);
					}

				} else if (rightDocked) {
					finalSplit.setDividerLocation(0.0);
					split.setDividerLocation(0.66);
				} else {
					// both undocked
					split.setDividerLocation(1.0);
				}
			}

			public void componentHidden(ComponentEvent e) {
				check(e.getComponent());
			}

			public void componentMoved(ComponentEvent e) {
				check(e.getComponent());
			}

			public void componentResized(ComponentEvent e) {
				check(e.getComponent());
			}

			public void componentShown(ComponentEvent e) {
				check(e.getComponent());
			}
		};
		p1.addContainerListener(new ContainerListener() {
			public void componentAdded(ContainerEvent e) {
				Component thisParent = finalThis.getParent();
				JSplitPane split = (JSplitPane) thisParent;
				if (e.getChild() == finalT1) {
					if (finalT2.getParent() == finalP2) {
						// both docked
						finalSplit.setDividerLocation(0.5);
					} else {
						// left docked only
						finalSplit.setDividerLocation(1.0);
					}
					split.setDividerLocation(0.66);
				}
			}

			public void componentRemoved(ContainerEvent e) {
				Component thisParent = finalThis.getParent();
				JSplitPane split = (JSplitPane) thisParent;
				if (e.getChild() == finalT1) {
					if (finalT2.getParent() == finalP2) {
						// right docked only
						finalSplit.setDividerLocation(0.0);
						split.setDividerLocation(0.66);
					} else {
						// both undocked
						split.setDividerLocation(1.0);
					}
				}
			}
		});
		t1.addComponentListener(clistener);
		t2.addComponentListener(clistener);
		disable();
	}

	/**
	 * Disables the component.
	 */
	public void disable() {
		m_context.setEnabled(false);
		m_thisTable.resetTree(new VariableDataModel());
		m_thisTable.setEnabled(false);
		m_localsTable.resetTree(new VariableDataModel());
		m_localsTable.setEnabled(false);
		m_evaluator.setEnabled(false);
		m_cmdLine.setEnabled(false);
	}

	/**
	 * Enables the component.
	 */
	public void enable() {
		m_context.setEnabled(true);
		m_thisTable.setEnabled(true);
		m_localsTable.setEnabled(true);
		m_evaluator.setEnabled(true);
		m_cmdLine.setEnabled(true);
	}
	
	public void reset() {
		disable();
		m_watchTableModel.clear();
	}

	/**
	 * Disables updating of the component.
	 */
	public void disableUpdate() {
		m_enabled = false;
	}

	/**
	 * Enables updating of the component.
	 */
	public void enableUpdate() {
		m_enabled = true;
	}

	// ActionListener

	/**
	 * Performs an action.
	 */
	public void actionPerformed(ActionEvent e) {
		if (!m_enabled)
			return;
		if (e.getActionCommand().equals("ContextSwitch")) {
			//TODO
			try {
			int frameIndex = m_context.getSelectedIndex();
			m_context.setToolTipText(m_toolTips.elementAt(frameIndex).toString());
			int frameCount = m_debugGui.m_debuggerControl.getFrameCount();
			if (frameIndex >= frameCount) {
				return;
			}
			m_debugGui.m_debuggerControl.contextSwitch(frameIndex);
			StackFrameInfo frame = m_debugGui.m_debuggerControl.getFrameInfo(frameIndex);
			m_thisTable.resetTree(new VariableDataModel(
				new Variable("this", new Value(VariableType.OBJECT, "this", IValue.THIS_ID)), m_debugGui.m_debuggerControl));
			VariableDataModel scopeModel;
			if (frame.hasDifferentScope()) {
				scopeModel = new VariableDataModel(
					new Variable("scope", new Value(VariableType.OBJECT, "scope", IValue.SCOPE_ID)), m_debugGui.m_debuggerControl);
			} else {
				scopeModel = new VariableDataModel();
			}
			m_localsTable.resetTree(scopeModel);
			//debugGui.m_debuggerControl.contextSwitch(frameIndex);
			m_debugGui.showStopLine(frame.getSourceName(), frame.getLineNumber());
			m_watchTableModel.updateModel();
			}
			catch (RemoteException re) {
				re.printStackTrace();
			}
		}
	}
}

/**
 * The debugger frame menu bar.
 */
class Menubar extends JMenuBar implements ActionListener {

	/**
	 * Serializable magic number.
	 */
	private static final long serialVersionUID = 3217170497245911461L;

	/**
	 * Items that are enabled only when interrupted.
	 */
	private Vector<JMenuItem> m_interruptOnlyItems = new Vector<JMenuItem>();

	/**
	 * Items that are enabled only when running.
	 */
	private Vector<JMenuItem> m_runOnlyItems = new Vector<JMenuItem>();

	/**
	 * The debugger GUI.
	 */
	private SwingGui m_debugGui;

	/**
	 * The menu listing the internal frames.
	 */
	private JMenu m_windowMenu;

	/**
	 * The "Break on exceptions" menu item.
	 */
	private JCheckBoxMenuItem m_breakOnExceptions;

	/**
	 * The "Break on enter" menu item.
	 */
	private JCheckBoxMenuItem m_breakOnEnter;

	/**
	 * The "Break on return" menu item.
	 */
	private JCheckBoxMenuItem m_breakOnReturn;

	/**
	 * Creates a new Menubar.
	 */
	Menubar(SwingGui debugGui) {
		super();
		m_debugGui = debugGui;
		String[] fileItems = { "Open...", "Run...", "", "Exit" };
		String[] fileCmds = { "Open", "Load", "", "Exit" };
		char[] fileShortCuts = { '0', 'N', 0, 'X' };
		int[] fileAccelerators = { KeyEvent.VK_O, KeyEvent.VK_N, 0,
				KeyEvent.VK_Q };
		String[] editItems = { "Cut", "Copy", "Paste", "Go to function..." };
		char[] editShortCuts = { 'T', 'C', 'P', 'F' };
		String[] debugItems = { "Break", "Go", "Step Into", "Step Over",
				"Step Out" };
		char[] debugShortCuts = { 'B', 'G', 'I', 'O', 'T' };
		String[] plafItems = { "Metal", "Windows", "Motif" };
		char[] plafShortCuts = { 'M', 'W', 'F' };
		int[] debugAccelerators = { KeyEvent.VK_PAUSE, KeyEvent.VK_F5,
				KeyEvent.VK_F11, KeyEvent.VK_F7, KeyEvent.VK_F8, 0, 0 };

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic('E');
		JMenu plafMenu = new JMenu("Platform");
		plafMenu.setMnemonic('P');
		JMenu debugMenu = new JMenu("Debug");
		debugMenu.setMnemonic('D');
		m_windowMenu = new JMenu("Window");
		m_windowMenu.setMnemonic('W');
		for (int i = 0; i < fileItems.length; ++i) {
			if (fileItems[i].length() == 0) {
				fileMenu.addSeparator();
			} else {
				JMenuItem item = new JMenuItem(fileItems[i], fileShortCuts[i]);
				item.setActionCommand(fileCmds[i]);
				item.addActionListener(this);
				fileMenu.add(item);
				if (fileAccelerators[i] != 0) {
					KeyStroke k = KeyStroke.getKeyStroke(fileAccelerators[i],
							Event.CTRL_MASK);
					item.setAccelerator(k);
				}
			}
		}
		for (int i = 0; i < editItems.length; ++i) {
			JMenuItem item = new JMenuItem(editItems[i], editShortCuts[i]);
			item.addActionListener(this);
			editMenu.add(item);
		}
		for (int i = 0; i < plafItems.length; ++i) {
			JMenuItem item = new JMenuItem(plafItems[i], plafShortCuts[i]);
			item.addActionListener(this);
			plafMenu.add(item);
		}
		for (int i = 0; i < debugItems.length; ++i) {
			JMenuItem item = new JMenuItem(debugItems[i], debugShortCuts[i]);
			item.addActionListener(this);
			if (debugAccelerators[i] != 0) {
				KeyStroke k = KeyStroke.getKeyStroke(debugAccelerators[i], 0);
				item.setAccelerator(k);
			}
			if (i != 0) {
				m_interruptOnlyItems.add(item);
			} else {
				m_runOnlyItems.add(item);
			}
			debugMenu.add(item);
		}
		m_breakOnExceptions = new JCheckBoxMenuItem("Break on Exceptions");
		m_breakOnExceptions.setMnemonic('X');
		m_breakOnExceptions.addActionListener(this);
		m_breakOnExceptions.setSelected(false);
		debugMenu.add(m_breakOnExceptions);

		m_breakOnEnter = new JCheckBoxMenuItem("Break on Function Enter");
		m_breakOnEnter.setMnemonic('E');
		m_breakOnEnter.addActionListener(this);
		m_breakOnEnter.setSelected(false);
		debugMenu.add(m_breakOnEnter);

		m_breakOnReturn = new JCheckBoxMenuItem("Break on Function Return");
		m_breakOnReturn.setMnemonic('R');
		m_breakOnReturn.addActionListener(this);
		m_breakOnReturn.setSelected(false);
		debugMenu.add(m_breakOnReturn);

		add(fileMenu);
		add(editMenu);
		// add(plafMenu);
		add(debugMenu);
		JMenuItem item;
		m_windowMenu.add(item = new JMenuItem("Cascade", 'A'));
		item.addActionListener(this);
		m_windowMenu.add(item = new JMenuItem("Tile", 'T'));
		item.addActionListener(this);
		m_windowMenu.addSeparator();
		m_windowMenu.add(item = new JMenuItem("Console", 'C'));
		item.addActionListener(this);
		add(m_windowMenu);

		updateEnabled(false);
	}

	/**
	 * Returns the "Break on exceptions" menu item.
	 */
	public JCheckBoxMenuItem getBreakOnExceptions() {
		return m_breakOnExceptions;
	}

	/**
	 * Returns the "Break on enter" menu item.
	 */
	public JCheckBoxMenuItem getBreakOnEnter() {
		return m_breakOnEnter;
	}

	/**
	 * Returns the "Break on return" menu item.
	 */
	public JCheckBoxMenuItem getBreakOnReturn() {
		return m_breakOnReturn;
	}

	/**
	 * Returns the "Debug" menu.
	 */
	public JMenu getDebugMenu() {
		return getMenu(2);
	}

	// ActionListener

	/**
	 * Performs an action.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		String plaf_name = null;
		if (cmd.equals("Metal")) {
			plaf_name = "javax.swing.plaf.metal.MetalLookAndFeel";
		} else if (cmd.equals("Windows")) {
			plaf_name = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		} else if (cmd.equals("Motif")) {
			plaf_name = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		} else {
			Object source = e.getSource();
			try {
			if (source == m_breakOnExceptions) {
				m_debugGui.m_debuggerControl.setBreakOnExceptions(m_breakOnExceptions
						.isSelected());
			} else if (source == m_breakOnEnter) {
				m_debugGui.m_debuggerControl.setBreakOnEnter(m_breakOnEnter.isSelected());
			} else if (source == m_breakOnReturn) {
				m_debugGui.m_debuggerControl.setBreakOnReturn(m_breakOnReturn.isSelected());
			} else {
				m_debugGui.actionPerformed(e);
			}
			}
			catch (RemoteException re) {
				re.printStackTrace();
			}
			return;
		}
		try {
			UIManager.setLookAndFeel(plaf_name);
			SwingUtilities.updateComponentTreeUI(m_debugGui);
			if (SwingGui.s_fileChooserDlg != null) {
				SwingUtilities.updateComponentTreeUI(SwingGui.s_fileChooserDlg);
			}			
		} catch (Exception ignored) {
			// ignored.printStackTrace();
		}
	}

	/**
	 * Adds a file to the window menu.
	 */
	public void addFile(String url) {
		int count = m_windowMenu.getItemCount();
		JMenuItem item;
		if (count == 4) {
			m_windowMenu.addSeparator();
			count++;
		}
		JMenuItem lastItem = m_windowMenu.getItem(count - 1);
		boolean hasMoreWin = false;
		int maxWin = 5;
		if (lastItem != null && lastItem.getText().equals("More Windows...")) {
			hasMoreWin = true;
			maxWin++;
		}
		if (!hasMoreWin && count - 4 == 5) {
			m_windowMenu.add(item = new JMenuItem("More Windows...", 'M'));
			item.setActionCommand("More Windows...");
			item.addActionListener(this);
			return;
		} else if (count - 4 <= maxWin) {
			if (hasMoreWin) {
				count--;
				m_windowMenu.remove(lastItem);
			}
			String shortName = SwingGui.getShortName(url);

			m_windowMenu.add(item = new JMenuItem((char) ('0' + (count - 4))
					+ " " + shortName, '0' + (count - 4)));
			if (hasMoreWin) {
				m_windowMenu.add(lastItem);
			}
		} else {
			return;
		}
		item.setActionCommand(url);
		item.addActionListener(this);
	}

	/**
	 * Updates the enabledness of menu items.
	 */
	public void updateEnabled(boolean interrupted) {
		for (int i = 0; i != m_interruptOnlyItems.size(); ++i) {
			JMenuItem item = m_interruptOnlyItems.elementAt(i);
			item.setEnabled(interrupted);
		}

		for (int i = 0; i != m_runOnlyItems.size(); ++i) {
			JMenuItem item = m_runOnlyItems.elementAt(i);
			item.setEnabled(!interrupted);
		}
	}
}

/**
 * Class to consolidate all cases that require to implement Runnable to avoid
 * class generation bloat.
 */
class RunProxy implements Runnable {

	// Constants for 'type'.
	static final int OPEN_FILE = 1;

	static final int LOAD_FILE = 2;

	static final int UPDATE_SOURCE_TEXT = 3;

	static final int ENTER_INTERRUPT = 4;

	/**
	 * The debugger GUI.
	 */
	private SwingGui m_debugGui;

	/**
	 * The type of Runnable this object is. Takes one of the constants defined
	 * in this class.
	 */
	private int m_type;

	/**
	 * The name of the file to open or load.
	 */
	String m_fileName;

	/**
	 * The source text to update.
	 */
	String m_text;

	/**
	 * The source for which to update the text.
	 */
	ISourceInfo m_sourceInfo;

	/**
	 * The frame to interrupt in.
	 */
	StackFrameInfo m_lastFrame;

	/**
	 * The name of the interrupted thread.
	 */
	String m_threadTitle;

	/**
	 * The message of the exception thrown that caused the thread interruption,
	 * if any.
	 */
	String m_alertMessage;

	/**
	 * Creates a new RunProxy.
	 */
	public RunProxy(SwingGui debugGui, int type) {
		m_debugGui = debugGui;
		m_type = type;
	}

	/**
	 * Runs this Runnable.
	 */
	public void run() {
		switch (m_type) {
		case OPEN_FILE:
			if (m_debugGui.m_debuggerControl != null) {
				try {
					m_debugGui.m_debuggerControl.compileScript(m_fileName, m_text);
				} catch (RuntimeException ex) {
					MessageDialogWrapper.showMessageDialog(m_debugGui, ex
							.getMessage(), "Error Compiling " + m_fileName,
							JOptionPane.ERROR_MESSAGE);
				} catch (RemoteException re) {
					re.printStackTrace();
				}
			}
			else {
				try {
					m_fileName = new File(m_fileName).toURL().toExternalForm();
				} catch (MalformedURLException e) {
					//Do nothing
				}
				ISourceInfo sourceInfo = new LocalSourceInfo(m_fileName, m_text);
				Breakpoints bpCache = BreakpointCache.getBreakpoints(m_fileName);
				if (bpCache != null) {
					int[] breakPoints = bpCache.getBreakpoints();
					if (breakPoints != null) {
						for (int line : breakPoints) {
							try {
								sourceInfo.setBreakpoint(line, true);
							} catch (RemoteException e) {
								//Do nothing
							}
						}
					}
				}
				try {
					if (!m_debugGui.updateFileWindow(sourceInfo)
							&& !m_fileName.equals("<stdin>")) {
						m_debugGui.createFileWindow(sourceInfo, -1);
					}
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
			}
			break;

		case LOAD_FILE:
			try {
				m_debugGui.m_debuggerControl.evalScript(m_fileName, m_text);
			} catch (RuntimeException ex) {
				MessageDialogWrapper.showMessageDialog(m_debugGui, ex
						.getMessage(), "Run error for " + m_fileName,
						JOptionPane.ERROR_MESSAGE);
			} catch (RemoteException re) {
				re.printStackTrace();
			}
			break;

		case UPDATE_SOURCE_TEXT:
			try {
				String fileName = m_sourceInfo.getUri();
				if (!m_debugGui.updateFileWindow(m_sourceInfo) && !fileName.equals("<stdin>")) {
					m_debugGui.createFileWindow(m_sourceInfo, -1);
				}
			} catch (RemoteException re) {
				//re.printStackTrace();
			}
			break;

		case ENTER_INTERRUPT:
			try {
					m_debugGui.enterInterruptImpl(m_lastFrame, m_threadTitle, m_alertMessage);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			break;

		default:
			throw new IllegalArgumentException(String.valueOf(m_type));

		}
	}
}

class ConsoleWrite implements Runnable {
    private ConsoleTextArea textArea;
    private String str;

    public ConsoleWrite(ConsoleTextArea textArea, String str) {
        this.textArea = textArea;
        this.str = str;
    }

    public void run() {
        textArea.write(str);
    }
};

class ConsoleWriter extends java.io.OutputStream {

    private ConsoleTextArea textArea;
    private StringBuffer buffer;

    public ConsoleWriter(ConsoleTextArea textArea) {
        this.textArea = textArea;
        buffer = new StringBuffer();
    }

    public synchronized void write(int ch) {
        buffer.append((char)ch);
        if(ch == '\n') {
            flushBuffer();
        }
    }

    public synchronized void write (char[] data, int off, int len) {
        for(int i = off; i < len; i++) {
            buffer.append(data[i]);
            if(data[i] == '\n') {
                flushBuffer();
            }
        }
    }

    public synchronized void flush() {
        if (buffer.length() > 0) {
            flushBuffer();
        }
    }

    public void close () {
        flush();
    }

    private void flushBuffer() {
        String str = buffer.toString();
        buffer.setLength(0);
        SwingUtilities.invokeLater(new ConsoleWrite(textArea, str));
    }
};

class ConsoleTextArea
    extends JTextArea implements KeyListener, DocumentListener {
    static final long serialVersionUID = 8557083244830872961L;

    private ConsoleWriter console1;
    private ConsoleWriter console2;
    private PrintStream out;
    private PrintStream err;
    private PrintWriter inPipe;
    private PipedInputStream in;
    private java.util.Vector history;
    private int historyIndex = -1;
    private int outputMark = 0;

    public void select(int start, int end) {
        requestFocus();
        super.select(start, end);
    }

    public ConsoleTextArea(String[] argv) {
        super();
        history = new java.util.Vector();
        console1 = new ConsoleWriter(this);
        console2 = new ConsoleWriter(this);
        out = new PrintStream(console1);
        err = new PrintStream(console2);
        PipedOutputStream outPipe = new PipedOutputStream();
        inPipe = new PrintWriter(outPipe);
        in = new PipedInputStream();
        try {
            outPipe.connect(in);
        } catch(IOException exc) {
            exc.printStackTrace();
        }
        getDocument().addDocumentListener(this);
        addKeyListener(this);
        setLineWrap(true);
        setFont(new Font("Monospaced", 0, 12));
    }


    synchronized void returnPressed() {
        Document doc = getDocument();
        int len = doc.getLength();
        Segment segment = new Segment();
        try {
            doc.getText(outputMark, len - outputMark, segment);
        } catch(javax.swing.text.BadLocationException ignored) {
            ignored.printStackTrace();
        }
        if(segment.count > 0) {
            history.addElement(segment.toString());
        }
        historyIndex = history.size();
        inPipe.write(segment.array, segment.offset, segment.count);
        append("\n");
        outputMark = doc.getLength();
        inPipe.write("\n");
        inPipe.flush();
        console1.flush();
    }

    public void eval(String str) {
        inPipe.write(str);
        inPipe.write("\n");
        inPipe.flush();
        console1.flush();
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_LEFT) {
            if(outputMark == getCaretPosition()) {
                e.consume();
            }
        } else if(code == KeyEvent.VK_HOME) {
           int caretPos = getCaretPosition();
           if(caretPos == outputMark) {
               e.consume();
           } else if(caretPos > outputMark) {
               if(!e.isControlDown()) {
                   if(e.isShiftDown()) {
                       moveCaretPosition(outputMark);
                   } else {
                       setCaretPosition(outputMark);
                   }
                   e.consume();
               }
           }
        } else if(code == KeyEvent.VK_ENTER) {
            returnPressed();
            e.consume();
        } else if(code == KeyEvent.VK_UP) {
            historyIndex--;
            if(historyIndex >= 0) {
                if(historyIndex >= history.size()) {
                    historyIndex = history.size() -1;
                }
                if(historyIndex >= 0) {
                    String str = (String)history.elementAt(historyIndex);
                    int len = getDocument().getLength();
                    replaceRange(str, outputMark, len);
                    int caretPos = outputMark + str.length();
                    select(caretPos, caretPos);
                } else {
                    historyIndex++;
                }
            } else {
                historyIndex++;
            }
            e.consume();
        } else if(code == KeyEvent.VK_DOWN) {
            int caretPos = outputMark;
            if(history.size() > 0) {
                historyIndex++;
                if(historyIndex < 0) {historyIndex = 0;}
                int len = getDocument().getLength();
                if(historyIndex < history.size()) {
                    String str = (String)history.elementAt(historyIndex);
                    replaceRange(str, outputMark, len);
                    caretPos = outputMark + str.length();
                } else {
                    historyIndex = history.size();
                    replaceRange("", outputMark, len);
                }
            }
            select(caretPos, caretPos);
            e.consume();
        }
    }

    public void keyTyped(KeyEvent e) {
        int keyChar = e.getKeyChar();
        if(keyChar == 0x8 /* KeyEvent.VK_BACK_SPACE */) {
            if(outputMark == getCaretPosition()) {
                e.consume();
            }
        } else if(getCaretPosition() < outputMark) {
            setCaretPosition(outputMark);
        }
    }

    public synchronized void keyReleased(KeyEvent e) {
    }

    public synchronized void write(String str) {
        insert(str, outputMark);
        int len = str.length();
        outputMark += len;
        select(outputMark, outputMark);
    }

    public synchronized void insertUpdate(DocumentEvent e) {
        int len = e.getLength();
        int off = e.getOffset();
        if(outputMark > off) {
            outputMark += len;
        }
    }

    public synchronized void removeUpdate(DocumentEvent e) {
        int len = e.getLength();
        int off = e.getOffset();
        if(outputMark > off) {
            if(outputMark >= off + len) {
                outputMark -= len;
            } else {
                outputMark = off;
            }
        }
    }

    public synchronized void postUpdateUI() {
        // this attempts to cleanup the damage done by updateComponentTreeUI
        requestFocus();
        setCaret(getCaret());
        select(outputMark, outputMark);
    }

    public synchronized void changedUpdate(DocumentEvent e) {
    }


    public InputStream getIn() {
        return in;
    }

    public PrintStream getOut() {
        return out;
    }

    public PrintStream getErr() {
        return err;
    }
};

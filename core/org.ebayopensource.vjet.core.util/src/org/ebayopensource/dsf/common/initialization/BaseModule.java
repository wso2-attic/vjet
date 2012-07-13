package org.ebayopensource.dsf.common.initialization;


public abstract class BaseModule implements ModuleInterface {

	private final InitializationManagerInterface m_initializationManager;
	private final InitializationHelper m_initHelper;
	private boolean m_isLazyInitable = true;

	public BaseModule(InitializationManagerInterface initializationManager)
	{
		if (initializationManager == null) {
			throw new NullPointerException();
		}

		m_initializationManager = initializationManager;
		m_initHelper = null;
	}

	public BaseModule(ModuleInterface[] dependentModules)
	{
		String componentName = getClass().getName() + ".InitManager";
		InitializationManager initMgr = new InitializationManager(
			dependentModules, componentName);
		m_initializationManager = initMgr;
		m_initHelper = initMgr.getInitializationHelper2();
	}

	public InitializationManagerInterface getInitializationManager() {
		return m_initializationManager;
	}

	private void checkForOwnInitManager() {
		if (m_initHelper == null) {
			throw new InitializationException(
				"To use this method, BaseModule must be constructed without " +
				"passing InitializationManagerInterface");
		}
	}

	/**
	 * This will add the initializable to the list of objects that will
	 * get call during initialize and shutdown. It will not detect duplicates
	 * 
	 * Note: this method works only if constructor with dependent modules was used
	 */
	protected final void addInitializable(Initializable initializable) {
		checkForOwnInitManager();
		m_initHelper.add(initializable);
	}

	/**
	 * Is this manager lazy initializable? By default all modules are lazy initable
	 * sub classes should override and specify false if they cannot be lazy inited
	 * 
	 * Note: this method works only if constructor with dependent modules was used
	 */
	protected final void setLazyInitable(boolean value) {
		checkForOwnInitManager();
		m_isLazyInitable = value;
	}

	private class InitializationManager
		extends BaseInitializationManager
	{
		private final String m_componentName;

		InitializationManager(ModuleInterface[] dependentModules,
			String componentName)
		{
			super(dependentModules);
			m_componentName = componentName;
		}

		public InitializationHelper getInitializationHelper2() {
			return getInitializationHelper();
		}

		public String getComponentName() {
			// make sure we do not report the same initmanager everywhere...
			return m_componentName;
		}

		public boolean isLazyInitable() {
			return m_isLazyInitable;
		}
	}
}

When working with VJET. This project must be installed into your workspace. It provide utilities to setup the 
variable java.source.path for java to js to find the source code that corresponds to the class. Without java.source.path you may see errors such as

JstType is null. 

To install in Juno
1. Select the project org.ebayopensource.vjet.vjet.javalaunch
2. Export ths project as a Deployable Plugin and Fragment
3. Select import into host (host path will automatically be filled in)
4. Restart Eclipse
5. Open preferences you will see a new preference named Java Source Path
	You can then check the param to add java.source.path to the VM arguments.
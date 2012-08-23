/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Mozilla Communicator client code, released
 * March 31, 1998.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1998
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */


vjo.ctype('partials.ArrayPartial')
.protos({
		    /** @Test 
		 
		    File Name:          15.4-1.js
		    ECMA Section:       15.4 Array Objects
		 
		    Description:        Every Array object has a length property whose value
		    is always an integer with positive sign and less than
		    Math.pow(2,32).
		 
		    Author:             christine@netscape.com
		    Date:               28 october 1997
		 
		*/
		    //> public void test1();
		    test1:function(){
	        	var myarr = new Array(); 
	        	myarr[Math.pow(2,32)-2] = "hi";
	        	assertTrue(myarr[Math.pow(2,32)-2] == "hi");
	        	
	        	var myarr2 = new Array();
	        	myarr2[Math.pow(2,32)-2]='hi'; 
	        	assertTrue(myarr2.length);
		        	
				var myarr3 = new Array();
				myarr3[Math.pow(2,32)-3]='hi';
				assertTrue(myarr3[Math.pow(2,32)-3]);
			
             	var myarr4 = new Array(); 
             	myarr4[Math.pow(2,32)-3]='hi'; 
             	assertTrue(myarr4.length==(Math.pow(2,32)-2));
             	
             	var myarr5 = new Array(); 
             	myarr5[Math.pow(2,31)-2]='hi'; 
             	assertTrue(myarr5[Math.pow(2,31)-2]=="hi");
             	
             	var myarr6 = new Array(); 
             	myarr6[Math.pow(2,31)-2]='hi';             
             	assertTrue((Math.pow(2,31)-1)==myarr6.length);
             	
             	var myarr7 = new Array(); 
             	myarr7[Math.pow(2,31)-1]='hi'; 
             	assertTrue(myarr7[Math.pow(2,31)-1]=="hi");
		             	
				 var myarr8 = new Array(); 
				 myarr8[Math.pow(2,31)-1]='hi'; 
				 assertTrue(myarr8.length==(Math.pow(2,31)))	
				 
				 var myarr9 = new Array();
				 myarr9[Math.pow(2,31)]='hi';
				 assertTrue(myarr9[Math.pow(2,31)] == "hi");
		             
				var myarr10 = new Array(); 
				myarr10[Math.pow(2,31)]='hi'; 
				assertTrue(myarr10.length==(Math.pow(2,31)+1));
				
				var myarr11 = new Array();
				myarr11[Math.pow(2,30)-2]='hi';
				assertTrue(myarr11[Math.pow(2,30)-2]=="hi");
				
				var myarr12 = new Array();
				myarr12[Math.pow(2,30)-2]='hi'; 
				assertTrue(myarr12.length==(Math.pow(2,30)-1));
		
		},
		
		/**
		   File Name:          15.4-2.js
		   ECMA Section:       15.4 Array Objects
		
		   Description:        Whenever a property is added whose name is an array
		   index, the length property is changed, if necessary,
		   to be one more than the numeric value of that array
		   index; and whenever the length property is changed,
		   every property whose name is an array index whose value
		   is not smaller  than the new length is automatically
		   deleted.  This constraint applies only to the Array
		   object itself, and is unaffected by length or array
		   index properties that may be inherited from its
		   prototype.
		
		   Author:             christine@netscape.com
		   Date:               28 october 1997
		
		*/
		
		
		test15_4_2:function(){
		
				var arr=new Array(); //< Array
				arr[Math.pow(2,16)] = 'hi';
				    arr.
					
				new TestCase( SECTION,
				              "var arr=new Array();  arr[Math.pow(2,16)] = 'hi'; arr.length",     
				              Math.pow(2,16)+1,  
				              eval("var arr=new Array();  arr[Math.pow(2,16)] = 'hi'; arr.length") );
				
				new TestCase( SECTION,
				              "var arr=new Array();  arr[Math.pow(2,30)-2] = 'hi'; arr.length",   
				              Math.pow(2,30)-1,  
				              eval("var arr=new Array();  arr[Math.pow(2,30)-2] = 'hi'; arr.length") );
				
				new TestCase( SECTION,
				              "var arr=new Array();  arr[Math.pow(2,30)-1] = 'hi'; arr.length",   
				              Math.pow(2,30),    
				              eval("var arr=new Array();  arr[Math.pow(2,30)-1] = 'hi'; arr.length") );
				
				new TestCase( SECTION,
				              "var arr=new Array();  arr[Math.pow(2,30)] = 'hi'; arr.length",     
				              Math.pow(2,30)+1,  
				              eval("var arr=new Array();  arr[Math.pow(2,30)] = 'hi'; arr.length") );
				
				
				new TestCase( SECTION,
				              "var arr=new Array();  arr[Math.pow(2,31)-2] = 'hi'; arr.length",   
				              Math.pow(2,31)-1,  
				              eval("var arr=new Array();  arr[Math.pow(2,31)-2] = 'hi'; arr.length") );
				
				new TestCase( SECTION,
				              "var arr=new Array();  arr[Math.pow(2,31)-1] = 'hi'; arr.length",   
				              Math.pow(2,31),    
				              eval("var arr=new Array();  arr[Math.pow(2,31)-1] = 'hi'; arr.length") );
				
				new TestCase( SECTION,
				              "var arr=new Array();  arr[Math.pow(2,31)] = 'hi'; arr.length",     
				              Math.pow(2,31)+1,  
				              eval("var arr=new Array();  arr[Math.pow(2,31)] = 'hi'; arr.length") );
				
				new TestCase( SECTION,
				              "var arr = new Array(0,1,2,3,4,5); arr.length = 2; String(arr)",    
				              "0,1",             
				              eval("var arr = new Array(0,1,2,3,4,5); arr.length = 2; String(arr)") );
				
				new TestCase( SECTION,
				              "var arr = new Array(0,1); arr.length = 3; String(arr)",            
				              "0,1,",            
				              eval("var arr = new Array(0,1); arr.length = 3; String(arr)") );
				
				test();
		
		}
    
})
.endType();
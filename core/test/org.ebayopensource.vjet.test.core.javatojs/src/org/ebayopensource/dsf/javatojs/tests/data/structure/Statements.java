/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import java.util.ArrayList;
import java.util.List;

public class Statements {

	public void forLoop(){
		
		int j, p=5;
		int total = 10;
		
		for (int i=0; i<total+5; i++){
			total += i*10;
			for (int k=0; k<5; k++){
				total += (i+k)*10 + k;
			}
		}
		
		for(int m=0, n=0; m<100; --m, n--) { 
			n++; 
		}
		
		for(p += 2; p < 10; p++){}
		
		for(j=0;;) { 
			j++; 
			if (j >= total){
				break ; 
			}
			continue;
		}
		
		for(;j<10;){}
		
		for(;j<10;) if (j>0) j++;
		
		for(;j<10; j+=2) {};

		for(;;) {}
	}
	
	public void forInIterable(){
		List<String> list = getList();
		for (String ele: list){
			ele.length();
		}
		for (String ele: list) if (ele == null) break;
	}
	
	void forInIterableB(){
		for (String ele: getList()){
			ele.length();
		}
		for (String ele: getList()) if (ele == null) break;
	}
	
	private List<String> getList() {
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		return list;
	}
	
	void forInArrayA() {
		String[] elements = getArray();
		for (String ele: elements){
			ele.length();
		}
	}
	
	void forInArrayB() {
		for (String ele: getArray()){
			ele.length();
		}
	}
	
	private String[] getArray() {
		return new String[] {"A", "B"};
	}
	
	public void whileLoop(){
		
		int total = 10;
		
		while (total > 0){
			total--;
			if (total < 5){
				break;
			}
			else {
				continue;
			}
		}
		
		while (total > 0) --total;
	}
	
	public void doWhile(){
		int i=3;
		do {
			i--;
		}while (i> 0);
	}
	
	public int ifElse(int total){
		
		if (total > 10){
			return 1;
		}
		else if (total > 100){
			return 2;
		}
		else {
			return 3;
		}
	}
	
	public int ifElse2(int total){
		
		if (total > 10) return 1;
		else if (total > 100)return 2;
		else return 3;
	}
	
	public String switchStmt(int id){
		
		assert (id > 0) : "id must > 0";
		
		String msg = "Msg for ";
		int count = 0;
	
		switch (id * 10){
			case 1:
				count++;
				msg = msg + id;
				break;
			case 2:
			case 3:
			case 4 + 3:{
				count++;
				return msg = msg + id*10;
			}
			default:
				switch (id){
					case 2:
					case 3:
					case 4 + 3:
						count++;
						return msg = msg + id*10;
					default:
				}
				count++;
				return "None";	
		}
		
		return msg;
	}
	
	public void label(){
		outer:
	    for(int i=0; i<10; i++ ){
	        for(int j=10; j>0; j--){
	            if( j == 5 ) {
	             break outer;       // exit entire loop
	            } 
	        }            
	    }
	
		outer:
	    for(int i=0; i<10; i++ ){
	        for(int j=10; j>0; j--) {
	            if( j== 5 ) {
	              continue outer;   // next iteration of i
	            }  
	        }
	    }
	}
}
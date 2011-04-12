/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package bugs.b3194;
public class InnerType {

        public String she = "she";
        private double four = 4.0;
        Foo foo = new Foo();

        public class Foo {
                public int booom = 15;

                public void changeFour(double f){
                        four = f;
                }
        }
}

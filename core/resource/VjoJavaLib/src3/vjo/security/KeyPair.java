package vjo.security;

import java.io.Serializable ;

import vjo.lang.* ;

/*
 * @(#)src/classes/sov/java/security/KeyPair.java, security, asdev, 20070119 1.12
 * ===========================================================================
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 *
 * IBM SDK, Java(tm) 2 Technology Edition, v5.0
 * (C) Copyright IBM Corp. 1998, 2005. All Rights Reserved
 * ===========================================================================
 */

/*
 * ===========================================================================
 (C) Copyright Sun Microsystems Inc, 1992, 2004. All rights reserved.
 * ===========================================================================
 */


 
/*
 * ===========================================================================
 * Change activity:
 *
 * Reason  Date   Origin   Description
 * ------  ----   ------   ---------------------------------------------------- 
 * 76914   141004 eldergil Merge in Sun's 1.5.0 security changes
 *
 * ===========================================================================
 */


/**
 * This class is a simple holder for a key pair (a public key and a
 * private key). It does not enforce any security, and, when initialized,
 * should be treated like a PrivateKey.
 *
 * @see PublicKey
 * @see PrivateKey
 *
 * @version 1.12 01/12/03
 * @author Benjamin Renaud
 */

public final class KeyPair implements Serializable {

    private static final long serialVersionUID = -7565189502268009837L;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * Constructs a key pair from the given public key and private key.
     *
     * <p>Note that this constructor only stores references to the public
     * and private key components in the generated key pair. This is safe,
     * because <code>Key</code> objects are immutable.
     *
     * @param publicKey the public key.
     *
     * @param privateKey the private key.
     */
    public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Returns a reference to the public key component of this key pair.
     *
     * @return a reference to the public key.
     */
    public PublicKey getPublic() {
        return publicKey;
    }

     /**
     * Returns a reference to the private key component of this key pair.
     *
     * @return a reference to the private key.
     */
   public PrivateKey getPrivate() {
        return privateKey;
    }
}


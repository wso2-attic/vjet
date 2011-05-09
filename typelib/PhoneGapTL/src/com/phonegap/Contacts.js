vjo.ctype('com.phonegap.Contacts') //< public
.globals({
	contacts : null, //< com.phonegap.Contacts
	ContactFindOptions : null, // type::Contacts.ContactFindOptions
	ContactName : null //< type::Contacts.ContactName
})
.protos({
	
	//>public Contacts.Contact create(com.phonegap.Contacts.objects::contact? properties) 
	create : vjo.NEEDS_IMPL,
	
	//>public void find(String[], (void fn(Contacts.objects::contact[] contacts)) onSuccess, (void fn()) onError, Contacts.objects::contactFindOptions contactFindOptions) 
	find : vjo.NEEDS_IMPL,
	
	Contact : vjo.ctype()
		.protos({	
			id:null, //< public String
			displayName : null, //< public String
			name : null, //< public Contacts.ContactName
			nickname: null, //< public String
			phoneNumbers : null, //< public Contacts.ContactField[]
			emails : null, //< public Contacts.ContactField[]
			addresses : null, //< public Contacts.ContactAddress[]
			ims : null, //< public Contacts.ContactField[]
			organizations : null, //< public Contacts.ContactOrganization[]
			revision : null, //< public String
			birthday : null, //< public Date
			gender : null, //< public String
			note : null, //< public String
			photos : null, //< public Contacts.ContactField[]
			categories : null, //< public Contacts.ContactField[]
			urls : null, //< public Contacts.ContactField[]
			timezone : null, //< public String
			
			//>public com.phonegap.Contacts.Contact clone() 
			clone : vjo.NEEDS_IMPL,
		
			//>public void save((void fn(com.phonegap.Contacts.objects::contact[] contacts)) onSuccess, Function onError) 
			save : vjo.NEEDS_IMPL,
			
			//>public void remove( (void fn()) onSuccess, (void fn(Contacts.objects::contactError contactError)) onError) 
			remove : vjo.NEEDS_IMPL
		
		})
		.endType(),
	
	
	objects:vjo.otype()
	.defs({
		
		contact: 	{/*
			 * id: A globally unique identifier. (DOMString)
		displayname: The name of this Contact, suitable for display to end-users. (DOMString)
		name: An object containing all components of a persons name. (ContactName)
		nickname: A casual name to address the contact by. (DOMString)
		phoneNumbers: An array of all the contact's phone numbers. (ContactField[])
		emails: An array of all the contact's email addresses. (ContactField[])
		addresses: An array of all the contact's addresses. (ContactAddresses[])
		ims: An array of all the contact's IM addresses. (ContactField[])
		organizations: An array of all the contact's organizations. (ContactOrganization[])
		revision: The last date the contact was revised. (DOMString)
		birthday: The birthday of the contact. (Date)
		gender: The gender of the contact. (DOMString)
		note: A note about the contact. (DOMString)
		photos: An array of the contact's photos. (ContactField[])
		categories: An array of all the contacts user defined categories. (ContactField[])
		urls: An array of web pages associated to the contact. (ContactField[])
		timezone: The timezone of the conact. (DOMString)
				 */
				id:null, //< public String?
				displayName : null, //< public String?
				name : null, //< public Contacts.ContactName?
				nickname: null, //< public String?
				phoneNumbers : null, //< public Contacts.ContactField[]?
				emails : null, //< public Contacts.ContactField[]?
				addresses : null, //< public Contacts.ContactAddress[]?
				ims : null, //< public Contacts.ContactField[]?
				organizations : null, //< public Contacts.ContactOrganization[]?
				revision : null, //< public String?
				birthday : null, //< public Date?
				gender : null, //< public String?
				note : null, //< public String?
				photos : null, //< public Contacts.ContactField[]?
				categories : null, //< public Contacts.ContactField[]?
				urls : null, //< public Contacts.ContactField[]?
				timezone : null //< public String?
				
		
		},
		
	contactError:{
		code:null //< public Contacts.ContactError
		},
	contactFindOptions:{
		filter : null, //< public String?
		multiple : null, //< public boolean?
		updatedSince : null //< public String?
	}
	})
	.endType(),
	
	
			
			/*
			 *  ContactError.UNKNOWN_ERROR
				ContactError.INVALID_ARGUMENT_ERROR
				ContactError.NOT_FOUND_ERROR
				ContactError.TIMEOUT_ERROR
				ContactError.PENDING_OPERATION_ERROR
				ContactError.IO_ERROR
				ContactError.NOT_SUPPORTED_ERROR
				ContactError.PERMISSION_DENIED_ERROR
			 */
	//>public
	ContactError : vjo.etype()
	.values("UNKNOWN_ERROR, INVALID_ARGUMENT_ERROR, NOT_FOUND_ERROR, TIMEOUT_ERROR,PENDING_OPERATION_ERROR, IO_ERROR, NOT_SUPPORTED_ERROR,PERMISSION_DENIED_ERROR")
	.endType(),
				
	
	ContactField:vjo.ctype()
	.protos({
		//>public constructs(String type, String value, boolean pref)
		constructs : vjo.NEEDS_IMPL,
		
		type : null, //< public String
		value : null, //< public String
		pref : null //< public boolean
	})
	.endType(),
	
	ContactAddress:vjo.ctype()
	.protos({
		formatted : null, //< public String
		streetAddress : null, //< public String
		locality : null, //< public String
		region : null, //< public String
		postalCode : null, //< public String
		country : null //< public String
		
	})
	.endType(),
	
	ContactFindOptions : vjo.ctype()
	.protos({
		//>public constructs()
		constructs :  vjo.NEEDS_IMPL,
		filter : null, //< public String?
		multiple : null, //< public boolean?
		updatedSince : null //< public String?
	}).endType(),
	
	/**
	 * The ContactName object stores name properties of a contact.
	 */
		//> public
	ContactName:vjo.ctype()
	.protos({
		//>public constructs()
		constructs : vjo.NEEDS_IMPL,
		formatted : null, //< public String
		familyName : null, //< public String
		givenName : null, //< public String
		middleName : null, //< public String
		honorificPrefix : null, //< public String
		honorificSuffix : null //< public String
	})
	.endType(),
	
	ContactOrganization:vjo.ctype()
	.protos({
		name : null, //< public String
		department : null, //< public String
		title : null //< public String
	})
	.endType()
	
	
	
})
.options({metatype: true})
.endType();
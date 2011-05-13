var contacts = navigator.service.contacts;

var contact = contacts.create({displayName: "justin"});

function onSuccess(contacts) {
    alert("Save Success");
};

function onError(contactError) {
    alert("Error = " + contactError.code);
};

// create a new contact object
var contact2 = navigator.service.contacts.create(); //<<
contact.displayName = "Plumber";
contact.nickname = "Plumber";       //specify both to support all devices

// populate some fields
var name = new ContactName();
name.givenName = "Jane";
name.familyName = "Doe";
contact.name = name;

// save to device
contact.save(onSuccess,onError);

var myContact = navigator.service.contacts.create({"displayName": "Test User" }); //<<

 // Wait for PhoneGap to load
    //
    function onLoad() {
        document.addEventListener("deviceready", onDeviceReady, false);
    }

    // PhoneGap is ready
    //
    function onDeviceReady() {
        var myContact = navigator.service.contacts.create({"displayName": "Test User"});
        myContact.gender = "male";
        console.log("The contact, " + myContact.displayName + ", is of the " + myContact.gender + " gender");
    }

    
    function onSuccess(contacts) {
    alert('Found ' + contacts.length + ' contacts.');
};


function onError() {
    alert('onError!');
};

// find all contacts with 'Bob' in any name field
var options = new ContactFindOptions();
options.filter="Bob"; 
var fields = ["displayName", "name"];
contacts.find(fields, function(contacts){
	
	for (var i=0; i<contacts.length; i++) {
        alert("Formatted: " + contacts[i].name.formatted + "\n" + 
                "Family Name: "  + contacts[i].name.familyName + "\n" + 
                "Given Name: "  + contacts[i].name.givenName + "\n" + 
                "Middle Name: "  + contacts[i].name.middleName + "\n" + 
                "Suffix: "  + contacts[i].name.honorificSuffix + "\n" + 
                "Prefix: "  + contacts[i].name.honorificSuffix);
    }
}, function(contactError){
	
}, {});

   // Wait for PhoneGap to load
    //
    function onLoad() {
        document.addEventListener("deviceready", onDeviceReady, false);
    }

    // PhoneGap is ready
    //
    function onDeviceReady() {
        // find all contacts with 'Bob' in any name field
        var options = new ContactFindOptions();
        options.filter="Bob"; 
        var fields = ["displayName", "name"];
        contacts.find(fields, onSuccess, onError, options);
    }

    // onSuccess: Get a snapshot of the current contacts
    //
    function onSuccess(contacts) {
        for (var i=0; i<contacts.length; i++) {
            console.log("Display Name = " + contacts[i].displayName);
        }
    }
    

    // onError: Failed to get the contacts
    //
    function onError() {
        alert('onError!');
    }
    
    function onSuccess(contacts) {
    alert("Save Success");
};

function onError(contactError) {
    alert("Error = " + contactError.code);
};

// create a new contact object
var contact = navigator.service.contacts.create();
contact.displayName = "Plumber";
contact.nickname = "Plumber";       //specify both to support all devices

// populate some fields
var name = new ContactName();
name.givenName = "Jane";
name.familyName = "Doe";
contact.name = name;

// save to device
contact.save(onSuccess,onError);

function onSuccess() {
    alert("Removal Success");
};

function onError(contactError) {
    alert("Error = " + contactError.code);
};


// remove the contact from the device
contact.remove(onSuccess,onError);

   // Wait for PhoneGap to load
    //
    function onLoad() {
        document.addEventListener("deviceready", onDeviceReady, false);
    }

    // PhoneGap is ready
    //
    function onDeviceReady() {
        // create
        var contact = navigator.service.contacts.create();//<<
        contact.displayName = "Plumber";
        contact.nickname = "Plumber";       //specify both to support all devices
        var name = new ContactName();
        name.givenName = "Jane";
        name.familyName = "Doe";
        contact.name = name;

        // save
        contact.save(onSaveSuccess,onSaveError);

        // clone
        var clone = contact.clone();//<<
        clone.name.givenName = "John";
        console.log("Original contact name = " + contact.name.givenName);
        console.log("Cloned contact name = " + clone.name.givenName); 

        // remove
        contact.remove(onRemoveSuccess,onRemoveError);
    }

    // onSaveSuccess: Get a snapshot of the current contacts
    //
    function onSaveSuccess(contacts) {
        alert("Save Success");
    }

    // onSaveError: Failed to get the contacts
    //
    function onSaveError(contactError) {
        alert("Error = " + contactError.code);
    }

    // onRemoveSuccess: Get a snapshot of the current contacts
    //
    function onRemoveSuccess(contacts) {
        alert("Removal Success");
    }

    // onRemoveError: Failed to get the contacts
    //
    function onRemoveError(contactError) {
        alert("Error = " + contactError.code);
    }
    
    
	
	
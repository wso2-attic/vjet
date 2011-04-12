vjo.ctype("vjoPro.example.Person")
.protos({
/**
* @JsBeanName Person
* @return void
* @access protected
*/
//>protected constructs()
constructs: function(pAge,pFirstName,pLastName){
this.age = pAge;
this.firstName = pFirstName;
this.lastName = pLastName;

}

})
.endType();

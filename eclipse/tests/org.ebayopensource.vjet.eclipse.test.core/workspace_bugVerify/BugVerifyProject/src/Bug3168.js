vjo.ctype('Bug3168') //< public

.props({
})
.protos({
	open : true, //< private boolean
	
	//>public boolean isOpen() 
	isOpen : function(){
		return this.open;
	},
	
	//>public void setOpen(boolean open) 
	setOpen : function(open){
		this.open = open;
	}
})
.endType();
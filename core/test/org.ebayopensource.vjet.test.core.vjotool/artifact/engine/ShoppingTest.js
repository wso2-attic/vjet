<<72>>vjo.<<73>>ctype('<<74>>engine.<<75>>ShoppingTest') //< public
.<<76>>needs([<<77>>'vjo.java.lang.System','en<<78>>gine.ShoppingItem',
    'engine.ShoppingCategory'])
.<<79>>inherits(<<80>>'engine.<<81>>GenericShopping')
.<<82>>satisfies('<<83>>engine.<<84>>IShopping')
.<<85>>props({
    sellerName:"VJET", //< public String
    shop:null, //< private Shopping
    //> public Shopping getInstance()
    get<<86>>Instance:function(){
        <<87>>if<<88>>(this.<<89>>shop===null){
            <<90>>this.<<91>>shop=<<92>>new <<93>>this();
        }
        retu<<94>>rn this.<<95>>shop;
    },
    //> public void main(String[] args)
    main<<96>>:function(args){
        var<<97>> sh=this.get<<98>>Instance();
        this.<<99>>vj$.<<100>>Sys<<101>>tem.<<102>>out.<<103>>println(<<104>>this.sellerName);
        this.vj$.<<105>>System.err.<<106>>println(sh.<<107>>getTotalItems());
    }
})
.<<108>>protos({
    //> private constructs()
    con<<109>>structs:function(){
        this.<<110>>base();
        this.populateShoppingLists();
    },
    //> public boolean buy(ShoppingItem buyItem)
    buy:function(buyItem){
        this.buyOnEbay(<<111>>buyItem);
        return true;
    },
    //> public boolean sell(ShoppingItem sellItem)
    sell:function(sellItem){
        this.<<112>>sellOnEbay(sellItem);
        return true;
    },
    //> public int getTotalItems()
    getTotalItems:function(){
        var electronicsItems=this.<<113>>getElectronicsList().<<114>>size(); //<int
        var motorItems=this.getMotorList().size();//<int
        return (<<115>>electronicsItems+<<116>>motorItems);
    },
    //> private void populateShoppingLists()
    populateShoppingLists:function(){
        this.populateShoppingItem("iPhone","Stylish CellPhone",300,this.vj$.ShoppingCategory.<<117>>ELECTRONICS);
        this.populateShoppingItem("PS3","Stylish Gaming Console",350,this.vj$.ShoppingCategory.ELECTRONICS);
        this.populateShoppingItem("BMW","Stylish Car",50000,this.vj$.ShoppingCategory.MOTORS);
        this.populateShoppingItem("Audi","Stylish Car",70000,this.vj$.ShoppingCategory.MOTORS);
    },
    //> private void populateShoppingItem(String name,String desc,int price,int category)
    populateShoppingItem:function(name,desc,price,category){
        var item=new this.vj$.ShoppingItem(name,desc,price,category);
        if(category===this.vj$.ShoppingCategory.<<118>>ELECTRONICS){
            this.addElectronicsList(item);
        }
        if(category===this.vj$.ShoppingCategory.MOTORS){
            this.addMotorList(<<119>>item);
        }
    }
})
.<<120>>endType()<<121>>;
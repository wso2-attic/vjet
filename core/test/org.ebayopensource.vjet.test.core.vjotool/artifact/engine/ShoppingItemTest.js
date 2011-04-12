vjo.ctype('engine.<<18>>ShoppingItemTest') //< public
.<<19>>protos({
    itemName:null, //< private String
    itemCategory:0, //< private int
    <<20>>itemDescription:null, //< private String
    itemPrice:0, //< private int
    //> public constructs(String name,String desc,int price,int category)
    c<<21>>onstructs<<22>>:<<23>>function(name,desc,price,category){
        <<24>>this.<<25>>itemName=<<26>>name;<<27>>
        this.<<28>>itemDescription=<<29>>desc;
        this.itemPrice=price;
        this.itemCategory=category;
    },
    //> public String getItemName()
    get<<30>>ItemName:function(){
        ret<<31>>urn <<32>>this.<<33>>itemName;
    },
    //> public void setItemName(String itemName)
    setItemName:function(itemName){
        <<34>>this.<<35>>itemName=<<36>>itemName;
    },
    //> public int getItemCategory()
    getItemCategory:function(){
        return this.itemCategory;
    },
    //> public void setItemCategory(int itemCategory)
    setItemCategory:function(itemCategory){
        this.itemCategory=itemCategory;
    },
    //> public String getItemDescription()
    getItemDescription:function(){
        return this.itemDescription;
    },
    //> public void setItemDescription(String itemDescription)
    setItemDescription:function(itemDescription){
        this.itemDescription=itemDescription;
    },
    //> public int getItemPrice()
    getItemPrice:function(){
        return this.itemPrice;
    },
    //> public void setItemPrice(int itemPrice)
    setItemPrice:function(itemPrice){
        this.itemPrice=itemPrice;
    }
})
.<<37>>endType();
vjo.ctype('<<38>>engine.GenericShoppingTest') //< public abstract
.<<39>>needs([<<40>>'<<41>>vjo.<<42>>java.<<43>>util.<<44>>List',<<45>>'vjo.java.util.ArrayList',
    'engine.ShoppingItem','engine.<<46>>ShoppingCategory'])
.<<47>>protos({
    electronicsList:null, //< private List<ShoppingItem> electronicsList
    motorList:null, //< private List<ShoppingItem> motorList
    //> private constructs()
    constructs<<48>>:function(){
        this.<<49>>electronicsList=new <<50>>this.<<51>>vj$.<<52>>ArrayList();
        this.motorList=new this.vj$.ArrayList();
    },
    //> public boolean sellOnEbay(ShoppingItem sellItem)
    sellOnEbay:function(sellItem){
        va<<53>>r category=<<54>>sellIte<<55>>m.<<56>>getItemCategory();
        i<<57>>f<<58>>(<<59>>category===<<60>>this.<<61>>vj$.<<62>>ShoppingCategory.<<63>>ELECTRONICS){
            <<64>>this.<<65>>electronicsList.<<66>>remove(<<67>>sellItem);
        }
        if(category===this.vj$.ShoppingCategory.MOTORS){
            this.motorList.remove(<<68>>sellItem);
        }
        re<<69>>turn true;
    },
    //> public boolean buyOnEbay(ShoppingItem buyItem)
    buyOnEbay:function(buyItem){
        var category=buyItem.getItemCategory();
        if(category===this.vj$.ShoppingCategory.ELECTRONICS){
            this.electronicsList.add(buyItem);
        }
        if(category===this.vj$.ShoppingCategory.MOTORS){
            this.motorList.add(buyItem);
        }
        return true;
    },
    //> public List<ShoppingItem> getElectronicsList()
    getElectronicsList:function(<<70>>){
        return this.<<71>>electronicsList;
    },
    //> public void addElectronicsList(ShoppingItem item)
    addElectronicsList:function(item){
        this.electronicsList.add(item);
    },
    //> public List<ShoppingItem> getMotorList()
    getMotorList:function(){
        return this.motorList;
    },
    //> public void addMotorList(ShoppingItem item)
    addMotorList:function(item){
        this.motorList.add(item);
    }
})
.endType();
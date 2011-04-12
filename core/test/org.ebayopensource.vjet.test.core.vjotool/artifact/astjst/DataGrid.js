/* wiki link : http://wiki.arch.ebay.com/index.php?page=V4_Yoda_VJO_UPREV */ 
vjo.ctype('astjst.DataGrid') //< public
.needs(['vjo.dsf.Element','com.ebay.darwin.app.blueprint.component.datagrid.DataGridMsg'])
.props({
	/**
	 * @JsReturnType void
	 * @JsJavaAccessToJs public
	 */
    //> public void staticFunc()
	staticFunc:function(message){
	}
})
.protos({

/**
 * @JsReturnType void
 * @JsJavaAccessToJs public
 * @JsParamType poModel com.ebay.darwin.app.blueprint.component.datagrid.DataGridJsModel 
 */
    //> public constructs(com.ebay.darwin.app.blueprint.component.datagrid.DataGridJsModel poModel)
constructs : function (poModel) {
  this.dataGridJsId = poModel.cmpJsId;
  this.dataGridId = poModel.tableId;
  this.isEditAction = poModel.editActionEnable;
  this.isDeleteAction = poModel.deleteActionEnable;
  this.isSelectAction = poModel.selectActionEnable;
  this.deleteActionSvcId = poModel.deleteActionSvcId;
  this.selectActionSvcId = poModel.selectActionSvcId;
  this.editActionServiceId = poModel.editActionSvcId;
  this.emptyTableMsg = poModel.emptyTableMsg;
  this.emptyTableRowId  = poModel.emptyTableRowId;
  this.numCols = poModel.numCols;
  this.dataGridObj = document.getElementById(this.dataGridId);
  this.emptyTableMsgCss =  poModel.emptyTableMsgCss;
  this.selectedRowCss = poModel.selectedRowCss;
  this.unselectedRowCss = poModel.unselectedRowCss;
  this.count = 0;
  var emptyTableRowObj = document.getElementById(this.emptyTableRowId);			
  if(emptyTableRowObj != null){ 
  	this.emptyTable = true;
  }
 },

getId : function () {
	    /* Returns unique id each time */
		return (this.dataGridJsId + this.count++);		
},
 /**
 * @JsReturnType void
 * @JsJavaAccessToJs public
 * @JsParamType poMsg com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
 */
		
    //> public void addRow(com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum poMsg)
addRow : function(poMsg) {
		//alert("DataGrid Component=>Listened event:'addRow' | Handler(JS API):'addRow'| Received publisher data:",poMsg.sRowId,",", poMsg.rowData[0],",",poMsg.rowData[1],"," ,poMsg.rowData[2] ,",",poMsg.rowData[3],",",poMsg.rowData[4]);
 		
		//If this is a empty table, remove the msg row first 
		if(this.emptyTable){
			var emptyTableRowObj = document.getElementById(this.emptyTableRowId);			
			if(emptyTableRowObj != null){
				this.dataGridObj.deleteRow(emptyTableRowObj.rowIndex);  
				this.emptyTable = false;
			}
		}
		var row = document.createElement('tr');
    	var tnode= this.dataGridObj.getElementsByTagName('tbody');
		tnode[0].appendChild(row); 
    	row.id = poMsg.sRowId;
    	row.className = this.unselectedRowCss;
        
        for (var i = 0; i < poMsg.rowData.length; i++){	
        	var cell = row.insertCell(i);
			cell.appendChild(document.createTextNode(poMsg.rowData[i]));
		}

		if(this.isEditAction || this.isDeleteAction || this.isSelectAction){
		    var actionCell = row.insertCell(i);
			var actionDiv = document.createElement('div');
			var _this = this;
			actionCell.appendChild(actionDiv);
			if(this.isEditAction){
				
				var editAnchor = this.createElem("a",
								  {	"href":"#",
								  	"id": "editAnch_" + this.getId()
								  });
				var editImage = this.createElem("img",
				            {
				             "src":"http://pics.ebaystatic.com/aw/pics/icons/iconEditFolder.gif",// FIXME: pass URL using Js Model
							 "alt":"edit",
						     "width":"16",
						     "height":"16",
						     "border":"0"
						     });
		        editAnchor.appendChild(editImage);
		        actionDiv.appendChild(editAnchor);
				//addEventListener : function(elem,type,listener,scope,capture)
				vjo.dsf.EventDispatcher.add(editAnchor.id, 'click', function(event) { return _this.editAction(event,poMsg.sRowId);});
			}			
		    
		    if(this.isDeleteAction){
			    var deleteAnchor = this. createElem('a',
			    					{ 'href':'#',
			    					  'id': 'deleteAnch_' + this.getId()
			    					});
				
				var deleteImage = this.createElem('img',
									{	'src': 'http://pics.ebaystatic.com/aw/pics/icons/iconDeleteFolder.gif', // FIXME: pass URL using Js Model
										'alt': 'delete',
										'width':'16',
										'height':'16',
										'border':'0'
									});
		        deleteAnchor.appendChild(deleteImage);
		        actionDiv.appendChild(deleteAnchor);
				vjo.dsf.EventDispatcher.add(deleteAnchor.id, 'click', function(event) { return _this.deleteAction(event,poMsg.sRowId);});		
		    }
		    if(this.isSelectAction){
			    var selectBox = document.createElement('input');
				selectBox.type = "checkbox";
				selectBox.id = "selectBox_" + this.getId();
		        actionDiv.appendChild(selectBox);
		        if(poMsg.selectionValue == "selected"){
					selectBox.checked = "checked"; //must set after adding selectBox in document  (IE)
					row.className = this.selectedRowCss;
				}
			    vjo.dsf.EventDispatcher.add(selectBox.id, 'click', function(event) { return _this.selectAction(event,selectBox.id,poMsg.sRowId);});		
		    }
		}		
}		
,
createElem : function (type, attrs) {
	var elem = document.createElement(type);
	for (var i in attrs) {
		elem[i] = attrs[i];
	}
	return elem;
},
/**
 * @JsReturnType void
 * @JsJavaAccessToJs public
 * @JsParamType poMsg com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
 */
		
    //> public void editRow(com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum poMsg)
editRow : function(poMsg) {
		//console.log("DataTable Component=>Listened event:'editRow' | Handler(JS API):'editRow'| Received publisher data:",poMsg.sRowId,",", poMsg.rowData[0],",",poMsg.rowData[1],"," ,poMsg.rowData[2] ,",",poMsg.rowData[3],",",poMsg.rowData[4]);
		var row = document.getElementById(poMsg.sRowId);
		var cells = row.getElementsByTagName("td");		 
		
		for (var i = 0; i < cells.length; i++)
		{
			textData = cells[i].firstChild;
			if ( (textData.nodeType == 3) && (textData.nodeValue != '\n'))
			{// textData.nodeType ==  Node.TEXT_NODE - not happy with IE
				textData.nodeValue = poMsg.rowData[i];
			}
		}		
	}		
,

/**
 * @JsReturnType void
 * @JsJavaAccessToJs public
 * @JsParamType poMsg com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
 */
    //> public void deleteRow(com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum poMsg)
deleteRow:function(poMsg)
{	
	var rowObj = document.getElementById(poMsg.sRowId);
	this.dataGridObj.deleteRow(rowObj.rowIndex);
	
	if(!this.emptyTable){
		var rows = this.dataGridObj.getElementsByTagName("tr");		 
		if(rows.length ==1){ //Table is empty now, has only Header Row
			var row = document.createElement('tr');
    		var tnode= this.dataGridObj.getElementsByTagName('tbody');
			tnode[0].appendChild(row); 
    		row.id = this.emptyTableRowId;
    		row.className = this.emptyTableMsgCss;
        	var cell = row.insertCell(0);
			cell.appendChild(document.createTextNode(this.emptyTableMsg));
			cell.colSpan = this.numCols; 
			
			this.emptyTable = true;
		}
	}
		
		
},

//snippet.publishaction.begin
/**
* @JsJavaAccessToJs public
* @JsReturnType void
* @JsParamType poEvt com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
* @JsParamType rowId String
*/
    //> public void editAction(com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum poEvt,String rowId)
editAction:function(poEvt,rowId){ 
	var msg = new com.ebay.darwin.app.blueprint.component.datagrid.DataGridMsg(this.editActionServiceId);
	msg.sRowId = rowId;
	var row = document.getElementById(rowId);
	var cells = row.getElementsByTagName("td");
	var rowData = new Array();
	for (var i = 0; i < cells.length; i++){
		textData = cells[i].firstChild;
		if ( (textData.nodeType == 3) && (textData.nodeValue != '\n')){ 
			rowData[i] = textData.nodeValue;
		}
	}
	msg.rowData = rowData; //Publisher Data
	vjo.dsf.ServiceEngine.handleRequest(msg); //Publish
},

/**
 * @JsJavaAccessToJs public
 * @JsReturnType void
 * @JsParamType poEvt com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
 * @JsParamType selectBoxId String
 * @JsParamType rowId String
 */
    //> public void selectAction(com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum poEvt,String selectBoxId,String rowId)
selectAction:function(poEvt,selectBoxId, rowId){
 	var msg = new com.ebay.darwin.app.blueprint.component.datagrid.DataGridMsg(this.selectActionSvcId);
 	msg.sRowId = rowId;
 	if(document.getElementById(selectBoxId).checked){
	 	msg.selectionValue = "selected";
	}else{
		msg.selectionValue = "unselected";
	}
	vjo.dsf.ServiceEngine.handleRequest(msg);
 }
, 
/**
* @JsJavaAccessToJs public
* @JsReturnType void
* @JsParamType poEvt com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
* @JsParamType rowId String
*/
    //> public void deleteAction(com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum poEvt,String rowId)
deleteAction:function(poEvt,rowId){ 
	var msg = new com.ebay.darwin.app.blueprint.component.datagrid.DataGridMsg(this.deleteActionSvcId);
	msg.sRowId = rowId;
	vjo.dsf.ServiceEngine.handleRequest(msg);
}
//snippet.publishaction.end
})
.endType();

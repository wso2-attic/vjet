vjo.itype('nonStaticPropAdvisor.ProtosAdvisorIType')
.props({
  sIprop1 : 25,
  sIprop2 : "Test"
})
.protos({
  pIFunction1 :  function (arg1) {},
  
  pIFunction2 :  function (arg1, arg2) {}
})
.endType();
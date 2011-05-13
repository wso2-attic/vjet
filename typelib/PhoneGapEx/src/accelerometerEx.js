var accelerometer = navigator.accelerometer;


accelerometer.getCurrentAcceleration(function(acceleration) {
    alert('Acceleration X: ' + acceleration.x + '\n' +
    'Acceleration Y: ' + acceleration.y + '\n' +
    'Acceleration Z: ' + acceleration.z + '\n' +
    'Timestamp: '      + acceleration.timestamp + '\n');
    
}, function(){
	// on error
});

var x = {
		doIt:function(){}

};
x.foobar = 10;

for(var x=0; x<10; x++) {
	
}
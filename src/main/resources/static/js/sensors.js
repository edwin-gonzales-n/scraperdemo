let urlMain = "https://us.wio.seeed.io";
let token1 = "access_token=e48f69f7d2658b62adbb59eaebaccfcd";
let token2 = "access_token=4077dcf98e877c3707cb1ef9c9f52c52";
let token3 = "access_token=feea889759bc30302b3af96dc9464518";
let moisture = "/v1/node/GroveMoistureA0/moisture?";
let humidity = "/v1/node/GroveTempHumD0/humidity?";
let temperature = "/v1/node/GroveTempHumD0/temperature_f?";
let DigitalLight = "/v1/node/GroveDigitalLightI2C1/lux?";

function getHumidity1() {
    $.get(urlMain+humidity+token1, function (data) {
        console.log(data);
        $('#sensor1').empty();
        // $('#sensor1').append('<br><h3>Humidity in Test Lab: <strong style="color: green">'+data.humidity+' %</strong></h3>');
        displayData(1,data.humidity,"percent");
    });
}

function getTemperature1() {
    $.get(urlMain+temperature+token1, function (data) {
        console.log(data);
        console.log(data.fahrenheit_degree);
        $('#sensor1').empty();
        // $('#sensor1').append('<br><h3>Temperature in Test Lab: <strong style="color: green">'+data.fahrenheit_degree+'<span> &#8457;</span></strong></h3>');
        // // $('#sensor1').append('<div class="GaugeMeter" data-percent="'+data.fahrenheit_degree+'" data-label="Fahrenheit"</div>')
        displayData(1,data.fahrenheit_degree,"f");
    });
}

function displayData(sensor,n,b){
    // console.log("This is the temperature: " +n);
    a = parseInt(n, 10);
    if(b === "percent"){
        let label = "Percent";
        $('#sensor'+sensor+'').append('<div class="GaugeMeter" data-percent="'+a+'" data-label="%" data-theme="LightGreen-DarkGreen"</div>');
        $(".GaugeMeter").gaugeMeter();
    }
    if(b === "f"){
        let label = "F";
        $('#sensor'+sensor+'').append('<div class="GaugeMeter" data-percent="'+a+'" data-label="'+label+'" data-theme="LightBlue-DarkBlue"</div>');
        $(".GaugeMeter").gaugeMeter();
    }
    if(b === "none"){
        $('#sensor'+sensor+'').append('<div class="GaugeMeter" data-percent="'+a+'" data-label="0-1023" data-theme="Black"</div>');
        $(".GaugeMeter").gaugeMeter();
    }
}

function getHumidity2() {
    $.get(urlMain+humidity+token2, function (data) {
        console.log(urlMain+humidity+token2);
        console.log(data);
        $('#sensor2').empty();
        // $('#sensor2').append('<br><h3>Humidity in Chemical Storage room: <strong style="color: green">'+data.humidity+' %</strong></h3>');
        displayData(2,data.humidity,"percent");
    });
}

function getTemperature2() {
    $.get(urlMain+temperature+token2, function (data) {
        console.log(data);
        console.log(data.fahrenheit_degree);
        $('#sensor2').empty();
        // $('#sensor2').append('<br><h3>Temperature in Chemical Storage room: <strong style="color: green">'+data.fahrenheit_degree+'<span> &#8457;</span></strong></h3>');
        displayData(2,data.fahrenheit_degree,"f");
    });
}

function getMoisture2(){
    $.get(urlMain+moisture+token2, function (data) {
        console.log(data);
        $('#sensor2').empty();
        // $('#sensor2').append('<br><h3>Moisture in Chemical Storage Room: <strong style="color: green">'+data.moisture+'</strong><span style="font-size: .5em"> (out of 1023)</span></h3>');
        displayData(2,data.moisture,"none");
    });
}

function getHumidity3() {
    $.get(urlMain+humidity+token3, function (data) {
        console.log(data);
        $('#sensor3').empty();
        // $('#sensor3').append('<br><h3>Humidity in Production Lab: <strong style="color: green">'+data.humidity+' %</strong></h3>');
        displayData(3,data.humidity,"percent");
    });
}

function getTemperature3() {
    $.get(urlMain+temperature+token3, function (data) {
        console.log(data);
        console.log(data.fahrenheit_degree);
        $('#sensor3').empty();
        // $('#sensor3').append('<br><h3>Temperature in Production Lab: <strong style="color: green">'+data.fahrenheit_degree+'<span> &#8457;</span></strong></h3>');
        displayData(3,data.fahrenheit_degree,"f");
    });
}
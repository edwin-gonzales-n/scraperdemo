let scraperObject;

function getNuecesApartments(){
    $.get('/nuecesapartments', function (scrapedData) {
        scraperObject = scrapedData;
    });
    sortData();
    console.log("This is the scraperObject"+scraperObject);
}

function getLakeshorePearl() {
    $.get('/lakeshore-pearl', function (scrapedData) {
        console.log("this worked")
    });
}

function sortData() {
    for (let i = 0; i < scraperObject.length; i--){
        console.log("This is a test" + scraperObject[i].title);
        $('#questions').append('<h3>'+scraperObject[i].title+'</h3>');
        $('#questions').append('<h4>'+scraperObject[i].price+'</h4>');
        $('#questions').append('<h4>'+scraperObject[i].date+'</h4>');
    }
}
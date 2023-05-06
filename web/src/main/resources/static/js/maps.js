function getDataView(type) {
    var newOrder  = [0,6];
    if (type == "yoy") {
        newOrder  = [0,6];
    }
    else if (type == "mom") {
        newOrder  = [0,5];
    }
    else if (type == "values") {
        newOrder  = [0,4];
    }
    var data = getDataTable();

    var myView = new google.visualization.DataView(analyticsDataTable);
    myView.setColumns(newOrder);
    return myView;
}

function updateMap(type){
    drawMap(getDataView(type), getMapOption(getDataView(type)));
}

function getMapOption(data) {
    /*
    var options = {
      region: 'US',
      displayMode: 'regions',
    };*/

    var options = {region: 'US',
        displayMode: 'regions',
        resolution: 'provinces',

        colorAxis: {
            //colors: [ '#0077cc', '#b3d9ff','#e6ccff', '#6600cc'],
            colors: [ '#0077cc', '#80bfff', '#fff4b3', '#ffcc00'],
            //values: [-5, 0, 0, 5]
            //minValue: -100,
            //maxValue: 100,
            minValue: -20, //data.getColumnRange(1).min,
            maxValue: 20, //data.getColumnRange(1).max,
            midpoint: 0,
        }
        /*
        for color '#ffd237' : #fff4b3
            #ffe680
            #ffd24d
            #ffbf1a
            #ffcc00
        for color '#007bff' : #b3d9ff
            #80bfff
            #4d9dff
            #1a7fff
            #0077cc
        */
        //colors: ['#E0FFD4', '#A5EF63', '#50AA00', '#267114'], //green
        //colors: ['#e6f4ff', '#008ffb', '#ff4560'] //nice from internet
        //backgroundColor: '#232323',
        /*
        chartArea: { 
            width: '100%', 
            height: '100%', 
            top: 2000, 
            left: 0 
        }*/
    };
    //options['colors'] = ['#FF8747', '#FFB581', '#C06000']; //my nice colors
    return options;
}

function createDataTable(analyticsData){
    var data = new google.visualization.DataTable();
    data.addColumn({id: 'stateName', label: 'State', type: 'string'});
    data.addColumn({id: 'stateShortName', label: 'Code', type: 'string'});
    data.addColumn({id: 'prevYearValue', label: analyticsData[0].prevYearDate, type: 'number'});
    data.addColumn({id: 'currentValue', label: analyticsData[0].prevMonthDate, type: 'number'});
    data.addColumn({id: 'prevMonthValue', label: analyticsData[0].date, type: 'number'});
    data.addColumn({id: 'momChange', label: 'Month-over-Month Change', type: 'number'});
    data.addColumn({id: 'yoyChange', label: 'Year-over-Year Change', type: 'number'});

    var rows = analyticsData.map(function(item) {
        return [item.region.name, item.region.shortName, item.prevYearValue, item.prevMonthValue, item.homeValue, item.momChange, item.yoyChange];
    });

    data.addRows(rows);

    var formatterValue = new google.visualization.NumberFormat({
        prefix: '$'
    });
    formatterValue.format(data, 2);
    formatterValue.format(data, 3);
    formatterValue.format(data, 4);

    var formatterChange = new google.visualization.NumberFormat({
        suffix: '%', 
        negativeColor: 'red'
    });
    formatterChange.format(data, 5);
    formatterChange.format(data, 6);
    analyticsDataTable = data;
    return data;
}
  
function getDataTable(){
    return analyticsDataTable;
}
  
function drawMap(analyticsDataView, options) {

    /*var data = createDataTable(analyticsData);
    var newOrder = [0,6];
    var myView = new google.visualization.DataView(data);
    myView.setColumns(newOrder);
    var options = getMapOption();
    var geomap = new google.visualization.GeoChart(container);*/
    //geomap.draw(data, options);
    analyticsGeoMap.draw(analyticsDataView, options);
};
function drawTable(analyticsDataTable){
    var table = new google.visualization.Table( document.getElementById('analytics_table_chart'));
    table.draw(analyticsDataTable, {showRowNumber: true, width: '100%', height: '100%'});
}

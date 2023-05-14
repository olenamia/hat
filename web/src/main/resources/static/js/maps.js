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

    var minValue = data.getColumnRange(1).min;
    var maxValue = data.getColumnRange(1).max;

    var options = {
        region: 'US',
        displayMode: 'regions',
        resolution: 'provinces',

        colorAxis: {
            colors: [ '#0077cc', '#80bfff', '#c0dad9', '#fff4b3', '#ffcc00'],
            minValue: minValue,
            maxValue: maxValue,
            midpoint: minValue < 0 && maxValue > 0 ? 0 : false,
        }
    };
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

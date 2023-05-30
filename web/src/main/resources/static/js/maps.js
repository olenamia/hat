function createDataTable(analyticsData) {
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({id: 'stateName', label: 'State', type: 'string'});
    dataTable.addColumn({id: 'stateShortName', label: 'Code', type: 'string'});
    dataTable.addColumn({id: 'prevYearValue', label: analyticsData[0].prevYearDate, type: 'number'});
    dataTable.addColumn({id: 'currentValue', label: analyticsData[0].prevMonthDate, type: 'number'});
    dataTable.addColumn({id: 'prevMonthValue', label: analyticsData[0].date, type: 'number'});
    dataTable.addColumn({id: 'momChange', label: 'Month-over-Month Change', type: 'number'});
    dataTable.addColumn({id: 'yoyChange', label: 'Year-over-Year Change', type: 'number'});

    var rows = analyticsData.map(function(item) {
        return [item.region.name, item.region.shortName, item.prevYearValue, item.prevMonthValue, item.homeValue, item.momChange, item.yoyChange];
    });

    dataTable.addRows(rows);

    var formatterValue = new google.visualization.NumberFormat({
        prefix: '$'
    });

    formatterValue.format(dataTable, 2);
    formatterValue.format(dataTable, 3);
    formatterValue.format(dataTable, 4);

    var formatterChange = new google.visualization.NumberFormat({
        suffix: '%', 
        negativeColor: 'red'
    });

    formatterChange.format(dataTable, 5);
    formatterChange.format(dataTable, 6);
    analyticsDataTable = dataTable;
    return dataTable;
}

function getDataView(type) {
    var columnOrder  = [0,6];

    if (type == "yoy") {
        columnOrder  = [0,6];
    }
    else if (type == "mom") {
        columnOrder  = [0,5];
    }
    else if (type == "values") {
        columnOrder  = [0,4];
    }

    var myView = new google.visualization.DataView(analyticsDataTable);
    myView.setColumns(columnOrder);
    return myView;
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

function drawMap(analyticsDataView, options) {
    analyticsGeoMap.draw(analyticsDataView, options);
};

function updateMap(type) {
    drawMap(getDataView(type), getMapOption(getDataView(type)));
}

function drawTable(analyticsDataTable) {
    var table = new google.visualization.Table( document.getElementById('analytics_table_chart'));
    table.draw(analyticsDataTable, {showRowNumber: true, width: '100%', height: '100%', 'pageSize': 25});
}

function loadSubRegions(regionType, stateName){
    var url = '/hat/api/regions/' + stateName;
    if (regionType == "County") {
        url += '/counties';
    }
    else if (regionType == "Metro Area") {
        url += '/metros' ;
    }

    fetch(url)
        .then(response => response.json())
        .then(subRegions => {
            var subRegionItemsHtml = '';
            for (var i = 0; i < subRegions.length; i++) {
                subRegionItemsHtml += '<a class="dropdown-item" href="#" data-method="updateRegion" data-param1="' +regionType+ '" data-param2="' + subRegions[i].regionId + '">' + subRegions[i].name + '</a>';
                subRegionItemsHtml += '<div class="dropdown-divider"></div>';
            }
            $('#toAddSubRegions').html(subRegionItemsHtml);
            //$('#subRegion').css("visibility", "visible");
            $('#subRegionDropDown').css("display", "block");
            $('#subRegionDropDownChoice').on('click', '.dropdown-item', function(e) {
                subRegionDropDownChoiceClick(e, $(this));
            });
            //$('#subRegionDropDown').dropdown();
        })
        .catch(error => {
            console.error(error);
        });
}

function updateRegion(type) {
    updateRegion(type, null, null);
}

function updateRegion(type, stateName) {
    updateRegion(type, stateName, null);
}

function updateRegion(type, stateName, regionId) {

    var url = '';
    if (type == "US") {
        url = '/hat/api/historical/values/US';
    }
    else if (type == "State") {
        url = '/hat/api/historical/values/state/' + stateName;
    }
    else if (type == "Metro Area") {
        url = '/hat/api/historical/values/metro/' + stateName + "/" + regionId;
    }
    else if (type == "County") {
        url = '/hat/api/historical/values/county/' + stateName + "/" + regionId;
    }

    fetch(url)
    .then(response => response.json())
    .then(data => {
        // TODO: create one DataTable and add DataViews
        drawHomeValueChart(data, document.getElementById('historical_values_chart'));
        drawYoYChangeChart(data, document.getElementById('historical_yoy_chart'));
        drawTableChart(data, document.getElementById('historical_table_chart'));
    })
    .catch(error => {
        console.error(error);
    });
}

function drawHomeValueChart(histoticalData, htmlElement) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'Home Value');

    histoticalData.forEach(function(key) {
        data.addRow([ key.date, key.homeValue ]);
    });

    var options = {
        chart: {
            title: 'Housing Pricing',
            subtitle: 'in dollars (USD)'
        },
        width: 900,
        height: 500
    };
    var chart = new google.charts.Line(htmlElement);
    chart.draw(data, google.charts.Line.convertOptions(options));
}

function drawYoYChangeChart(histoticalData, htmlElement) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'YoY change');

    histoticalData.forEach(function(key) {
        data.addRow([ key.date, key.yoyChange ]);
    });

    var options = {
        chart: {
            title: 'Year Over Year Housing Price Change',
            subtitle: 'in %'
        },
        width: 900,
        height: 500
    };
    var chart = new google.charts.Line(htmlElement);
    chart.draw(data, google.charts.Line.convertOptions(options));
}

function drawTableChart(histoticalData, htmlElement) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'Home Value');
    data.addColumn('number', 'YoY change');

    histoticalData.forEach(function(key) {
        data.addRow([ 
            key.date, 
            {v: key.homeValue, f: "$ " + key.homeValue.toFixed(2)}, 
            {v: key.yoyChange, f: key.yoyChange ? key.yoyChange.toFixed(2) + " %" : ""}
        ]);
    });

    var table = new google.visualization.Table(htmlElement);
    table.draw(data, {showRowNumber: true, width: '100%', height: '100%', 'pageSize': 25});
}

function drawColumnChart(histoticalData, htmlElement) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'Value');
    data.addColumn('number', 'YoY');

    histoticalData.forEach(function(key) {
        data.addRow([ key.date, key.homeValue, key.yoyChange ]);
    });
    var options = {
        title : 'House Values',
        hAxis : {
            title : 'Years',
        },
        vAxis : {
            title : 'Home Value'
        }
    };
    var chart = new google.visualization.ColumnChart(htmlElement);
    chart.draw(data, options);
}
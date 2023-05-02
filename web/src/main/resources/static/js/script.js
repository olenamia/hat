

function getRegionLabelByType(type){

    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

function updateRegion(stateName, type) {
    /*var selectedOption = selectElement.options[selectElement.selectedIndex];
    var stateStr = selectedOption.dataset.state;
    var stateName = selectedOption.text;
    var stateShortName = selectedOption.dataset.shortName;
    var stateId = selectedOption.value;*/
    //alert(selectedOption.text);
    //var stateName = state.stateName;
    //console.log(state);
    console.log(type);
    console.log(stateName);


    fetch('/hat/api/historical/values/state/' + stateName)
    .then(response => response.json())
    .then(data => {
/*
        real_data = JSON.parse(data);
        real_data.forEach(function(key) {
            data.addRow([ key.date, key.homeValue ]);
        });*/

        //var historicData =  JSON.parse(data);
        
        drawHomeValueChart(data, document.getElementById('historical_values_chart'));
        drawYoYChangeChart(data, document.getElementById('historical_yoy_chart'));
        drawTableChart(data, document.getElementById('historical_table_chart'));
    })
    .catch(error => {
        console.error(error);
    });
  }

    function drawHomeValueChart(real_data, htmlElement) {
        var data = new google.visualization.DataTable();

        data.addColumn('string', 'Date');
        data.addColumn('number', 'Home Value');

        real_data.forEach(function(key) {
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
    function drawYoYChangeChart(real_data, htmlElement) {
        var data = new google.visualization.DataTable();

        data.addColumn('string', 'Date');
        data.addColumn('number', 'YoY change');

        real_data.forEach(function(key) {
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
    function drawTableChart(real_data, htmlElement) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Home Value');
        data.addColumn('number', 'YoY change');

        real_data.forEach(function(key) {
              data.addRow([ key.date, {v: key.homeValue, f: "$"+key.homeValue}, key.yoyChange ]);
          });

        var table = new google.visualization.Table(htmlElement);

        table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
      }
      function drawColumnChart(real_data, htmlElement) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Value');
        data.addColumn('number', 'YoY');

        real_data.forEach(function(key) {
            //alert(key.date + " " + key.homeValue);
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


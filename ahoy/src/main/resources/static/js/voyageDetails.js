const dateTimeArray = [];
const avgSpeedArray = [];
const maxSpeedArray = [];
const currentAbbrvslm = localStorage.getItem('abbrvslm');

$(document).ready( function () {
    document.getElementById('getVesselName').innerHTML = "Vessel Name: " +currentAbbrvslm;
    let mid = {"abbrvslm" : currentAbbrvslm}
    data1 = JSON.stringify(mid);

	 $.ajax({
           url: "/ajax/retrieveVoyageByVessel",
           type:"POST",
           data:data1,
           contentType:"application/json; charset=utf-8",
           dataType:"json",
           success: function(response){
            if(response.length==0){
                alert("No Voyages found!");
            }
             $(".ajax").remove();
             $.each(response, function(key,val){
                 let row = "";
                 if (val.berth==null){
                    row = "<tr><td><div class='toggleTable2' data-value='"+val.invoyn+"'>"+val.invoyn+"</div></td><td>"+val.outvoyn+"</td><td>"+val.berth+"</td><td>"+val.status+"</td><td>"+val.btrdt.split(" ")[1]+"</td><td>"+val.unbthgdt.split(" ")[1]+"</td></tr>";
                 }else{
                    row = "<tr><td><div class='toggleTable2' data-value='"+val.invoyn+"'>"+val.invoyn+"</div></td><td>"+val.outvoyn+"</td><td>"+val.berth.berthnum+"</td><td>"+val.status+"</td><td>"+val.btrdt.split(" ")[1]+"</td><td>"+val.unbthgdt.split(" ")[1]+"</td></tr>";
                 }

               $(row).insertAfter("#voyageOfVessel");
             })
           }
         })
});

$(document).on('click','.toggleTable2',function(){

    $('.toggleTable2').css('cursor', 'pointer');
    let detail = $(this).data("value");
    let mid = {"abbrvslm" : currentAbbrvslm, "invoyn" : detail}
    data2 = JSON.stringify(mid);
    $.ajax({
       url: "/ajax/retrieveVoyageDetailsByVessel",
       type:"POST",
       data:data2,
       contentType:"application/json; charset=utf-8",
       dataType:"json",
       success: function(response){
         $(".toggled2").remove();
         if(response.length==0){
            alert("No Speed History Found!");
         }
         var prev_speed = parseFloat("0.0");
         var count = 0;
         $.each(response, function(key,val){
              var curr_speed = parseFloat(val.avg_speed);
              let row = "";
              if(count == 0){
                row = "<tr class='toggled2'style='background-color:white;' ><td>"+val.timestamp+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td></tr>";
              }else if((curr_speed-prev_speed)>0.0){
                row = "<tr class='toggled2'style='background-color:green;' ><td>"+val.timestamp+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td></tr>";
              } else if((curr_speed-prev_speed)<0.0){
                row = "<tr class='toggled2'style='background-color:red;' ><td>"+val.timestamp+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td></tr>";
              } else {
                row = "<tr class='toggled2'style='background-color:white;' ><td>"+val.timestamp+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td></tr>";
              }

              $(row).insertAfter("#voyageDetailsOfVessel");
              prev_speed = curr_speed;
              count++;
         })
       }
    })

    loadChart();
    getChart();

})

function loadChart(){
    $.ajax({
           async: false,
           url: "/ajax/retrieveVoyageAvgSpeedByVesselChart",
           type:"POST",
           data:data2,
           contentType:"application/json; charset=utf-8",
           dataType:"json",
           success: function(response){
                dateTimeArray.length=0;
                avgSpeedArray.length=0;

                for (var i in response){
                    dateTimeArray.push(i);
                    avgSpeedArray.push(response[i]);
                }
           }
    })
    $.ajax({
           async: false,
           url: "/ajax/retrieveVoyageMaxSpeedByVesselChart",
           type:"POST",
           data:data2,
           contentType:"application/json; charset=utf-8",
           dataType:"json",
           success: function(response){
                maxSpeedArray.length = 0;
                for (var i in response){
                    maxSpeedArray.push(response[i]);
                }
           }
    })

}

function getChart(){

    Highcharts.chart('container', {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Visualization of Voyage Speed History'
        },
        subtitle: {
            text: currentAbbrvslm+' Source: PSA'
        },
        xAxis: {
            categories: dateTimeArray
        },
        yAxis: {
            title: {
                text: 'Knots'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            }
        },
        series: [{
            name: 'Average Speed',
            data: avgSpeedArray
        },{
            name: 'Maximum Speed',
            data: maxSpeedArray
        }]
        });

}
$(document).ready( function () {
  loadVoyages();
  loadAlerts();
  });
  



function loadVoyages(){
  $.ajax({
    url: "/ajax/retrieveTodayVoyage",
    type:"GET",
    dataType:"json",
    success: function(response){
      $(".ajax").remove();

      $.each(response, function(key,val){
//             console.log(response);
//             console.log(val);
//             console.log(val.voyagePK);
//               var card = '<div class="flex"><button class="" value = "' + val + '" type="button">' + val.voyage.invoyn + '</button></div>';
//               let row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"val.voyagePK.invoyn+"</td> <td>"+val.status+"</td> <td>"+val.berth"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
        let row = "";
        if (val.berth==null){
        row = '<tr class = "ajax"> <td>'+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";

       }else{
         row = '<tr class = "ajax"> <td>'+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+ (val.berth.berthnum) +"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";


       }
//                let row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>"
        $(row).insertAfter("#todayVoyageRecord");
      })
    }
  })
}


function loadAlerts(){
  $.ajax({
    url: "/ajax/retrieveSubscribedAlerts",
    type:"GET",
    dataType:"json",
    success: function(response){
      console.log("1");
      $(".alert").remove();
      $.each(response, function(key,val){
        console.log(val);
        var alertrow = '<tr class = "alert"> <td>'+val.alertPK.alerttype +'</td> <td>'+val.alertPK.voyagePK.abbrvslm + '</td><td>' + val.voyage.status + '</td> <td>' + val.voyage.fullinvoyn + '</td><td>' + val.voyage.btrdt.split(" ")[1] +'</td><td>'+ val.alertcontent+ '</td> <td>test</td></tr>';
        console.log("2");
        // var alertrow = '<tr><td>test</td></tr>';
        console.log(alertrow);
        $(alertrow).insertAfter("#todayAlertRecord");
        console.log("3");
      })
    }
  })
}

$("#option2").click(function(){
  	 $.ajax({
           url: "/ajax/retrieveTodaySubscribedVoyages",
           type:"GET",
           dataType:"json",
           success: function(response){
             $(".ajax").remove();
             $.each(response, function(key,val){
                console.log(val);
                console.log(val.voyagePK);

               if (val.berth==null){
               row =  '<tr class = "ajax"> <td>'+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               }else{
                row = '<tr class = "ajax"> <td>'+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth.berthnum+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               }


               $(row).insertAfter("#todayVoyageRecord");
             })
           }
         })
});

$("#option1").click(function(){
	 $.ajax({
           url: "/ajax/retrieveTodayVoyage",
           type:"GET",
           dataType:"json",
           success: function(response){
             $(".ajax").remove();

             $.each(response, function(key,val){
//             console.log(response);
//             console.log(val);
//             console.log(val.voyagePK);
//               var card = '<div class="flex"><button class="" value = "' + val + '" type="button">' + val.voyage.invoyn + '</button></div>';
//               let row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"val.voyagePK.invoyn+"</td> <td>"+val.status+"</td> <td>"+val.berth"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               let row = "";
               if (val.berth==null){
               row = '<tr class = "ajax"> <td>'+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";

              }else{
                row = '<tr class = "ajax"> <td>'+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+ (val.berth.berthnum) +"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";


              }
//                let row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>"
               $(row).insertAfter("#todayVoyageRecord");
             })
           }
         })
  });


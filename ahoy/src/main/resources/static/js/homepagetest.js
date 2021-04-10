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
      
      $(".alert").remove();
      $.each(response, function(key,val){
        
        let isChecked = ""; 
        let readKey = "Alert"+val.alertPK.voyagePK.abbrvslm + val.alertPK.voyagePK.invoyn + val.alertPK.alerttype;
        if (localStorage.getItem(readKey) != null){
          isChecked = "checked";
          return;
        }
        var alertrow = '<tr class = "alert"> <td>'+val.alertPK.alerttype +'</td> <td>'+val.alertPK.voyagePK.abbrvslm + '</td><td>' + val.voyage.status + '</td> <td>' + val.voyage.fullinvoyn + '</td><td>' + val.voyage.btrdt.split(" ")[1] +'</td><td>'+ val.alertcontent+ '</td> <td class = "checkbox"><input type = "checkbox" class = "readNotif" value = "'+ readKey +  '"></td></tr>';
        
        $(alertrow).insertAfter("#todayAlertRecord");
        
      })
    }
  })
}

$(document).on('click','.readNotif',function(){
  
  if($(this).prop("checked") == true){
    localStorage.setItem($(this).val(), "checked");
    $(this).parent().parent().remove();
  } 
  
})

$("#showAllAlerts").click(function (){
  for(let i=0; i<localStorage.length; i++) {
    let key = localStorage.key(i);
    if(key.startsWith("Alert")){
      localStorage.removeItem(key);
      i--;
    }
  }
  loadAlerts();
})

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


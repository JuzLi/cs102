$(document).ready( function () {
	 $.ajax({
           url: "/ajax/retrieveTodayVoyage",
           type:"GET",
           dataType:"json",
           success: function(response){
             $(".ajax").remove();
             $.each(response, function(key,val){
             console.log(response);
             console.log(val);
             console.log(val.voyagePK);
               let row = "";
               if (val.berth==null){
               row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               }else{
                row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth.berthnum+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               }
               $(row).insertAfter("#todayVoyageRecord");
             })
           }
         })
});

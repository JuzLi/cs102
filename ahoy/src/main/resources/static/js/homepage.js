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
//               var card = '<div class="flex"><button class="" value = "' + val + '" type="button">' + val.voyage.invoyn + '</button></div>';
//               let row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"val.voyagePK.invoyn+"</td> <td>"+val.status+"</td> <td>"+val.berth"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               let row = "";
               if (val.berth==null){
               row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               }else{
                row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth.berthnum+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               }
//                let row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>"

               $(row).insertAfter("#todayVoyageRecord");
             })
           }
         })
});

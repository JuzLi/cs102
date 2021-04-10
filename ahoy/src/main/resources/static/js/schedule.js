$(document).ready( function () {
	 $.ajax({
           url: "/ajax/retrieveDetailedVoyageRecordByDay",
           type:"GET",
           dataType:"json",
           success: function(response){
             $(".ajax").remove();
             $.each(response, function(key,val){
             console.log(response);
             console.log(val);
               let row = "";
//             row = "<tr> <td>"+val.voyagePK.abbrvslm+"</td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.outvoyn+"</td><td>"+val.status+"</td> <td>"+val.berth.berthnum+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
               if(val!=null)
                row = "<tr><td>"+val.voyage.vessel.abbrvslm+"</td><td>"+val.voyage.invoyn+"</td><td>"+val.voyage.outvoyn+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td><td>"+val.voyage.btrdt.split(" ")[1]+"</td><td>"+val.voyage.unbthgdt.split(" ")[1]+"</td><td>"+val.voyage.berth.berthnum+"</td><td>"+val.voyage.status+"</td><td class='checkbox'><input type='checkbox'></td></tr>";
               $(row).insertAfter("#detailedVoyageRecordByDay");
             })
           }
         })
});




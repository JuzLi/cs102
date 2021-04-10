$(document).ready(
    loadData()
);

$("#filterSelector").change(function(){
    let filter = $(this).val();
    if(filter === "All"){
        loadData();
    }
    else{
        loadFilteredData(filter);
    }
})
function loadData() {
   $.ajax({
           url: "/ajax/retrieveTodayAlerts",
           type:"GET",
           dataType:"json",
           success: function(response){
            $(".ajax").remove();
             $.each(response, function(key,val){
              console.log(val);
             
              let row = "";
              if(val!=null){
                
                row = '<tr class = "ajax"><td>'+val.alertPK.alerttype+"</td><td>"+val.alertPK.voyagePK.abbrvslm+"</td><td>"+val.alertPK.voyagePK.invoyn+"</td><td>"+val.alertdatetime+"</td><td>"+val.alertcontent+"</td></tr>";
                $(row).insertAfter("#alertTable");
              } 
             })
           }
         })
  }


  function loadFilteredData(filter) {
      let mid = {"filter" : filter};
      let data = JSON.stringify(mid);
    $.ajax({
            url: "/ajax/retrieveTodayAlertsFiltered",
            type:"POST",
            data:data,
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function(response){
              $(".ajax").remove();
              $.each(response, function(key,val){
               console.log(val);
              
               let row = "";
               if(val!=null){
                 
                 row = '<tr class = "ajax"><td>'+val.alertPK.alerttype+"</td><td>"+val.alertPK.voyagePK.abbrvslm+"</td><td>"+val.alertPK.voyagePK.invoyn+"</td><td>"+val.alertdatetime+"</td><td>"+val.alertcontent+"</td></tr>";
                 $(row).insertAfter("#alertTable");
               } 
              })
            }
          })
   }
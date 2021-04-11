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

   function sortTableAlerts(){
     var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
     table = document.getElementById("alertTableSort");
     switching = true;
     dir = "asc";
     while (switching) {
       switching = false;
       rows = table.rows;
       for (i = 1; i < (rows.length - 1); i++) {
         shouldSwitch = false;
         x = rows[i].getElementsByTagName("td")[1];
         y = rows[i + 1].getElementsByTagName("td")[1];
         if (dir == "asc") {
           if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
             shouldSwitch = true;
             break;
           }
         } else if (dir == "original") {
             loadData();
         }
       }
       if (shouldSwitch) {
         rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
         switching = true;
         switchcount ++;
       } else {
         if (switchcount == 0 && dir == "asc") {
           dir = "original";
           switching = true;
         }
       }
     }
   }


   $("#sortNameButtonAlerts").click(function(){
        sortTableAlerts();
   });

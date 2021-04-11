$(document).ready( function () {
  loadTodayVoyages();
  loadAlerts();
});
  

$("#sortVesselNameHomepageSchedule").click(function(){
 sortTableHomepage(0);
});

$("#sortBerthStatusHomepage").click(function(){
 sortTableHomepage(2);
});

$("#sortVesselNameHomepageAlerts").click(function(){
  sortTableAlertsHomepage();
      
});

$("#allVoyages").click(function(){
  loadTodayVoyages();
}
);

$("#subscribedVoyages").click(function(){
  loadSubscribedVoyages();
}
);


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
  let filter = $("#alertFilter").val()
  if(filter == "allAlerts"){
    loadAlerts();
  }
  else{
    loadFilteredData(filter);
  }
})


$("#alertFilter").change(function(){
  let filter = $(this).val();
  if(filter == "allAlerts"){
    loadAlerts();
  }
  else{
    loadFilteredData(filter);
  }
});


$(document).on('click','.openDetailPage',function(){
    $('.openDetailPage').css('cursor', 'pointer');
    let detail = $(this).data("value");
    localStorage.setItem("abbrvslm", detail);
    window.location.href='voyageDetails';
})

function sortTableAlertsHomepage() {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("todayAlertsTable");
  switching = true;
  dir = "asc";
  while (switching) {
    switching = false;
    rows = table.rows;
    for (i = 1; i < (rows.length - 1); i++) {
      shouldSwitch = false;
      x = rows[i].getElementsByTagName("td")[0];
      y = rows[i + 1].getElementsByTagName("td")[0];
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          shouldSwitch = true;
          break;
        }
      } else if (dir == "original") {
        let filter = $("#alertFilter").val()
        if(filter == "allAlerts"){
          loadAlerts();
        }
        else{
          loadFilteredData(filter);
        }
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

function sortTableHomepage(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("todayVoyageTable");
  switching = true;
  dir = "asc";
  while (switching) {
    switching = false;
    rows = table.rows;
    for (i = 1; i < (rows.length - 1); i++) {
      shouldSwitch = false;
      x = rows[i].getElementsByTagName("td")[n];
      y = rows[i + 1].getElementsByTagName("td")[n];
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          shouldSwitch = true;
          break;
        }
      } else if (dir == "original") {
        loadTodayVoyages();
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





function loadSubscribedVoyages(){
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
                 row = '<tr class = "ajax"> <td><div class="openDetailPage" data-value="'+val.voyagePK.abbrvslm+'">'+val.voyagePK.abbrvslm+"</div></td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
            }else{
                 row = '<tr class = "ajax"> <td><div class="openDetailPage" data-value="'+val.voyagePK.abbrvslm+'">'+val.voyagePK.abbrvslm+"</div></td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+ (val.berth.berthnum) +"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
            }


            $(row).insertAfter("#todayVoyageRecord");
          })
        }
      })
};

function loadTodayVoyages(){
$.ajax({
        url: "/ajax/retrieveTodayVoyage",
        type:"GET",
        dataType:"json",
        success: function(response){
          $(".ajax").remove();

          $.each(response, function(key,val){
            let row = "";
            if (val.berth==null){
                row = '<tr class = "ajax"> <td><div class="openDetailPage" data-value="'+val.voyagePK.abbrvslm+'">'+val.voyagePK.abbrvslm+"</div></td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+val.berth+"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
            }else{
                row = '<tr class = "ajax"> <td><div class="openDetailPage" data-value="'+val.voyagePK.abbrvslm+'">'+val.voyagePK.abbrvslm+"</div></td> <td>"+val.voyagePK.invoyn+"</td><td>"+val.status+"</td> <td>"+ (val.berth.berthnum) +"</td><td>"+val.btrdt.split(" ")[1]+"</td></tr>";
            }
            $(row).insertAfter("#todayVoyageRecord");
          })
        }
      })
};

function loadFilteredData(filter) {
  let mid = {"filter" : filter};
  let data = JSON.stringify(mid);
  $.ajax({
        url: "/ajax/retrieveSubscribedAlertsFiltered",
        type:"POST",
        data:data,
        contentType:"application/json; charset=utf-8",
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



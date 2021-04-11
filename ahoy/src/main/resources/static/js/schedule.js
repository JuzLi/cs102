
let days = "0"

$(document).ready(loadData());

function loadData() {
  let mid = {"numDays" : days, "subscribed" : "unsubscribed"};
  let data = JSON.stringify(mid);

 $.ajax({
         url: "/ajax/retrieveDetailedVoyageRecordByDay",
         type:"POST",
         data:data,
         contentType:"application/json; charset=utf-8",
         dataType:"json",
         success: function(response){
           $(".ajax").remove();
           $.each(response, function(key,val){
            
           
            let row = "";
            if(val!=null){
              let vessel = val.voyage.vessel.abbrvslm;
              let isChecked = ""
              if(localStorage.getItem(vessel) != null){
                isChecked = "checked"
              }
              if(val.voyage.berth==null){
                row = '<tr class = "ajax"><td><div class="openDetailPage" data-value="'+val.voyage.vessel.abbrvslm+'">'+val.voyage.vessel.abbrvslm+"</div></td><td>"+val.voyage.invoyn+"</td><td>"+val.voyage.outvoyn+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td><td>"+val.voyage.btrdt.split(" ")[1]+"</td><td>"+val.voyage.unbthgdt.split(" ")[1]+"</td><td>"+val.voyage.berth+"</td><td>"+val.voyage.status+'</td><td class="checkbox"><input type="checkbox" class = "createPref" value ="' + val.voyage.vessel.abbrvslm +  '"' + isChecked + '></td></tr>';

              }else{
                row = '<tr class = "ajax"><td><div class="openDetailPage" data-value="'+val.voyage.vessel.abbrvslm+'">'+val.voyage.vessel.abbrvslm+"</div></td><td>"+val.voyage.invoyn+"</td><td>"+val.voyage.outvoyn+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td><td>"+val.voyage.btrdt.split(" ")[1]+"</td><td>"+val.voyage.unbthgdt.split(" ")[1]+"</td><td>"+val.voyage.berth.berthnum+"</td><td>"+val.voyage.status+'</td><td class="checkbox"><input type="checkbox" class = "createPref" value ="' + val.voyage.vessel.abbrvslm +  '"' + isChecked + '></td></tr>';
              }
              $(row).insertAfter("#detailedVoyageRecordByDay");
            } 
           })
         }
       })
}

$("#daySelector").change(function () {
  let val = $(this).val();
  days = val;
  if($("#filterCheck").prop("checked") == true){
    loadSubscribedData();
  }
  else{
    loadData();
  }
  
})

$(document).on('click','.createPref',function(){
  let vessel = $(this).val();
  let mid = {"abbrvslm" : vessel}
  data = JSON.stringify(mid);
  
  if($(this).prop("checked") == true){
    localStorage.setItem(vessel, true);
    $.ajax({
      url: "/ajax/createVesselPreference",
      type:"POST",
      data:data,
      contentType:"application/json; charset=utf-8",
      dataType:"text",
      success: function(response){
      }
    })
  } else if($(this).prop("checked") == false){
    localStorage.removeItem(vessel);
    $.ajax({
      url: "/ajax/removeVesselPreference",
      type:"POST",
      data:data,
      contentType:"application/json; charset=utf-8",
      dataType:"text",
      success: function(response){
      }
    })
  }
  
})

$("#filterCheck").click(function(){
  if($(this).prop("checked") == true){
    loadSubscribedData();
  } else{
    loadData();
  }
})



function loadSubscribedData() {
  let mid = {"numDays" : days, "subscribed" : "subscribed"};
  let data = JSON.stringify(mid);

 $.ajax({
         url: "/ajax/retrieveSubscribedDetailedVoyageRecordByDay",
         type:"POST",
         data:data,
         contentType:"application/json; charset=utf-8",
         dataType:"json",
         success: function(response){
           $(".ajax").remove();
           $.each(response, function(key,val){
            
           
            let row = "";
            if(val!=null){
              let vessel = val.voyage.vessel.abbrvslm;
              let isChecked = ""
              if(localStorage.getItem(vessel) != null){
                isChecked = "checked"
              }
              if(val.voyage.berth==null){
                row = '<tr class = "ajax"><td><div class="openDetailPage" data-value="'+val.voyage.vessel.abbrvslm+'">'+val.voyage.vessel.abbrvslm+"</div></td><td>"+val.voyage.invoyn+"</td><td>"+val.voyage.outvoyn+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td><td>"+val.voyage.btrdt.split(" ")[1]+"</td><td>"+val.voyage.unbthgdt.split(" ")[1]+"</td><td>"+val.voyage.berth+"</td><td>"+val.voyage.status+'</td><td class="checkbox"><input type="checkbox" class = "createPref" value ="' + val.voyage.vessel.abbrvslm +  '"' + isChecked + '></td></tr>';

              }else{
                row = '<tr class = "ajax"><td><div class="openDetailPage" data-value="'+val.voyage.vessel.abbrvslm+'">'+val.voyage.vessel.abbrvslm+"</div></td><td>"+val.voyage.invoyn+"</td><td>"+val.voyage.outvoyn+"</td><td>"+val.avg_speed+"</td><td>"+val.max_speed+"</td><td>"+val.distance_to_go+"</td><td>"+val.voyage.btrdt.split(" ")[1]+"</td><td>"+val.voyage.unbthgdt.split(" ")[1]+"</td><td>"+val.voyage.berth.berthnum+"</td><td>"+val.voyage.status+'</td><td class="checkbox"><input type="checkbox" class = "createPref" value ="' + val.voyage.vessel.abbrvslm +  '"' + isChecked + '></td></tr>';
              }
              $(row).insertAfter("#detailedVoyageRecordByDay");
            } 
           })
         }
       })
}


$(document).ready( function () {
    loadData();
});

function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("scheduleTable");
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

$("#sortNameButton").click(function(){
sortTable(0);

});

$("#sortBerthingTimeButton").click(function(){
sortTable(6);

});


let filters = ["",""];

function setFilter(thisInput, thisIndex) {
  filters[thisIndex] = thisInput.value.toUpperCase();

  filterTable();
}
function filterTable() {
  var table = document.getElementById("scheduleTable");
  var rows = Object.values(table.getElementsByTagName("tr"));

  for(var rowItr=1; rowItr < rows.length; rowItr++) {
    var row = rows[rowItr];
    var cells = Object.values(row.getElementsByTagName("td"));


    var isRowVisible = filters.every((filter, filterIndex) => {
        var cell = cells[filterIndex];
        var txtValue = cell.textContent || cell.innerText;

        return filter === "" || txtValue.toUpperCase().indexOf(filter) == 0;
    });

    row.style.display = isRowVisible ? "" : "none";
  };
}

$(document).on('click','.openDetailPage',function(){
    $('.openDetailPage').css('cursor', 'pointer');
    let detail = $(this).data("value");
    localStorage.setItem("abbrvslm", detail);
    window.location.href='voyageDetails';
})

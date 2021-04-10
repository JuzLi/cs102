$(document).ready( function () {
    loadSubscribed();
    loadUnsubscribed();
})

function loadSubscribed(){
  $.ajax({
    url: "/ajax/searchSubscribedAlerts",
    type:"GET",
    dataType:"json",
    success: function(response){
      $(".ajax1").remove();
      $.each(response, function(key,val){
        console.log(val);
        var subrow = '<tr class = "ajax1"><td>' + val +'</td><td><button class="deletePref" value = "' + val + '" type="button">Unsubscribe</button></td></tr>';
        $(subrow).insertAfter("#subscribedAlerts")
      })
    }

  })
}

function loadUnsubscribed(){
  $.ajax({
    url: "/ajax/searchUnsubscribedAlerts",
    type:"GET",
    dataType:"json",
    success: function(response){
      $(".ajax2").remove();
      $.each(response, function(key,val){
        var unsubrow = '<tr class = "ajax2"><td>' + val +'</td><td><button class="createPref" value = "' + val + '" type="button">Subscribe</button></td></tr>';
        $(unsubrow).insertAfter("#unsubscribedAlerts")
      })
    }
  })
}


$(document).on('click','.createPref',function(){
  let vessel = $(this).val();
  let mid = {"alertType" : vessel}
  data = JSON.stringify(mid);
  $.ajax({
    url: "/ajax/createAlertPreference",
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"text",
    success: function(response){
      $(this).parent().parent().remove()
      loadSubscribed();
      loadUnsubscribed();

    }
  })
  
  
})


$(document).on('click','.deletePref',function(){
  let vessel = $(this).val();
  let mid = {"alertType" : vessel}
  data = JSON.stringify(mid);
  $.ajax({
    url: "/ajax/deleteAlertPreference",
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"text",
    success: function(response){
      $(this).parent().parent().remove()
      loadSubscribed();
      loadUnsubscribed();

    }
  })
 
})

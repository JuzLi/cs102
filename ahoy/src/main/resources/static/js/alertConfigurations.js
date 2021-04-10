$(document).ready( function () {
    $.ajax({
      url: "/ajax/searchSubscribedAlerts",
      type:"GET",
      dataType:"json",
      success: function(response){
        $(".ajax1").remove();
        $.each(response, function(key,val){
          console.log(val);
          var subrow = '<tr><td>' + val +'</td><td><button class="deletePref ajax1" value = "' + val + '" type="button">Unsubscribe</button></td></tr>';
          $(subrow).insertAfter("#subscribedAlerts")
        })
      }
  
    })
})

$(document).ready( function () {
  $.ajax({
    url: "/ajax/searchUnsubscribedAlerts",
    type:"GET",
    dataType:"json",
    success: function(response){
      $(".ajax2").remove();
      $.each(response, function(key,val){
        var unsubrow = '<tr><td>' + val +'</td><td><button class="createPref ajax2" value = "' + val + '" type="button">Subscribe</button></td></tr>';
        $(unsubrow).insertAfter("#unsubscribedAlerts")
      })
    }
  })
})


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
      alert("Subscribed!")

    }
  })
  $(this).remove()
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
      alert("Unsubscribed!")

    }
  })
  $(this).remove()
})

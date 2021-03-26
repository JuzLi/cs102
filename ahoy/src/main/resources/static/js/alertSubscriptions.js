$(".submission").click(function(){
    $.ajax({
      url: "/ajax/searchAlerts",
      type:"GET",
      dataType:"json",
      success: function(response){
        $(".ajax").remove();
        $.each(response, function(key,val){
          var card = '<div><button class="createPref ajax" value = "' + val + '" type="button">' + val + '</button></div>';
          $(card).insertAfter("#unsubbedAlerts")
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


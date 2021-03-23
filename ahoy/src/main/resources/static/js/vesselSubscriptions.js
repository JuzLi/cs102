$(".submission").click(function(){
    let searchTerm = $("#vesselSearchTerm").val();
    let  data = {"abbrvslm" : searchTerm}
    data2 = JSON.stringify(data)
    $.ajax({
      url: "/test/5",
      type:"POST",
      data:data2,
      contentType:"application/json; charset=utf-8",
      dataType:"json",
      success: function(response){
        $(".ajax").remove();
        $.each(response, function(key,val){
          var card = '<li class = "ajax" name = ' + val.abbrvslm + '>' + val.abbrvslm + '</li>'
          $(card).insertAfter("#allVesselsLike")
        })
      }
    })


})

$(".createPref").click(function(){
  let vessel = $(this).val();
  let mid = {"abbrvslm" : vessel}
  data = JSON.stringify(mid);
  $.ajax({
    url: "/test/6",
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


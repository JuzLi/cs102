$(".submission").click(function(){
    let searchTerm = $("#vesselSearchTerm").val();
    let  mid = {"abbrvslm" : searchTerm}
    let data = JSON.stringify(mid);
    $.ajax({
      url: "/ajax/searchVessels",
      type:"POST",
      data:data,
      contentType:"application/json; charset=utf-8",
      dataType:"json",
      success: function(response){
        $(".ajax").remove(); 
        $.each(response, function(key,val){
         
         var tablerow = '<tr class = "ajax"><td>' + val.abbrvslm + '</td><td><button class="createPref" value="' + val.abbrvslm + '" type = "button">Subscribe</button></td></tr>';
             
          $(tablerow).insertAfter("#allVesselsLike")
        })
      }
    })


})

$(document).on('click','.createPref',function(){
  let vessel = $(this).val();
  let mid = {"abbrvslm" : vessel}
  data = JSON.stringify(mid);
  $.ajax({
    url: "/ajax/createVesselPreference",
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"text",
    success: function(response){
      alert("Subscribed!")

    }
  })
  $(this).parent().parent().remove()
})


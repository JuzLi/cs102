$(".submission").click(function(){
  console.log("hello");
    var searchTerm = $("#vesselSearchTerm").val();
    alert(searchTerm)
    var  data = {"abbrvslm" : searchTerm}
    data2 = JSON.stringify(data)
    $.ajax({
      url: "/test5",
      type:"POST",
      data:data2,
      contentType:"application/json; charset=utf-8",
      dataType:"json",
      success: function(response){
        console.log(response)
      }
    })


})

// function searchVessels(data){

// //    $.ajax({
// //      url: "/test5",
// //      type: "post",
// //      data: data,
// //      success: function(response) {
// //            console.log(response)
// //      },
// //      error: function(xhr,status, error) {
// //        var err = eval("(" + xhr.responseText + ")")
// //        console.log("error")
// //        console.log(err)
// //    }})
// $.post("http://localhost:8080/test5", data, function(response){
// alert(response)}
// )

    
// }
$("#recoverAccount").click(function(){
    let forgottenEmail = $("#forgottenEmail").val();
    let forgottenUser = $("#forgottenUser").val();
    let mid = {"username": forgottenUser, "email" : forgottenEmail};
    let data = JSON.stringify(mid);        
    $.ajax({
        url: "/ajax/recoverAccount",
        type:"POST",
        data:data,
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        statusCode: {
            200: function(response){
                alert("Temporary Password Sent to Email");
            }
        }
    })
});
    
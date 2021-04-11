$(document).ready(function(){
    $.ajax({
        url: "/ajax/getCurrentUser",
        type:"GET",
        dataType:"json",
        success: function(data,status,xhr){
            $("#exampleInputUsername1").attr('placeholder',data.username);
    }})
})

$("#emailSubmission").click(function(){
    let newEmail = $("#newEmail").val();
    let mid = {"email" : newEmail};
    let data = JSON.stringify(mid);        
    $.ajax({
        url: "/ajax/changeEmail",
        type:"POST",
        data:data,
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        statusCode: {
            200: function(response){
                alert("Email successfully updated!");
            }
        }
    })
});
    


$("#passwordSubmission").click(function(){
    let newPass = $("#newPass").val();
    let confirmPass = $("#confirmPass").val();

    if(newPass == confirmPass){
        let mid = {"password" : newPass};
        let data = JSON.stringify(mid);
        $.ajax({
            url: "/ajax/changePassword",
            type:"POST",
            data:data,
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            statusCode: {
                200: function(response){
                    alert("Password successfully updated!");
                }
            }
        })
    } else{
       alert("Passwords do not match");
    }
})

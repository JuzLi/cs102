$("#vesselFinder").submit(function(){
    var searchTerm = $("#vesselSearchTerm").val();
    alert(searchTerm)
    searchVessels(searchTerm)

})

function searchVessels(vessel){
    var data = JSON.stringify(vessel)
    if (data) {
        $.ajax({
            url : '/test5',
            headers : {
                'Content-Type' : 'application/json'
            },
            method : 'POST',
            dataType : 'json',
            data : data,
            success : function(data) {
                if (data.status == 200) {
                    console.log("Status")
                } 
            },

            error : function(xhr, status, error) {
                // Handle errors here
                console.log("error")
            }
        });
    }
}
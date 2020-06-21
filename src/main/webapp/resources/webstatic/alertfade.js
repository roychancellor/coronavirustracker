/**
 * Causes a Bootstrap alert message to fade after 8000 milliseconds
 */
window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove(); 
    });
}, 8000);
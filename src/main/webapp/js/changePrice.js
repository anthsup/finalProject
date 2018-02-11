$(".input-custom").bind('keyup mouseup', function () {
    var id = $(this).parent().find('#command-id').val();
    $.ajax({
        type: 'POST',
        data: $('.parent-custom'.concat(id)).serialize(),
        url: '/controller',
        success: function(data) {
            var jqObj = jQuery(data);
            $('.total').html(jqObj.find('.total').html());
            $('#subtotal'.concat(id)).html(jqObj.find('#subtotal'.concat(id)).html());
        }
    })
});

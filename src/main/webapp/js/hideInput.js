$("#periodicalType").change(function () {
    var selected_option = $('#periodicalType').val();

    if (selected_option === '1') {
        $('#authors').show();
        $('#booksAmount').show();
    }

    if (selected_option !== '1') {
        $('#authors').hide();
        $('#booksAmount').hide();
    }
});
function changePage(page, periodicalsPerPage) {
    var pageNumber = page || 1;
    // var periodicalsPerPage = 8;

    var command = 'controller?command=periodicals';

    $.ajax({
        type: 'GET',
        url: command + '&page=' + pageNumber + '&periodicalsPerPage=' + periodicalsPerPage,
        success: function (data) {
            var jqObj = jQuery(data);
            var periodicals = jqObj.find(".periodicals").children();
            $(".periodicals").empty();
            $(".periodicals").append(periodicals);
        }
    });
}

$(document).ready(function () {
    var periodicalsPerPage = 8;

    var records = $('#periodicalsNumber').val();
    // periodicalsPerPage = $(this).val();
    var pages = Math.ceil(records / periodicalsPerPage);
    $('#pagination').twbsPagination('destroy');
    $('#pagination').twbsPagination({
            totalPages: pages,
            visiblePages: pages,
            onPageClick: function (event, page) {
                $('.periodicals').html(changePage(page, periodicalsPerPage));
            }
        }
    );
});
function changePage(page, periodicalsPerPage) {
    var pageNumber = page || 1;
    var command = $('#searchCommand').val() === "" ?
        'controller?command=periodicals' : $('#searchCommand').val();

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
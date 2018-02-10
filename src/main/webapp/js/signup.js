var $form = $('#signupForm');

$(document).ready( function () {
    $("#signupForm").validate( {
        rules: {
            login: {
                required: true,
                pattern: /^[a-zA-Z][a-zA-Z0-9-_.]{2,18}$/
            },
            firstName: {
                required: true,
                pattern: /^[A-Z\u0400-\u04ff][a-z\u0400-\u04ff.'-]{2,18}$/
            },
            lastName: {
                required: true,
                pattern: /^[A-Z\u0400-\u04ff][a-z\u0400-\u04ff.'-]{2,18}$/
            },
            email: {
                required: true,
                email: true
            },
            city: {
                required: true,
                pattern: /^[a-zA-Z\u0400-\u04ff]+(?:[\s-][a-zA-Z\u0400-\u04ff]+)*$/
            },
            password: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/
            },
            confirm: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/,
                equalTo: "#password"
            },
            postal: {
                required: true,
                pattern: /^[2][0-9]{5}$/
            },
            address: {
                required: true,
                pattern: /^[a-zA-Z\u0400-\u04ff]+(?:[\s-.,]+[a-zA-Z\u0400-\u04ff0-9]+)*$/
            }
        },
        messages: {
            firstName: "Please enter your valid first name",
            lastName: "Please enter your valid last name",
            login: {
                required: "Please enter a login"
            },
            password: {
                required: "Please provide a password",
                pattern: "Your password must be at least 8 characters long and contain an upper case, lower case and number/Special Char"
            },
            confirm: {
                required: "Please provide a password",
                pattern: "Your password must be at least 8 characters long and contain an upper case, lower case and number/Special Char",
                equalTo: "Please enter the same password as above"
            },
            email: "Please enter a valid email address"
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );
            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents( ".col-sm-5" ).addClass( "has-feedback" );
            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else {
                error.insertAfter( element );
            }
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !element.next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
            }
        },
        success: function ( label, element ) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !$( element ).next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-ok form-control-feedback'></span>" ).insertAfter( $( element ) );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
            $( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );
        },
        unhighlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
            $( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
        }
    });
});

signupFormReady = function() {
    if ($form.find('.login').hasClass("has-success") &&
        $form.find('.email').hasClass("has-success") &&
        $form.find('.firstName').hasClass("has-success") &&
        $form.find('.lastName').hasClass("has-success") &&
        $form.find('.city').hasClass("has-success") &&
        $form.find('.postal').hasClass("has-success") &&
        $form.find('.address').hasClass("has-success") &&
        $form.find('.password').hasClass("has-success") &&
        $form.find('.confirm').hasClass("has-success")) {
        return true;
    } else {
        return false;
    }
};

$form.find('.signup').prop('disabled', true);
var readyInterval = setInterval(function() {
    if (signupFormReady()) {
        $form.find('.signup').prop('disabled', false);
        clearInterval(readyInterval);
    }
}, 250);
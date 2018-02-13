$(document).ready( function () {
    $("#formToValidate").validate( {
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
            },
            photo: {
                url: true
            }
        },
        messages: {
            firstName: {
                pattern: "Имя должно начинаться с большой буквы и быть длиннее 2 символов"
            },
            lastName: {
                pattern: "Фамилия должна начинаться с большой буквы и быть длиннее 2 символов"
            },
            login: {
                pattern: "Логин должен быть длиннее 2ух символов и включать в себя только латинские буквы, цифры и символы - _ ."
            },
            password: {
                pattern: "Ваш пароль должен включать минимум 8 символов и содержать букву верхнего, нижнего регистра и число или специальный символ"
            },
            confirm: {
                pattern: "Ваш пароль должен включать минимум 8 символов и содержать латинскую букву верхнего, нижнего регистра и число или специальный символ"
            },
            address: {
                pattern: "Неправильный формат. Введите адрес в формате 'ул. Чапаева, 32, кв. 228'"
            }
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

$(document).ready( function () {
    $("#changePassword").validate( {
        rules: {
            old_password: "required",
            new_password: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/
            },
            confirm_password: {
                required: true,
                pattern: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/,
                equalTo: "#new_password"
            }
        },
        messages: {
            new_password: {
                pattern: "Ваш пароль должен включать минимум 8 символов и содержать букву верхнего, нижнего регистра и число или специальный символ"
            },
            confirm_password: {
                pattern: "Ваш пароль должен включать минимум 8 символов и содержать латинскую букву верхнего, нижнего регистра и число или специальный символ"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );
            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            element.parents( ".input-group" ).addClass( "has-feedback" );
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
            $( element ).parents( ".input-group" ).addClass( "has-error" ).removeClass( "has-success" );
            $( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );
        },
        unhighlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".input-group" ).addClass( "has-success" ).removeClass( "has-error" );
            $( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
        }
    });
});



$(document).ready(main);

function main() {
	$('.btn-collapse').click(function(e) {
		e.preventDefault();
		var $this = $(this);
		var $collapse = $this.closest('.collapse-group').find('.collapse');
		$collapse.collapse('toggle');
	});
}

//Wait for the DOM to be ready
$(function() {
	// Initialize form validation on the registration form.
	// It has the name attribute "contactForm"
	$("form[name='contactForm']").validate({
		// Specify validation rules
		rules : {
			// The key name on the left side is the name attribute
			// of an input field. Validation rules are defined
			// on the right side
			firstName : "required",
			lastName : "required",
			feedback : {
				required : true,
				maxlength : 20
			},
			email : {
				required : true,
				email : true
			// Specify that email should be validated by the built-in "email" rule
			},
			password : {
				required : true,
				minlength : 5
			}
		},
		// Specify validation error messages
		messages : {
			firstName : "Please enter your First Name",
			lastName : "Please enter your Last Name",
			password : {
				required : "Please provide a password",
				minlength : "Your password must be at least 5 characters long"
			},
			email : "Please enter a valid email address"
		},
		// Make sure the form is submitted to the destination defined
		// in the "action" attribute of the form when valid
		submitHandler : function(form) {
			form.submit();
		}
	});

	$("form[name='forgotPasswordForm']").validate({
		// Specify validation rules
		rules : {
			email : {
				required : true,
				email : true
			// Specify that email should be validated by the built-in "email" rule
			}
		},
		// Specify validation error messages
		messages : {
			email : "Please enter a valid email address"
		},
		// Make sure the form is submitted to the destination defined
		// in the "action" attribute of the form when valid
		submitHandler : function(form) {
			form.submit();
		}
	});
	
	
	$("form[name='loginForm']").validate({
		// Specify validation rules
		rules : {
			username : {
				required : true,
				minlength : 5
			},
			password : {
				required : true,
				minlength : 5
			}
	        
		},
		// Specify validation error messages
		messages : {
			password : "Please enter a valid password with min length of 5 characters",
			username : "Please enter a valid username"
		},
		// Make sure the form is submitted to the destination defined
		// in the "action" attribute of the form when valid
		submitHandler : function(form) {
			form.submit();
		}
	});
	
	$("form[name='contactForm']").validate({
		// Specify validation rules
		rules : {
			userName : {
				required : true
			},
			password : {
				required : true,
				minlength : 5
			}
		},
		// Specify validation error messages
		messages : {
			userName : "Please enter your User Name",
			password : {
				required : "Please provide a password",
				minlength : "Your password must be at least 5 characters long"
			}
		},
		// Make sure the form is submitted to the destination defined
		// in the "action" attribute of the form when valid
		submitHandler : function(form) {
			form.submit();
		}
	});
	
	
	/* Signup form validation */
    $( "form[name='signupForm']" ).validate({
               
    	rules : {
    	
    		email : {
				required : true
			},
    		
			username : {
				required : true
			},
			
			password : {
				required : true,
				minlength : 6
			},
			
			confirmPassword : {
				required : true,
				minlength : 6,
				equalTo : "#password"
			},
			
			firstName : {
				required : true
			},
			
			lastName : {
				required : true
			},
			
			cardNumber : {
				required : true
			},
			
			cardCode : {
				required : true
			},
			
			description : {
				required : true,
				min: 0,
                max: function (value, validator, $field) {
                    return 300 - (value.match(/\r/g) || []).length;
                }
			},
		},
    	
		messages : {
			email: "The email is required",
			username : "The username is required",
			password : {
				required : "Please provide a password",
				minlength : "Your password must be at least 6 characters long"
			},
			confirmPassword : "The confirmation password must match exactly the password field",
			firstName : "The first name is required",
			lastName : "The last name is required",
			description: "Post content must be less than 300 characters",
			cardNumber: "Card Number is required",
			cardCode: "Card Code is required"
		},
		
		// Make sure the form is submitted to the destination defined
		// in the "action" attribute of the form when valid
		submitHandler : function(form) {
			form.submit();
		}
		
		
		
//		    phoneNumber: {
//                    validators: {
//                        notEmpty: {
//                            message: 'The phone number is required'
//                        },
//                        phone: {
//                            country: 'country',
//                            message: 'The value is not valid %s phone number'
//                        }
//                    }
//                }
//            }
//        })
//        // Revalidate phone number when changing the country
//        .on('change', '[name="country"]', function(e) {
//            $('#signupForm').formValidation('revalidateField', 'phoneNumber');
            
            
        });
	
	

});

////Wait for the DOM to be ready
//$(function() {
//  // Initialize form validation on the registration form.
//  // It has the name attribute "registration"
//  $("form[name='registration']").validate({
//    // Specify validation rules
//    rules: {
//      // The key name on the left side is the name attribute
//      // of an input field. Validation rules are defined
//      // on the right side
//      firstName: "required",
//      lastName: "required",
//      feedback: {
//          required: true,
//          maxlength: 20
//      },  
//      email: {
//        required: true,
//        email: true // Specify that email should be validated by the built-in "email" rule
//      },
//      password: {
//        required: true,
//        minlength: 5
//      }
//    },
//    // Specify validation error messages
//    messages: {
//      firstName: "Please enter your First Name",
//      lastName: "Please enter your Last Name",
//      password: {
//        required: "Please provide a password",
//        minlength: "Your password must be at least 5 characters long"
//      },
//      email: "Please enter a valid email address"
//    },
//    // Make sure the form is submitted to the destination defined
//    // in the "action" attribute of the form when valid
//    submitHandler: function(form) {
//      form.submit();
//    }
//  });
//});

//function isAuthenticated() {
//	return true;
//}
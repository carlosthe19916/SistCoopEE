/*
Theme Name: Abyss
Description: Responsive One Page Template
Author: FeelgoodThemes
Theme URI: https://googledrive.com/host/0B5mPGF72ukWESHZOMW5CanpiWmc/
Version: 1.0
*/

/* ------------------------------------------------------------------------ */
/*	BOOTSTRAP FIX FOR WINPHONE 8 AND IE10
/* ------------------------------------------------------------------------ */
if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
  	var msViewportStyle = document.createElement("style")
  	msViewportStyle.appendChild(
    	document.createTextNode(
      		"@-ms-viewport{width:auto!important}"
    	)
  	)
  	document.getElementsByTagName("head")[0].appendChild(msViewportStyle)
}

function detectIE() {
	if ($.browser.msie && $.browser.version == 9) {
		return true;
	}
	if ($.browser.msie && $.browser.version == 8) {
		return true;
	}
	return false;
}

function getWindowWidth() {
    return Math.max( $(window).width(), window.innerWidth);
}

function getWindowHeight() {
    return Math.max( $(window).height(), window.innerHeight);
}


// BEGIN WINDOW.LOAD FUNCTION
$(window).load(function() {

	/* ------------------------------------------------------------------------ */
	/*	PRELOADER
	/* ------------------------------------------------------------------------ */
	var preloaderDelay = 350,
        preloaderFadeOutTime = 800;

    function hidePreloader() {
        var loadingAnimation = $('#loading-animation'),
            preloader = $('#preloader');

        loadingAnimation.fadeOut();
        preloader.delay(preloaderDelay).fadeOut(preloaderFadeOutTime);
    }

    hidePreloader();

});

//BEGIN DOCUMENT.READY FUNCTION
jQuery(document).ready(function($) {

	$.browser.chrome = $.browser.webkit && !!window.chrome;
	$.browser.safari = $.browser.webkit && !window.chrome;

	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		$('body').addClass('mobile');
	}
	
	if ($.browser.chrome) {
		$('body').addClass('chrome');
	}
	
	if ($.browser.safari) {
		$('body').addClass('safari');
	}
	
	
	/* ------------------------------------------------------------------------ */
	/*	REFRESH WAYPOINTS
	/* ------------------------------------------------------------------------ */
	function refreshWaypoints() {
		setTimeout(function() {
			$.waypoints('refresh');
		}, 1000);
	}
	

	/* ------------------------------------------------------------------------ */
	/*	ANIMATED ELEMENTS
	/* ------------------------------------------------------------------------ */
	if ( !$('body').hasClass('mobile') ) {

		$('.animated').appear();

		if( detectIE() ) {
			$('.animated').css({
				'display':'block',
				'visibility': 'visible'
			});
		}
		else {
			$('.animated').on('appear', function() {
				var elem = $(this);
				var animation = elem.data('animation');
				if ( !elem.hasClass('visible') ) {
					var animationDelay = elem.data('animation-delay');
					if ( animationDelay ) {
						setTimeout(function(){
							elem.addClass( animation + " visible" );
						}, animationDelay);
					}
					else {
						elem.addClass( animation + " visible" );
					}
				}
			});
			
			/* Starting Animation on Load */
			$(window).load(function() {
				$('.onstart').each( function() {
					var elem = $(this);
					if ( !elem.hasClass('visible') ) {
						var animationDelay = elem.data('animation-delay');
						var animation = elem.data('animation');
						if ( animationDelay ) {
							setTimeout(function(){
								elem.addClass( animation + " visible" );
							}, animationDelay);
						}
						else {
							elem.addClass( animation + " visible" );
						}
					}
				});
			});	
			
		}

	}
	

	/* ------------------------------------------------------------------------ */
	/*	SMOOTH SCROLL
	/* ------------------------------------------------------------------------ */
    var scrollAnimationTime = 1500,
        scrollAnimation = 'easeInOutCubic';

    $('a.scrollto').bind('click.smoothscroll', function (event) {
        event.preventDefault();

        var target = this.hash;

        $('html, body').stop().animate({
            'scrollTop': $(target).offset().top-40
        }, scrollAnimationTime, scrollAnimation, function() {
            history.pushState ? history.pushState(null, null, target) : window.location.hash = target;
        });

    });
	
	
	/* ------------------------------------------------------------------------ */
	/*	NAVIGATION
	/* ------------------------------------------------------------------------ */
	function scrollNav() {
 
        var config = {
            $sections : $( 'section.on-menu' ),
            $navlinks : $( '#site-nav ul li' ),
            currentLink : 0,
            $body : $( 'html, body' ),
            animspeed : 1500,
            animeasing : 'easeInOutCubic'
        };

        function init() {

            if( $('#site-nav ul').hasClass('nav-disabled') ) {
                return false;
            }

            config.$navlinks.each(function(index){
                $(this).find('a').on('click', function() {
                    scrollAnim( config.$sections.eq(index).offset().top, this.hash );
                    return false;
                });
            });

			$('#back-to-top a, .back-to-top a').on('click', function() {
				scrollAnim( 40, this.hash );
				return false;
			});

            config.$sections.waypoint( function( direction ) {
                if( direction === 'down' ) { changeNav( $( this ) ); }
            }, { offset: '30%' } ).waypoint( function( direction ) {
                if( direction === 'up' ) { changeNav( $( this ) ); }
            }, { offset: '-30%' } );

            $( window ).on('debouncedresize', function() {
                scrollAnim( config.$sections.eq( config.currentLink ).offset().top );
            } );

        }

        function changeNav( $section ) {
            config.$navlinks.eq( config.currentLink ).removeClass( 'active' );
            config.currentLink = $section.index( 'section.on-menu' );
            config.$navlinks.eq( config.currentLink ).addClass( 'active' );
        }

        function scrollAnim( top, hash) {
            config.$body.stop().animate( { scrollTop : top-40 }, config.animspeed, config.animeasing, function(event) {
            	if (history.pushState) {
				    history.pushState(null, null, hash);
				}
				else {
                	window.location.hash = hash;
                }
            });
        }

        init();

    }

    scrollNav();


	/* ------------------------------------------------------------------------ */
	/*	SCROLL ACTIONS
	/* ------------------------------------------------------------------------ */
	$(window).scroll(function() {
		if ($(this).scrollTop() > 100) {
			$("#back-to-top").fadeIn(500);
			if ($('#main-header').data('style') == 'light') {
				$("#main-header").removeClass("nav-light");
			}
		}
		else {
			$("#back-to-top").fadeOut(500);
			if ($('#main-header').data('style') == 'light') {
				$("#main-header").addClass("nav-light");
			}
		}
	});
	
	
	/* ------------------------------------------------------------------------ */
	/*	HOME
	/* ------------------------------------------------------------------------ */	
	function setHomeHeight() {
		var home = $('#home');
			
		if (home.hasClass('fullscreen')) {
			home.height(getWindowHeight());
		}
	}

	setHomeHeight();

	$(window).on('resize',function() {
		setHomeHeight();
	});

	function initHomeSection() {
		if ($('body').hasClass('image-background')) {
			$('body').backstretch([
				'img/bg1.jpg'
			]);
		}
		else if ($('body').hasClass('slide-background')) {
			$('body').backstretch([
				'img/bg3.jpg',
				'img/bg2.jpg',
				'img/bg1.jpg',
			], {duration: 5000, fade: 4000});
		}
		else if ($('body').hasClass('video-background')) {
			if (!Modernizr.touch) {
				var videobackground = new $.backgroundVideo($('body'), {
					"align": "centerXY",
					"width": 1280,
					"height": 720,
					"path": "video/",
					"filename": "clouds",
					"types": ["webm","mp4"]
				});
			}
		}
	}

	initHomeSection();

	
	/* ------------------------------------------------------------------------ */
	/*	Custom Isotope for Centered Portfolio
	/* ------------------------------------------------------------------------ */
	$.Isotope.prototype._getMasonryGutterColumns = function() {
		var gutter = this.options.masonry.gutterWidth || 0;
		containerWidth = this.element.parent().width();
		this.masonry.columnWidth = this.options && this.options.masonry.columnWidth ||
			this.$filteredAtoms.outerWidth(true) ||
			containerWidth;
		this.masonry.columnWidth += gutter;
		this.masonry.cols = Math.floor(containerWidth / this.masonry.columnWidth);
		this.masonry.cols = Math.max(this.masonry.cols, 1);
	};
	 
	$.Isotope.prototype._masonryReset = function() {
		this.masonry = {};
		this._getMasonryGutterColumns();
		var i = this.masonry.cols;
		this.masonry.colYs = [];
		while (i--) {
			this.masonry.colYs.push( 0 );
		}
	};
	 
	$.Isotope.prototype._masonryResizeChanged = function() {
		var prevColCount = this.masonry.cols;
		this._getMasonryGutterColumns();
		return ( this.masonry.cols !== prevColCount );
	};
	 
	$.Isotope.prototype._masonryGetContainerSize = function() {
		var gutter = this.options.masonry.gutterWidth || 0;
		var unusedCols = 0,
			i = this.masonry.cols;
		while ( --i ) {
			if ( this.masonry.colYs[i] !== 0 ) {
				break;
			}
		  unusedCols++;
		}
		return {
			height : Math.max.apply( Math, this.masonry.colYs ),
			width : ((this.masonry.cols - unusedCols) * this.masonry.columnWidth) - gutter
		};
	};
	
	
	/* ------------------------------------------------------------------------ */
	/*	PORTFOLIO
	/* ------------------------------------------------------------------------ */
	function layoutPortfolio() {
	
		var $portfolioContainer = $('.portfolio-container');
		var $container = $('.portfolio'),
			$body = $('body'),
			columns = null;

		$('.portfolio-container .container').css('width','100%' );
		$('.portfolio-container .container').css('padding','0' );

		function getColumnNumber() { 
			var portfolioWidth = $portfolioContainer.width(), 
				columnNumber = 1;

			if (portfolioWidth > 1199) {
				columnNumber = 3;
			} else if (portfolioWidth > 980) {
				columnNumber = 3;
			} else if (portfolioWidth > 600) {
				columnNumber = 2;
			} else if (portfolioWidth > 240) {
				columnNumber = 1;
			}
			
			return columnNumber;
		}
		
		function setColumns() {
			var portfolioWidth = $portfolioContainer.width(), 
				columnNumber = getColumnNumber(), 
				itemWidth = Math.floor(portfolioWidth / columnNumber);
			
			$container.find('.portfolio-item').each(function() { 
				$(this).css( { 
					width : itemWidth + 'px' 
				});
			});
		}
		
		function setPortfolio() { 
			setColumns();
			$container.isotope('reLayout');
		}

		var portfolioWidth = $portfolioContainer.width(),
			columnNumber = getColumnNumber(),
			colWidth = Math.floor(0 +  (portfolioWidth / columnNumber));
			
		$container.isotope({
			filter: '*',
			animationEngine: 'best-available',
			resizable: false,
			itemSelector : '.portfolio-item',
			layoutMode: 'masonry',
			animationOptions: {
				duration: 750,
				easing: 'linear',
				queue: false
			},
			masonry : {
				columnWidth : colWidth
			},
		}, refreshWaypoints());
		
		$container.waitForImages(function () { 
			setPortfolio();
		});

		$(window).on('resize', function () { 
			setPortfolio();          
		});
		
		$('nav.portfolio-filter ul a').on('click', function() {
			var selector = $(this).attr('data-filter');
			$container.isotope({ filter: selector }, refreshWaypoints());
			$('nav.portfolio-filter ul a').removeClass('active');
			$(this).addClass('active');
			return false;
		});
	}
	
	layoutPortfolio();
	
	$(window).on('resize', function () { 
		layoutPortfolio();        
	});


	/* ------------------------------------------------------------------------ */
	/*	CHARTS
	/* ------------------------------------------------------------------------ */
	function initPieCharts() {
		$('.chart').easyPieChart({
			size : 160,
			animate : 2000,
			lineWidth : 4,
			lineCap : 'square',
			barColor : '#5AA9CE',
			trackColor : '#eeeeee',
			scaleColor : false
		});
	}
	initPieCharts();


	/* ------------------------------------------------------------------------ */
	/*	TESTIMONIALS
	/* ------------------------------------------------------------------------ */
    $('.testimonials-slider').flexslider({
		animation: "slide",
		directionNav: true,
		controlNav: true,
		slideshowSpeed: 5000,
		animationSpeed: 1500,
		initDelay: 0,
		smoothHeight: true
	});
	
	
	/* ------------------------------------------------------------------------ */
	/*	MAP
	/* ------------------------------------------------------------------------ */
	$(window).load(function(){
		//Google Map					
		var latlng = new google.maps.LatLng(40.7104399,-74.0052632);
		var settings = {
			zoom: 14,
			center: new google.maps.LatLng(40.7104399,-74.0052632), mapTypeId: google.maps.MapTypeId.ROADMAP,
			mapTypeControl: false,
			scrollwheel: false,
			draggable: true,
			mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
			navigationControl: false,
			navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL},
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		
			
		var map = new google.maps.Map(document.getElementById("map_canvas"), settings);
		
		google.maps.event.addDomListener(window, "resize", function() {
			var center = map.getCenter();
			google.maps.event.trigger(map, "resize");
			map.setCenter(center);
		});
		
		var contentString = '<div id="content">'+
			'<div id="siteNotice">'+
			'</div>'+
			'<h3 id="firstHeading" class="firstHeading">Abyss</h3>'+
			'<div id="bodyContent">'+
			'<p>Here we are. Come to drink a coffee!</p>'+
			'</div>'+
			'</div>';
		var infowindow = new google.maps.InfoWindow({
			content: contentString
		});
		
		var companyImage = new google.maps.MarkerImage('img/marker.png',
			new google.maps.Size(32,47),<!-- Width and height of the marker -->
			new google.maps.Point(0,0),
			new google.maps.Point(35,20)<!-- Position of the marker -->
		);
		
		
		var companyPos = new google.maps.LatLng(40.7104399,-74.0052632);
		
		var companyMarker = new google.maps.Marker({
			position: companyPos,
			map: map,
			icon: companyImage,               
			title: "Abyss",
			zIndex: 3
		});
		
		
		google.maps.event.addListener(companyMarker, 'click', function() {
			infowindow.open(map,companyMarker);
		});
	});


	/* ------------------------------------------------------------------------ */
	/*	SIMPLE TEXT ROTATOR
	/* ------------------------------------------------------------------------ */
	function initTextRotator(){
		$('.textrotator').textrotator({
			animation: 'fade',
			speed: 2500
		});
	}
	initTextRotator();
	
	
	/* ------------------------------------------------------------------------ */
	/*	MAGNIFIC POPUP
	/* ------------------------------------------------------------------------ */
    function initMagnificPopup(){
		$('.portfolio-item > a').magnificPopup({
			type: 'image'
		});
	}
	initMagnificPopup();


	/* ------------------------------------------------------------------------ */
	/*	FLEXSLIDER
	/* ------------------------------------------------------------------------ */
    function initFlexSlider(){
        $('.flexslider').flexslider({
            animation: 'slide',
            directionNav: false,
            slideshowSpeed: 6000,
            animationSpeed: 1200
        });
    }
    initFlexSlider();
	
	
	/* ------------------------------------------------------------------------ */
	/*	FITVIDS
	/* ------------------------------------------------------------------------ */
    function initFitVids(){
        $('.video-container').fitVids();
    }
    initFitVids();	
	

});
//END DOCUMENT.READY FUNCTION


/* ------------------------------------------------------------------------ */
/*	CONTACT FORM
/* ------------------------------------------------------------------------ */
function initContactForm() {

	var scrollElement = $('html,body'),
		contactForm = $('.contact-form');

	contactForm.on('submit', function() {

		var requiredFields = $(this).find('.required'),
			formData = contactForm.serialize(),
			formAction = $(this).attr('action'),
			formSubmitMessage = $('.response-message');

		requiredFields.each(function() {

			if( $(this).val() == "" ) {

				$(this).addClass('input-error');

			} else {

				$(this).removeClass('input-error');
			}

		});

		function validateEmail(email) { 
			var exp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return exp.test(email);
		}

		var emailField = $('.contact-form-email');

		if( !validateEmail(emailField.val()) ) {

			emailField.addClass('input-error');

		}

		if ($('.contact-form :input').hasClass('input-error')) {
			return false;
		} else {

			$.post(formAction, formData, function(data) {
				formSubmitMessage.html(data);

				requiredFields.val('');

				setTimeout(function() {
					formSubmitMessage.slideUp();
				}, 5000);
			});

		}

		return false;

	});

}


/* ------------------------------------------------------------------------ */
/*	REFRESH WAYPOINTS FUNCTION
/* ------------------------------------------------------------------------ */
function waypointsRefresh(){
	setTimeout(function(){
		$.waypoints('refresh');
	},1000);
} 


$(window).load(function() {
    $(window).trigger( 'hashchange' );
});


$(document).ready(function() {
    initContactForm();
});

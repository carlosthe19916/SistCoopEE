(function($) {

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


	$(document).ready(function() {

		var demoSwitcher = $("#demo-switcher"),
			demoSwitcherToggle = demoSwitcher.find(".demo-toggle"),
			demoSwitcherWidth = demoSwitcher.outerWidth(),
			demoSwitcherAnimationTime = 600,
			demoSwitcherAnimationEasing,
			demoSwitcherActiveClass = "switcher-active";

		demoSwitcher.css({ left : '-' + demoSwitcherWidth + 'px' });

		demoSwitcherToggle.on('click', function(event) {

			event.preventDefault();

			if( demoSwitcher.hasClass(demoSwitcherActiveClass) ) {

				demoSwitcher.stop().animate({ left : '-' + demoSwitcherWidth + 'px' }, demoSwitcherAnimationTime, demoSwitcherAnimationEasing).removeClass(demoSwitcherActiveClass);

			} else {

				demoSwitcher.stop().animate({ left : 0 }, demoSwitcherAnimationTime, demoSwitcherAnimationEasing).addClass(demoSwitcherActiveClass);

			}

		});

		/* ==============================================
		Custom Theme 
		=============================================== */

		/*$(".home-set-default").on('click', function(event) {

			event.preventDefault();
			
			$("#home").removeClass('fullscreen')

			if ($(window).width() < 768) {
				$("#home, .home-heading, .home-img").animate({ height:'400px' });
			}
			else if ($(window).width() >= 768 && $(window).width() < 992) {
				$("#home, .home-heading, .home-img").animate({ height:'500px' });
			}
			else if ($(window).width() >= 992 && $(window).width() < 1200) {
				$("#home, .home-heading, .home-img").animate({ height:'600px' });
			}
			else if ($(window).width() >= 1200) {
				$("#home, .home-heading, .home-img").animate({ height:'700px' });
			}
			
    		$(window).trigger('resize');

    		$(".home-set-fullscreen").removeClass('demo-active');

    		$(this).addClass('demo-active');

		});

		$(".home-set-fullscreen").on('click', function(event) {

			event.preventDefault();
			
			$("#home").addClass('fullscreen');

			$("#home, .home-heading, .home-img").animate({ height: ($(window).height()) }, function() {

				$(window).trigger('resize');

			});
			
			$(".home-set-default").removeClass('demo-active');

			$(this).addClass('demo-active');

		});*/


		$(".background-set-image").on('click', function(event) {

			event.preventDefault();

			if ($('body').hasClass('video-background')) {
				$('#video_background').fadeOut(2000, function() {
					$(this).remove();
				});
			}
			
			$("body").removeClass('slide-background video-background').addClass('image-background');

    		$(".background-set-slide").removeClass('demo-active');
    		$(".background-set-video").removeClass('demo-active');

    		$(this).addClass('demo-active');

			initHomeSection();

		});

		$(".background-set-slide").on('click', function(event) {

			event.preventDefault();

			if ($('body').hasClass('video-background')) {
				$('#video_background').fadeOut(2000, function() {
					$(this).remove();
				});
			}
			
			$("body").removeClass('image-background video-background').addClass('slide-background');

    		$(".background-set-image").removeClass('demo-active');
    		$(".background-set-video").removeClass('demo-active');

    		$(this).addClass('demo-active');

			initHomeSection();

		});

		$(".background-set-video").on('click', function(event) {

			event.preventDefault();
			
			$("body").removeClass('image-background slide-background').addClass('video-background');

    		$(".background-set-image").removeClass('demo-active');
    		$(".background-set-slide").removeClass('demo-active');

    		$(this).addClass('demo-active');

			initHomeSection();

		});
		

		$('.template-set-color a').on('click', function(event) {

			event.preventDefault();

			var cssFile = $(this).data('color');	

			$('#demo-color-css').attr('href', 'css/color-switcher/' + cssFile + '.css');

			$('.skills .row').empty().html('<div class="col-sm-6 col-md-3">\
					<div class="skill-item">\
						<div class="chart easyPieChart" data-percent="73">\
							<span class="percent">73</span>\
						</div>\
						<div class="skill_desc">\
							<p class="lead">HTML5</p>\
							<p>Lorem ipsum dolor sit amet consectetur adipiscing elit nunc in ipsum.</p>\
						</div>\
					</div>\
				</div><!-- END COLUMN 1 -->\
				<div class="col-sm-6 col-md-3">\
					<div class="skill-item">\
						<div class="chart easyPieChart" data-percent="85">\
							<span class="percent">85</span>\
						</div>\
						<div class="skill_desc">\
							<p class="lead">CSS3</p>\
							<p>Suspendisse vel eros lorem. Nunc facilisis enim in sapien volutpat tincidunt.</p>\
						</div>\
					</div>\
				</div><!-- END COLUMN 2 -->\
				<div class="col-sm-6 col-md-3">\
					<div class="skill-item">\
						<div class="chart easyPieChart" data-percent="92">\
							<span class="percent">92</span>\
						</div>\
						<div class="skill_desc">\
							<p class="lead">jQuery</p>\
							<p>Lorem ipsum dolor sit amet consectetur adipiscing elit nunc in ipsum.</p>\
						</div>\
					</div>\
				</div><!-- END COLUMN 3 -->\
				<div class="col-sm-6 col-md-3">\
					<div class="skill-item no-bottom-margin">\
						<div class="chart easyPieChart" data-percent="59">\
							<span class="percent">59</span>\
						</div>\
						<div class="skill_desc">\
							<p class="lead">PHP</p>\
							<p>Suspendisse vel eros lorem. Nunc facilisis enim in sapien volutpat tincidunt.</p>\
						</div>\
					</div>\
				</div><!-- END COLUMN 4 -->\
			');
			
			var newBarColor = $(this).data('color-code');
			var pieChartClass = 'chart';

		    function initPieCharts() {
		        var chart = $('.' + pieChartClass);

		        chart.appear();

		        chart.each(function() {

		            var $this = $(this);

		            if( !$this.hasClass('pie-chart-loaded') ) {
		                $this.easyPieChart({
		                    size : 160,
							animate : 2000,
							lineWidth : 4,
							lineCap : 'square',
							barColor : newBarColor,
							trackColor : '#eeeeee',
							scaleColor : false
		                }).addClass('pie-chart-loaded');
		            }
		        });
		        
		    }

		    initPieCharts();
			
	    });	
		
	});

})(jQuery);

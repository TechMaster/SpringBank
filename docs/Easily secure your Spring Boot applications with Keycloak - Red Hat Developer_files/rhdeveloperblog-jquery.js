// RH DEVELOPER BLOG SPECIFIC SCRIPTS – THESE FUNCTION IN ADDITION TO THE EXISTING FRAMEWORK FOR THE RH DEV. BLOG
// INIT FUNCTIONS AT THE BOTTOM

// Footer collapsible section control
$(function() {
    $(".menu-item--expanded h3").on("click", function(){
        var windowsize = document.body.clientWidth;
        if (windowsize <= 1170) {
            $(this).parent().toggleClass("collapsed");
        } else {
            return false;
        }
    });
});


// Footer collapsible section control width checker
function checkWidth() {
    var windowsize = document.body.clientWidth;

    if (windowsize <= 1170) {
        $(".menu-item--expanded").addClass("collapsed");
    }else {
        $(".menu-item--expanded").removeClass("collapsed");
    }
}
// Run function on resize
checkWidth();
$(window).on("resize", checkWidth);





function hideShowArrows() {
    var currentSlide = $('.rhd-topic-nav').slick('slickCurrentSlide');
    if (currentSlide === 0) {
        $('.rhd-topic-header-inner .previous-arrow').css('display', 'none');
    }
    else {
        $('.rhd-topic-header-inner .next-arrow').css('display', 'block');
        $('.rhd-topic-header-inner .previous-arrow').css('display', 'block');
        var slick = $('.rhd-topic-nav').slick('getSlick');
        var total = slick.$slides.length - 1;
        var last = slick.$slides[total];
        if ($(last).hasClass('slick-active')) {
            $('.rhd-topic-header-inner .next-arrow').css('display', 'none');
        }
    }
}

// Slick function slick slide control
function slickNav()  {
    $('.rhd-topic-nav').slick({
        infinite: false,
        slidesToShow: 4,
        slidesToScroll: 1,
        variableWidth: true,
        prevArrow: $('.rhd-topic-header-inner .previous-arrow'),
        nextArrow: $('.rhd-topic-header-inner .next-arrow'),
    });

    hideShowArrows();

    $('.rhd-topic-header-inner .slider-arrow').click(function() {
        hideShowArrows();
    });
    $(window).resize(function(){
        hideShowArrows();
    });
}


// This function manages all the event actions to display and hide the search bar in navigation
function searchToggle() {

    var toggleSearch = function () {
        $('.rhd-header .rhd-nav-search').toggleClass('open');
        $('.rhd-header').toggleClass('rhd-search-open');
        $('.rhd-search-toggle-overlay').toggle('open');
    };
    $('.rhd-search-toggle').click(function() {
        toggleSearch();
    });
    $('.rhd-search-toggle-overlay').click(function(){
        toggleSearch();
    });

}



// Mobile Menu Control
function mobileMenu() {
    $('.menu-toggle').click(function() {
        $('body').toggleClass('mobile-tray-open');
        $('.rhd-mobile-tray').slideToggle(250);
    });

    // Control Overlay
    $('.rhd-mobile-overlay').click(function() {
        $('body').toggleClass('mobile-tray-open');
        $('.rhd-mobile-tray').slideToggle(250);
    });
}



// Find any <img> element in posts with 'data-add-featherlight' parameter and create a display enlargement trigger
function featherlightScriptAugment() {
    // Find any img with 'data-add-featherlight' parameter and make sure its parent isn't an <a> element
    $('img[data-add-featherlight]').each(function(){
        // Determine parent type. If it's already an <a> element, don't do anything
        if(!$(this).parent().is('a')) {
            // If the parent type isn't already a, create the data-featherlight link
            var img = $(this).clone();

            var imgA = $('<a/>');
            imgA.attr('data-featherlight','image');

            imgA.attr('href',$(this).attr('data-add-featherlight'));
            imgA.html(img);

            $(this).replaceWith(imgA);
        }
    });
}


// Finds any code sample HTML tags in the entry content and adds a 'notranslate' tag for the Google Translate plugin
function setCodeToNoTranslate() {
    var noTranslateClass = 'notranslate';
    // Adds class to inline code samples (<code>...sample...</code>)
    $('code').addClass(noTranslateClass);
    // Adds class to any Syntax Highlighter code blocks
    $('.syntaxhighlighter').addClass(noTranslateClass);
    // Adds class name to <pre>-type code blocks
    $('pre').addClass(noTranslateClass);

}


// Sets the width of the sidebar elements for waypoints.js -- used to maintain its width once the sidebar is set to position:fixed
/*function establishSidebarWidth(tar) {
    var w = $(tar).parent().width();
    // Assign width to element
    $(tar).css({'width':w + 'px'});
}*/

// Adds a waypoint to pause the vertical scrolling bar
/*
function addSideWaypoint() {
    // Determine height comparison to make sure the sticky waypoint can/should be added
    var sticky = new Waypoint.Sticky({
        element: $('.sidebar-non-author-page div.vert-container')[0],
        offset: 30
    });
}
*/

// Adds an external link icon to any link that shows up in a numbered or ordered list (<ul> or <ol>) in articles
function addExternalLinkToList() {
    // External Link Check in links within <li> elements
    $('.entry-content').children().find('li > a').each(function() {
        // Retrieve URL
        var urlstr = $(this).attr('href');
        var rhd = 'redhat.com';
        var check  = urlstr.indexOf(rhd);
        if( check === -1 && urlstr !== '#' && $(this).parent().hasClass('addtoany_list') !== true) {
            var spanExt = $('<span/>');
            spanExt.attr('class','far fa-external-link');
            spanExt.text(' ');
            $(this).append(spanExt);
        }
    });
}


// Adds a PDF icon to any PDF link in RHD blog in .entry-content (anything in an article's content)
function addPdfIcon() {
    $('.entry-content').children().find('a').each(function() {
        // Determine if the last 4 characters of the URL attribute are .pdf. If so, add the icon for Font Awesome pdf
        var urlstr = $(this).attr('href');

        // PDF Link Check
        if(urlstr.slice(-4) === '.pdf') {
            $(this).addClass('in-body-pdf');
            $(this).attr('target','_blank');
            var spanPdf = $('<span/>');
            spanPdf.attr('class','fa fa-file-pdf-o');
            $(this).append(spanPdf);
        }
    });
}


// This function finds the featured image for a given post and duplicates it to below the entry header for mobile presentation
function featuredImgPresentation() {
    $('article').each(function() {
        if($(this).children().find('.featured-post-image').length) {
            // If the featured image is present, then copy a version of it to a mobile friendly view.
            var imgDiv = $('<div />');
            imgDiv.attr('class','featured-img-vert-div');
            // Insert div container
            $(this).children().find('.entry-meta').after(imgDiv);

            // Insert image object
            var origPLink   = $(this).children().find('.featured-post-image-permalink').attr('href');

            // Now retrieve origignally rendered featured image src for the article.
            var origFeatImgSrc = $(this).children().find('.featured-post-image').attr('src');

            // Create new image DOM object
            var dupeImg = $('<img />');
            dupeImg.attr('class','featured-img-mobile');
            dupeImg.attr('src',origFeatImgSrc);

            // Insert into new div
            if(origPLink.length) {
                var dupeA   = $('<a />');
                dupeA.attr('href',origPLink);
                dupeA.attr('class','featured-post-image-mobile-permalink');
                // Insert into div
                $(this).children().find('.featured-img-vert-div').prepend(dupeA);
                $(this).children().find('.featured-post-image-mobile-permalink').prepend(dupeImg);
            } else
            if(!origPLink.length) {
                $(this).children().find('.featured-img-vert-div').prepend(dupeImg);
            }
        }
    });
}



// INIT
$(function(){
    // This creates a dropdown version for display at mobile device breakpoints.

    // Check for featured images and init image duplication if necessary
    featuredImgPresentation();

    // Initializes the login for the Developers main site.
    rhBlogSSO();

    // General post content check (archives and individual post pages)
    if($('.entry-content').length) {
        addPdfIcon();
        addExternalLinkToList();

        // Init function to find code samples and add a 'notranslate' class name
        setCodeToNoTranslate();
    }


    // Individual post content check
    if($('.standard-post').length) {
        // Check for the Featherlight tag and run tag placement script if present
        if($('img[data-add-featherlight]').length) {
            featherlightScriptAugment();
        }
    }



    // Set mobile menu control (2018 site version)
    mobileMenu();

    // Header search animation
    searchToggle();

    // Slick nav control in mobile
    slickNav();

});


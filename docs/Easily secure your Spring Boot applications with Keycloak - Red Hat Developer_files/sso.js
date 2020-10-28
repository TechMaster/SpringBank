var ssoConfig = {};

ssoConfig.AccountUrl = 'https://sso.redhat.com/auth/realms/redhat-external/account/';
ssoConfig.AuthUrl = 'https://sso.redhat.com/auth/';
ssoConfig.Confirmation = window.location.href;


function rhBlogSSO() {
    function updateUser() {
        window.digitalData = digitalData || {};
        digitalData.user = digitalData.user || [{profile: [{profileInfo:{}}]}];
        var usr = digitalData.user[0].profile[0].profileInfo;

        if (keycloak.authenticated) {
            keycloak.updateToken().success(function () {
                saveTokens();

                $('a.logged-in-name')
                    .text(keycloak.tokenParsed['name'])
                    .attr('href', ssoConfig.AccountUrl);
                $('li.login, li.register').hide();
                //$('li.logged-in').show();
                $('li.login a, a.keycloak-url').attr("href", keycloak.createAccountUrl())
                // once the promise comes back, listen for a click on logout
                $('a.logout').on('click',function(e) {
                    e.preventDefault();
                    keycloak.logout();
                });

                usr.loggedIn = true;

                usr.keyCloakID = keycloak.tokenParsed['id'];
                usr.daysSinceRegistration = daysDiff(Date.now(), keycloak.tokenParsed['createdTimestamp']);

                if (typeof Object.keys == "function") {
                    usr.socialAccountsLinked = Object.keys(keycloak.tokenParsed['user-social-links'])
                } else {
                    for (social in keycloak.tokenParsed['user-social-links']) {
                        usr.socialAccountsLinked.push(social);
                    }
                }

            }).error(clearTokens);
        } else {

            $('li.logged-in').hide();
            $('li.login a').on('click',function(e){
                e.preventDefault();
                keycloak.login();
            });
            $('li.register a, a.keycloak-url').on('click',function(e){
                e.preventDefault();
                keycloak.login({ action : 'register', redirectUri : ssoConfig.Confirmation }); // sso Change
            });
        }

        updateAnalytics(usr);
    }

    function daysDiff(dt1, dt2) {
        return Math.floor(Math.abs(dt1-dt2)/(1000*60*60*24))
    }

    function updateAnalytics(usr) {
        var ddUserAuthEvent = {
            eventInfo: {
                eventName: 'user data',
                eventAction: 'available',
                user: [{
                    profile: [{
                        profileInfo: usr
                    }]
                }],
                timeStamp: new Date(),
                processed: {
                    adobeAnalytics: false
                }
            }
        };

        //Push it onto the event array of the digitalData object
        window.digitalData = window.digitalData || {};
        digitalData.event = digitalData.event || [];
        digitalData.event.push(ddUserAuthEvent);
        //Update digitalData.page.listing objects
        digitalData.user = digitalData.user || [{ profile: [{ profileInfo: {} }] }];
        digitalData.user[0].profile[0].profileInfo = usr;
        //Create and dispatch an event trigger using the predefined function
        sendCustomEvent('ajaxAuthEvent');
    }

    function saveTokens() {
        if (keycloak.authenticated) {
            var tokens = {token: keycloak.token, refreshToken: keycloak.refreshToken};
            if (storageAvailable('localStorage')) {
                window.localStorage.token = JSON.stringify(tokens);
            } else {
                document.cookie = 'token=' + btoa(JSON.stringify(tokens));
            }
        } else {
            if (storageAvailable('localStorage')) {
                delete window.localStorage.token;
            } else {
                document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
            }
        }
    }

    function loadTokens() {
        if (storageAvailable('localStorage')) {
            if (window.localStorage.token) {
                return JSON.parse(window.localStorage.token);
            }
        } else {
            var name = 'token=';
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];

                while (c.charAt(0) == ' ') {
                    c = c.substring(1);
                }

                if (c.indexOf(name) == 0) {
                    return JSON.parse(atob(c.substring(name.length, c.length)));
                }
            }
        }
    }

    function clearTokens() {
        keycloak.clearToken();
        if (storageAvailable('localStorage')) {
            window.localStorage.token = "";
        } else {
            document.cookie = 'token=' + btoa("");
        }
    }

    function checkIfProtectedPage() {
        if ($('.protected').length) {
            if (!keycloak.authenticated) {
                keycloak.login();
            }
        }
    }

    var keycloak = Keycloak({
        url: ssoConfig.AuthUrl, // sso change
        realm: 'redhat-external',
        clientId: 'rhd-web'
    });


    rhBlogSSO.keycloak = keycloak;
    var tokens = loadTokens();
    var init = {onLoad: 'check-sso', checkLoginIframeInterval: 10};
    if (tokens) {
        init.token = tokens.token;
        init.refreshToken = tokens.refreshToken;
    }

    keycloak.onAuthLogout = updateUser;

    keycloak.init(init).success(function (authenticated) {
        updateUser(authenticated);
        saveTokens();
        checkIfProtectedPage();



    }).error(function () {
        updateUser();
    });

    function sendCustomEvent(evt){
        if(document.createEvent && document.body.dispatchEvent){
            var event = document.createEvent('Event');
            event.initEvent(evt, true, true); //can bubble, and is cancellable
            document.body.dispatchEvent(event);
        } else if(window.CustomEvent && document.body.dispatchEvent) {
            var event = new CustomEvent(evt, {bubbles: true, cancelable: true});
            document.body.dispatchEvent(event);
        }
    }

    function storageAvailable(type) {
        try {
            var storage = window[type],
                x = '__storage_test__';
            storage.setItem(x, x);
            storage.removeItem(x);
            return true;
        }
        catch(e) {
            return false;
        }
    }

};
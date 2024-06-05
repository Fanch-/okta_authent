// react app

if (isAuthenticated === 'off') {
    fetch('https://api-my-backend.com/api/user', {
        credentials: 'include',
        mode: 'cors'
    }).then(async response => {
        // what we thought could be done without CORS trouble
        if (response.redirected) {
            window.location.href = response.url;
        }
        const body = await response.json();
        setUser(body)
        setIsAuthenticated('on');
    }).catch(error => {
        console.error('There has been a problem with the fetch operation:', error);
        // mandatory for cors error to authenticate
        window.location.href = 'https://api-my-backend.com/oauth2/authorization/okta';
    })
}

// We could do it otherwise, but we would like to understand the CORS error, confirm
// we can't do it that way and this isn't a misconfiguration.

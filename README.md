# okta_authent

Example for support 🙏


The flow is :

* [call.js](frontend/call.js) from https://my-frontend.com

* which target [https://api-my-backend.com/api/user](backend/src/main/java/api/UserController.java) line 23 (status 302)

* redirect to https://api-my-backend.com/oauth2/authorization/okta (302)

* then (200) from https://dev-3504888.okta.com/oauth2/default/v1/authorize?response_type=code&client_id=XXXX&scope=openid%20email%20profile&state=y73jQmAetw5nxT7IwH2odcMzu0zXwyfxwNxSJ750TsE%3D&redirect_uri=https://api-my-backend.com/login/oauth2/code/okta&nonce=FN1_KnX5Sn46CwVLc0l_vqETzhV_V68f7uO-5snf798

* but CORS error, we can see we don't have the backend domain -> Access-Control-Allow-Origin: https://api-my-backend.com
in response header from okta domain call.

```console
Access to fetch at 'https://dev-3504888.okta.com/oauth2/default/v1/authorize?response_type=code&client_id=XXXX&scope=openid%20email%20profile&state=y73jQmAetw5nxT7IwH2odcMzu0zXwyfxwNxSJ750TsE%3D&redirect_uri=https://api-my-backend.com/login/oauth2/code/okta&nonce=FN1_KnX5Sn46CwVLc0l_vqETzhV_V68f7uO-5snf798'
(redirected from 'https://api-my-backend.com/api/user')
from origin 'https://my-frontend.com'
has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource. If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled.
```

What we also see in the browser console :
```
Cookie Attributes: The cookie being set might not comply with the browser's requirements, such as the SameSite attribute or the Secure attribute for cookies sent over HTTPS.
```


In Security -> API -> Trusted origins, we set correctly :

CORS
Redirect 

For both origin URL https://api-my-backend.com and https://my-frontend.com.



import { HttpInterceptorFn } from '@angular/common/http';
import { TokenService } from '../token/token.service';

export const customInterceptor: HttpInterceptorFn = (req, next) => {

  console.log(req.url)
  /**
   * in the custom interceptor all HTTP requests are intercepted
   * the interceptor clones the incoming request and sets the authorization header to the token
   * once this  is done it allows the request to be handled by the next handler
   * 
   * The interceptor allows protected endpoints in our backend to always recieve the jwt we generate
   * as all apis dont need a jwt like sign up or login we can ensure these are skipped from the interceptor
   */ 

  if(req.url=== 'http://localhost/55567/login' || 'http://localhost/55567/sign-up'){
    return next(req);

  }

  const tokenservice = new TokenService();
  const jwt = tokenservice.token //call the getter that fetches the token from the local storage

  req = req.clone({headers:req.headers.set('Authorization', 'Bearer' + `${jwt}`)})
  //allow the request to be handled by the next handler
  return next(req);

  
}

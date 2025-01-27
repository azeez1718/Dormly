import { HttpInterceptorFn } from '@angular/common/http';
import { TokenService } from '../token/token.service';
import { Inject} from '@angular/core';

export const customInterceptor: HttpInterceptorFn = (req, next) => {


  /**
   * in the custom interceptor all HTTP requests are intercepted
   * the interceptor clones the incoming request and sets the authorization header to the token
   * once this  is done it allows the request to be handled by the next handler
   * 
   * The interceptor allows protected endpoints in our backend to always recieve the jwt we generate
   * as all apis dont need a jwt like sign up or login we can ensure these are skipped from the interceptor
   */ 

  if(req.url=== 'http://localhost:8099/api/v1/Dormly.com/login' || req.url ==='http://localhost:8099/api/v1/Dormly.com/sign-up'){
    return next(req);

  }

  //inject our token service as there is no constructor 
  //const tokenservice = Inject(TokenService)
  const jwt = localStorage.getItem('token')?.trim() //call the getter that fetches the token from the local storage
  

  
  req = req.clone({headers:req.headers.set('Authorization',`Bearer ${jwt}`)})
  //allow the request to be handled by the next handler
  return next(req);
  

  

  
}

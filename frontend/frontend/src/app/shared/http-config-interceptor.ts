import { HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { Router } from "@angular/router"
import { catchError, Observable, throwError } from "rxjs"
import { GlobalService } from "./services/global.service"


@Injectable({
    providedIn: 'root'
})

export class HttpConfigInterceptor implements HttpInterceptor{
    
    constructor(
        private globalService: GlobalService,
        private router: Router
    ){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const token: string = this.globalService.user && this.globalService.user.jwt;

        let request = req;

        if(token){
            request = req.clone({
                setHeaders: {
                    Authorization: token
                }
            });
        }

        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                if(error.status === 401){
                    this.router.navigateByUrl("/customers");
                }
                return throwError(error);
            })
        );
    }

}
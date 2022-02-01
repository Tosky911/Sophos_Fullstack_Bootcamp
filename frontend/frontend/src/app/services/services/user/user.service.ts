import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/entities/user';
import { GeneralResponse } from 'src/app/shared/models/general-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  UrlUser = 'http://localhost:8080/customers/';
  constructor(private http:HttpClient) { }

  //Listar todos los clientes de la BBDD
  get(): Observable<GeneralResponse<User[]>>{
    return this.http.get<GeneralResponse<User[]>>(this.UrlUser);
  }
  
  //Crear un usuario
  save(user: User): Observable<GeneralResponse<User[]>>{
    return this.http.post<GeneralResponse<User[]>>(this.UrlUser, user);
  }

  //Listar un solo usuario
  listOneCustomerId(id:any): Observable<GeneralResponse<User>>{
    const url = `${this.UrlUser}${id}`;
    return this.http.get<GeneralResponse<User>>(url);
  }

  //Actualizar los datos del usuario
  edit(id:any, user:User): Observable<GeneralResponse<User[]>>{
    const url = `${this.UrlUser}${id}`;
    return this.http.put<GeneralResponse<User[]>>(url, user);
  }

  //Borrar un usuario
  delete(id:any): Observable<GeneralResponse<number>>{
    const url = `${this.UrlUser}${id}`;
    return this.http.delete<GeneralResponse<number>>(url);
  }

}

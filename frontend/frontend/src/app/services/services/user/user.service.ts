import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/entities/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  UrlUser = 'http://localhost:8080/users/';
  constructor(private http:HttpClient) { }

  //Listar todos los clientes de la BBDD
  getUser(): Observable<User[]>{
    return this.http.get<User[]>(this.UrlUser);
  }
  
  //Crear un usuario
  createUser(data: any): Observable<any>{
    return this.http.post(this.UrlUser,data);
  }

  //Listar un solo usuario
  getUserId(id:any): Observable<any>{
    const url = `${this.UrlUser}${id}`;
    return this.http.get(url);
  }

  //Actualizar los datos del usuario
  updateUser(id:any, data:any): Observable<any>{
    const url = `${this.UrlUser}${id}`;
    return this.http.put(url, data);
  }

  //Borrar un usuario
  deleteUser(id:any): Observable<any>{
    const url = `${this.UrlUser}${id}`;
    return this.http.delete(url);
  }

}

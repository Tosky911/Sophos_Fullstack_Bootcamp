import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from 'src/app/models/customer';
import { GeneralResponse } from 'src/app/shared/models/general-response';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  UrlCustomer = 'http://localhost:8080/customers/';
  constructor(private http:HttpClient) { }

  //Listar todos los clientes de la BBDD
  get(): Observable<GeneralResponse<Customer[]>>{
    return this.http.get<GeneralResponse<Customer[]>>(this.UrlCustomer);
  }
  
  //Crear un usuario
  save(customer: Customer): Observable<GeneralResponse<Customer[]>>{
    return this.http.post<GeneralResponse<Customer[]>>(this.UrlCustomer, customer);
  }

  //Listar un solo usuario
  listOneCustomerId(id:any): Observable<GeneralResponse<Customer>>{
    const url = `${this.UrlCustomer}${id}`;
    return this.http.get<GeneralResponse<Customer>>(url);
  }

  //Actualizar los datos del usuario
  edit(id:any, customer:Customer): Observable<GeneralResponse<Customer[]>>{
    const url = `${this.UrlCustomer}${id}`;
    return this.http.put<GeneralResponse<Customer[]>>(url, customer);
  }

  //Borrar un usuario
  delete(id:any): Observable<GeneralResponse<number>>{
    const url = `${this.UrlCustomer}${id}`;
    return this.http.delete<GeneralResponse<number>>(url);
  }

}

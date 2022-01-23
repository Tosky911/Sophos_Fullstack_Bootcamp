import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http'; 
//import { Product } from ;
//import { Transaction } from ;


@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  UrlTransaction = 'http://localhost:8080/users/';
  constructor(private http: HttpClient) { }

  //Listar todas las transacciones por producto/cuenta
  getTransaction(id: any, productId: any): Observable<Transaction[]>{
    const url = `${this.UrlTransaction}${id}/products/${productId}/transaction`;
    return this.http.get<Transaction[]>(url);
  }
  
  //Crear transaccion para un producto/cuenta
  createTransaction(data: any, id:any, productId: any): Observable<any>{
    const url = `${this.UrlTransaction}${id}/products/${productId}/transaction/create`;
    return this.http.post(url, data);
  }

  //Listar todos los productos que pueden hacer transferencias de dinero
  getProduct(id:any, productId: any): Observable<Product[]>{
    const url = `${this.UrlTransaction}${id}/products/${productId}/transferable`;
    return this.http.get<Product[]>(url);
  }


}

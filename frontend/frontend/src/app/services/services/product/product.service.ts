import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http'; 
import { Product } from 'src/app/models/product';
import { GeneralResponse } from 'src/app/shared/models/general-response';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  UrlProduct = 'http://localhost:8080/customers/';

  constructor(private http: HttpClient) { }

  //Listar todos los productos de un usuario
  getProduct(id: any): Observable<GeneralResponse<Product[]>> {
    const url = `${this.UrlProduct}${id}/products`;
    return this.http.get<GeneralResponse<Product[]>>(url);
  }

  //Listar un producto por id
  getOneProduct(id: any, productId :any): Observable<GeneralResponse<Product>>{
    const url = `${this.UrlProduct}${id}/products/${productId}`;
    return this.http.get<GeneralResponse<Product>>(url);
  }

  //Crear un producto para un usuario
  createProduct(data: any, id: any): Observable<GeneralResponse<Product>>{
    const url = `${this.UrlProduct}${id}/products`;
    return this.http.post<GeneralResponse<Product>>(url, data);
  }

  //Cambiar el state a activo
  activateProduct(id: any, productId: any, data:any): Observable<any>{
    const url = `${this.UrlProduct}${id}/products/${productId}/activate`;
    return this.http.put(url, data);
  }

  //Cambiar el state a inactivo
  deactivateProduct(id: any, productId: any, data:any): Observable<any>{
    const url = `${this.UrlProduct}${id}/products/${productId}/deactivate`;
    return this.http.put(url, data);
  }

  //Cambiar el state cancelado
  cancelProduct(id: any, productId: any, data:any): Observable<any>{
    const url = `${this.UrlProduct}${id}/products/${productId}/cancel`;
    return this.http.put(url, data);
  }

  //Cambiar el state cancelado
  uncancelProduct(id: any, productId: any, data:any): Observable<any>{
    const url = `${this.UrlProduct}${id}/products/${productId}/uncancel`;
    return this.http.put(url, data);
  }

  //Agregar dinero a un producto/cuenta
  addMoney(id: any, productId: any, money: any, data: any): Observable<any>{
    const url = `${this.UrlProduct}${id}/products/${productId}/${money}/deposit`;
    return this.http.put(url, data);
  }

  //Retirar dinero de un producto/cuenta
  withdrawMoney(id: any, productId: any, money: any, data: any): Observable<any>{
    const url = `${this.UrlProduct}${id}/products/${productId}/${money}/withdraw`;
    return this.http.put(url, data);
  }

}

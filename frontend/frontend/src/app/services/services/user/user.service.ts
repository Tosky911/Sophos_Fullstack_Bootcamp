import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';
import { GeneralResponse } from 'src/app/shared/models/general-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = "http://localhost:8080/users";

  constructor(private http: HttpClient) {
  }

  get(): Observable<GeneralResponse<User[]>> {
      return this.http.get<GeneralResponse<User[]>>(this.url);
  }

  save(users: User[]): Observable<GeneralResponse<User[]>> {
      return this.http.post<GeneralResponse<User[]>>(this.url, users);
  }

  update(users: User[]): Observable<GeneralResponse<User[]>> {
      return this.http.put<GeneralResponse<User[]>>(this.url, users);
  }

  delete(userName: string): Observable<GeneralResponse<string>> {
      return this.http.delete<GeneralResponse<string>>(this.url + '/' + userName);
  }

  login(user: User): Observable<GeneralResponse<User>> {
      return this.http.post<GeneralResponse<User>>(this.url + '/auth', user);
  }

}

import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { GlobalService } from 'src/app/shared/services/global.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isLogged= false;

  constructor(
    public globalService:GlobalService
  ) { }

  ngOnInit(): void {
  }

  authenticated(user: User){
    this.globalService.user= user;
    this.isLogged= true;
  }

  signOut(){
    this.globalService.user ={
      userName:"",
      password:"",
      jwt:"",
      firstName:"",
      lastName:""
    };
    this.isLogged = false;
  }

}
